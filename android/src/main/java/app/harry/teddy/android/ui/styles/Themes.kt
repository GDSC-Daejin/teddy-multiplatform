package app.harry.teddy.android.ui.styles

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable



@Composable
fun MainTheme(
    darkTheme : Boolean = isSystemInDarkTheme(),
    content : @Composable() () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) darkColorSchemes else lightColorSchemes,
        typography = typo,
        content = content
    )
}