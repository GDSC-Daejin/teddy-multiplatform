@file:OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@file:Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED", "RUNTIME_ANNOTATION_NOT_SUPPORTED")

package presentation

import domain.LoadState
import domain.dto.entity.UserScore
import domain.dto.entity.UserStats
import domain.dto.request.UserScoreRequestParams
import domain.isLoading
import domain.usecase.GetTotalUserScoreUseCase
import domain.usecase.GetUserStatsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import presentation.DetailScreenViewModelDelegate.*
import utils.OptionalHiltViewModel
import utils.OptionalInject

interface DetailScreenViewModelDelegate : UnidirectionalViewModelDelegate<State, Effect, Event> {

    class State(
        val isUserStatsLoading: Boolean = true,
        val isTotalUserScoreLoading: Boolean = true,
        val userStats: UserStats? = null,
        val totalUserScore: UserScore? = null
    ) {

        val user get() = userStats?.user

        val isTotalContentLoading get() = isUserStatsLoading && isTotalUserScoreLoading
    }

    sealed interface Event {
        object Refresh : Event
        class LoadUser(val userId: String) : Event
        class ChangeUserScoreType(val type: UserScoreRequestParams.Type) : Event
    }

    sealed interface Effect {

    }
}

@OptionalHiltViewModel
class DetailScreenViewModel @OptionalInject constructor(
    private val getUserStatsUseCase: GetUserStatsUseCase,
    private val getTotalUserScoreUseCase: GetTotalUserScoreUseCase
) : PlatformViewModel(), DetailScreenViewModelDelegate {

    private val type = MutableStateFlow(UserScoreRequestParams.Type.To)

    private val userId = MutableStateFlow<String?>(null)

    private val userStats = MutableStateFlow<LoadState<UserStats>>(LoadState.Loading())

    private val totalUserScore = MutableStateFlow<LoadState<UserScore>>(LoadState.Loading())

    override val state = combine(
        userStats, totalUserScore,
    ) { userStats, totalUserScore ->
        State(
            isUserStatsLoading = userStats.isLoading(),
            isTotalUserScoreLoading = totalUserScore.isLoading(),
            userStats = userStats.getDataOrNull(),
            totalUserScore = totalUserScore.getDataOrNull()
        )
    }.stateIn(
        platformViewModelScope,
        SharingStarted.Lazily,
        State()
    )

    private val effectChannel = Channel<Effect>(Channel.UNLIMITED)
    override val effect = effectChannel.receiveAsFlow()

    init {
        userId.filterNotNull()
            .onEach { userId ->

                loadUserStats(userId)

                type.onEach { type -> loadTotalUserScore(userId, type) }
                    .launchIn(platformViewModelScope)

            }.launchIn(platformViewModelScope)
    }

    private suspend fun loadUserStats(userId: String) {
        getUserStatsUseCase(userId).collect {
            userStats.emit(it)
        }
    }

    private suspend fun loadTotalUserScore(userId: String, type: UserScoreRequestParams.Type) {
        getTotalUserScoreUseCase(UserScoreRequestParams(userId, type)).collect {
            totalUserScore.emit(it)
        }
    }

    private suspend fun refresh() {
        val id = userId.value ?: return
        loadUserStats(id)
        loadTotalUserScore(id, type.value)
    }

    override fun event(e: Event) {
        platformViewModelScope.launch {
            when (e) {
                is Event.LoadUser -> userId.emit(e.userId)
                is Event.ChangeUserScoreType -> type.emit(e.type)
                Event.Refresh -> refresh()
            }
        }
    }
}