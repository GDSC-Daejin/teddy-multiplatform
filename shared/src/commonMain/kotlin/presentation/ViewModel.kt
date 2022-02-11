package presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

interface UnidirectionalViewModelDelegate<S: Any, EF, E> {

    fun Flow<S>.asStateFlow(
        defaultValue : S,
        coroutineScope : CoroutineScope
    ) : StateFlow<S> = stateIn(
        coroutineScope,
        SharingStarted.Lazily,
        defaultValue
    )

    val effect: Flow<EF>
    val state: StateFlow<S>
    fun event(e: E)

}

expect open class PlatformViewModel() {

    protected val platformViewModelScope : CoroutineScope

    open fun onViewModelCleared()

}