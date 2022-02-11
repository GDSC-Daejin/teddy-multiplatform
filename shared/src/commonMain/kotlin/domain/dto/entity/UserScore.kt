package domain.dto.entity

import kotlinx.serialization.Serializable

@Serializable
data class UserScore(
    val username: String,
    val name: String,
    val avatar: String,
    val memberType: String,
    val score: Int,
    val scoreType: String,
    val listType: String
)