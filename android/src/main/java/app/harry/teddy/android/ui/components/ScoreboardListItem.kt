package app.harry.teddy.android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import domain.dto.entity.ScoreboardScore
import domain.dto.entity.UserStatsGivenOrReceived

@Composable
private fun UserListItem(
    avatar: String,
    name: String,
    score: Int,
    onClick: () -> Unit
) {

    val image = rememberImagePainter(data = avatar) {
        transformations(CircleCropTransformation())
    }

    Surface(
        color = Color.Transparent,
        contentColor = MaterialTheme.colors.onSurface,
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {

        ConstraintLayout(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            val (imageRef, nameRef, scoreRef) = createRefs()

            Image(
                painter = image,
                modifier = Modifier.size(36.dp)
                    .constrainAs(imageRef) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    },
                contentDescription = null
            )

            Text(
                modifier = Modifier
                    .constrainAs(nameRef) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(imageRef.end, margin = 20.dp)
                    },
                text = name,
                style = MaterialTheme.typography.subtitle2,
            )

            Text(
                modifier = Modifier
                    .constrainAs(scoreRef) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    },
                text = score.toString(),
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.secondary
            )

        }

    }

}

@Composable
fun ScoreboardListItem(
    scoreboardScore: ScoreboardScore,
    onClick: (ScoreboardScore) -> Unit
) {
    UserListItem(
        scoreboardScore.avatar,
        scoreboardScore.name,
        scoreboardScore.score,
    ) {
        onClick(scoreboardScore)
    }
}

@Composable
fun UserStatsGivenOrReceivedListItem(
    userStatsGivenOrReceived: UserStatsGivenOrReceived,
    onClick: (UserStatsGivenOrReceived) -> Unit
) {
    UserListItem(
        userStatsGivenOrReceived.avatar,
        userStatsGivenOrReceived.name,
        userStatsGivenOrReceived.scoreInc,
    ) {
        onClick(userStatsGivenOrReceived)
    }
}