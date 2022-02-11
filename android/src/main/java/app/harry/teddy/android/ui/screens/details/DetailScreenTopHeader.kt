package app.harry.teddy.android.ui.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import domain.dto.entity.UserStats
import domain.dto.entity.UserStatsUser

@Composable
fun DetailScreenTopHeader(user: UserStatsUser?) {

    val image = rememberImagePainter(data = user?.avatar) {
        transformations(CircleCropTransformation())
    }

    Surface(modifier = Modifier.fillMaxWidth()) {

        Column {

            Column(modifier = Modifier.padding(horizontal = 20.dp)) {

                Spacer(modifier = Modifier.height(10.dp))

                Image(
                    painter = image,
                    modifier = Modifier.size(56.dp),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(user?.name.orEmpty(), style = MaterialTheme.typography.h1)

                Spacer(modifier = Modifier.height(20.dp))

                Divider(color = MaterialTheme.colors.onSurface.copy(alpha = 0.05f))

                Spacer(modifier = Modifier.height(20.dp))

                Row {
                    Text("전체", style = MaterialTheme.typography.body2)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("${user?.given}개", style = MaterialTheme.typography.body2, color = MaterialTheme.colors.secondary)
                    Text(" 주고, ", style = MaterialTheme.typography.body2)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("${user?.received}개", style = MaterialTheme.typography.body2, color = MaterialTheme.colors.secondary)
                    Text(" 받음", style = MaterialTheme.typography.body2)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row {
                    Text("오늘", style = MaterialTheme.typography.body2)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("${user?.givenToday}개", style = MaterialTheme.typography.body2, color = MaterialTheme.colors.secondary)
                    Text(" 주고, ", style = MaterialTheme.typography.body2)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("${user?.receivedToday}개", style = MaterialTheme.typography.body2, color = MaterialTheme.colors.secondary)
                    Text(" 받음", style = MaterialTheme.typography.body2)
                }


                Spacer(modifier = Modifier.height(20.dp))

            }

        }

    }

}