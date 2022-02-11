package app.harry.teddy.android.ui.screens.home

import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import domain.dto.request.ScoreboardRequestParams

@Composable
fun HomeDurationDropdownMenu(
    modifier: Modifier = Modifier,
    selectedDuration : ScoreboardRequestParams.Duration,
    isDropdownMenuOpened : Boolean,
    onSelectedDurationChanged : (ScoreboardRequestParams.Duration) -> Unit,
    onDropdownMenuOpenStateChanged: (Boolean) -> Unit,
) {

    ExposedDropdownMenuBox(
        expanded = isDropdownMenuOpened,
        modifier = modifier,
        onExpandedChange = { onDropdownMenuOpenStateChanged(isDropdownMenuOpened.not()) },
    ) {
        OutlinedTextField(
            readOnly = true,
            value = when (selectedDuration) {
                ScoreboardRequestParams.Duration.All -> "전체"
                ScoreboardRequestParams.Duration.Monthly -> "월간"
                ScoreboardRequestParams.Duration.Weekly -> "주간"
            },
            onValueChange = { },
            trailingIcon = {
                IconButton(onClick = { }, modifier = Modifier.clearAndSetSemantics { }) {
                    Icon(
                        Icons.Filled.ArrowDropDown,
                        "Trailing icon for exposed dropdown menu",
                        Modifier.rotate(
                            if (isDropdownMenuOpened)
                                180f
                            else
                                360f
                        ),
                        tint = MaterialTheme.colors.onSurface
                    )
                }
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colors.onSurface,
                focusedBorderColor = Color.Unspecified,
                unfocusedBorderColor = Color.Unspecified,
            )
        )

        ExposedDropdownMenu(
            isDropdownMenuOpened, {
                onDropdownMenuOpenStateChanged(false)
            }
        ) {
            DropdownMenuItem({ onSelectedDurationChanged(ScoreboardRequestParams.Duration.All) }) { Text("전체") }
            DropdownMenuItem({
                onSelectedDurationChanged(ScoreboardRequestParams.Duration.Monthly)
            }) { Text("월간") }
            DropdownMenuItem({
                onSelectedDurationChanged(ScoreboardRequestParams.Duration.Weekly)
            }) { Text("주간") }
        }
    }

}