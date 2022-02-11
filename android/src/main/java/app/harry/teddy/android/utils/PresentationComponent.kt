package app.harry.teddy.android.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.Flow
import presentation.UnidirectionalViewModelDelegate

data class PresentationComponent<S, EF, E>(
    val state: S,
    val effect: Flow<EF>,
    val dispatch: (E) -> Unit
)

@Composable
fun <S : Any, EF, E> UnidirectionalViewModelDelegate<S, EF, E>.extract(): PresentationComponent<S, EF, E> {

    val state by state.collectAsState()

    val dispatch: (E) -> Unit = { event -> event(event) }

    return PresentationComponent(
        state = state,
        effect = effect,
        dispatch = dispatch
    )
}