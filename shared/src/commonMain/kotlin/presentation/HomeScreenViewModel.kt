@file:OptIn(FlowPreview::class)
@file:Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED", "RUNTIME_ANNOTATION_NOT_SUPPORTED")

package presentation

import domain.LoadState
import domain.dto.entity.ScoreboardScore
import domain.dto.request.ScoreboardRequestParams
import domain.isLoading
import domain.isSuccess
import domain.usecase.GetScoreboardUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import presentation.HomeScreenViewModelDelegate.*
import utils.OptionalHiltViewModel
import utils.OptionalInject

interface HomeScreenViewModelDelegate : UnidirectionalViewModelDelegate<State, Effect, Event> {

    class State(
        val isLoading: Boolean = true,
        val scoreboards: List<ScoreboardScore> = emptyList(),
    )

    sealed interface Event {
        object Refresh : Event
        class ChangeScoreboardType(val type: ScoreboardRequestParams.Type) : Event
        class ChangeScoreboardDuration(val duration: ScoreboardRequestParams.Duration) : Event
    }

    sealed interface Effect {

    }
}

@OptionalHiltViewModel
class HomeScreenViewModel @OptionalInject constructor(
    private val getScoreboardUseCase: GetScoreboardUseCase
) : PlatformViewModel(), HomeScreenViewModelDelegate {

    private val scoreboardType = MutableStateFlow(ScoreboardRequestParams.Type.To)
    private val scoreboardDuration = MutableStateFlow(ScoreboardRequestParams.Duration.All)
    private val scoreboards = MutableStateFlow<LoadState<List<ScoreboardScore>>>(LoadState.Loading())

    override val state = scoreboards.map {
        State(
            isLoading = it.isLoading(),
            if (it.isSuccess()) it.data else emptyList(),
        )
    }.stateIn(
        platformViewModelScope,
        SharingStarted.Lazily,
        State()
    )

    private val effectChannel = Channel<Effect>(Channel.UNLIMITED)
    override val effect = effectChannel.receiveAsFlow()

    init {
        platformViewModelScope.launch {
            combine(
                scoreboardType, scoreboardDuration
            ) { scoreboardType, scoreboardDuration ->
                load(scoreboardType, scoreboardDuration)
            }.collect()
        }
    }

    suspend fun load(type : ScoreboardRequestParams.Type, duration: ScoreboardRequestParams.Duration) {
        getScoreboardUseCase(
            ScoreboardRequestParams(type = type, duration = duration)
        ).onEach {
            scoreboards.emit(it)
        }.collect()
    }

    private suspend fun refresh() {
        load(scoreboardType.value, scoreboardDuration.value)
    }

    override fun event(e: Event) {
        platformViewModelScope.launch {
            when (e) {
                is Event.ChangeScoreboardType -> scoreboardType.emit(e.type)
                is Event.ChangeScoreboardDuration -> scoreboardDuration.emit(e.duration)
                Event.Refresh -> refresh()
            }
        }
    }
}