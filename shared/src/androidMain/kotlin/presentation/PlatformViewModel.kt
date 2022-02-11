package presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

actual open class PlatformViewModel actual constructor() : ViewModel() {

    protected actual val platformViewModelScope: CoroutineScope = viewModelScope

    actual open fun onViewModelCleared() {
    }

    override fun onCleared() {
        super.onCleared()
        onViewModelCleared()
    }

}