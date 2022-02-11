package domain.dto.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserStatsUser(
    val username: String,
    val name: String,
    val avatar: String,
    val memberType: String,
    val receivedToday: Int,
    val givenToday: Int,
    val received: Int,
    val given: Int
)

@Serializable
data class UserStatsGivenOrReceived(
    val username: String,
    val name: String,
    val avatar: String,
    val memberType: String,
    @SerialName("scoreinc") val scoreInc: Int,
    @SerialName("scoredec") val scoreDec: Int
)

@Serializable
data class UserStats(
    val user: UserStatsUser,
    val given: List<UserStatsGivenOrReceived>,
    val received: List<UserStatsGivenOrReceived>,
    val givenToday: List<UserStatsGivenOrReceived>,
    val receivedToday: List<UserStatsGivenOrReceived>
)