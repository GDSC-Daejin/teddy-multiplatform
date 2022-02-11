package presentation

import kotlinx.coroutines.CoroutineScope

actual open class PlatformViewModel actual constructor() {
    protected actual val platformViewModelScope: CoroutineScope
        get() = TODO("Not yet implemented")

    actual open fun onViewModelCleared() {
    }

}