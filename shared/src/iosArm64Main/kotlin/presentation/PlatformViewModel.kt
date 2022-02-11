package presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

actual open class PlatformViewModel actual constructor() {
    protected actual val platformViewModelScope: CoroutineScope = MainScope()

    actual open fun onViewModelCleared() {
        platformViewModelScope.cancel()
    }

}