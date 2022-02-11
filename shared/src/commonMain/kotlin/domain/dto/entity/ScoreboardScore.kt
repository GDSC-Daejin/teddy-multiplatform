package domain.dto.entity

import kotlinx.serialization.Serializable

@Serializable
class ScoreboardScore(
    val username: String,
    val name: String,
    val avatar: String,
    val memberType: String,
    val score: Int
)