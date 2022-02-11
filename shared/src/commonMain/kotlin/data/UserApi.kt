@file:Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")

package data

import domain.dto.entity.ApiResponse
import domain.dto.entity.UserScore
import domain.dto.entity.UserStats
import domain.dto.request.UserScoreRequestParams
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import utils.OptionalInject
import utils.OptionalSingleton

interface UserScoreApi {

    suspend fun getTotalUserScore(params: UserScoreRequestParams): ApiResponse<UserScore>

    suspend fun getUserStat(userId : String): ApiResponse<UserStats>

}

@OptionalSingleton
class UserScoreApiImp @OptionalInject constructor(
    private val httpClient: HttpClient
) : UserScoreApi {

    override suspend fun getTotalUserScore(params: UserScoreRequestParams): ApiResponse<UserScore> {
        return httpClient.get("/userscore/${params.userId}/${params.type.value}/inc").body()
    }

    override suspend fun getUserStat(userId : String): ApiResponse<UserStats> {
        return httpClient.get("/userstats/$userId").body()
    }

}