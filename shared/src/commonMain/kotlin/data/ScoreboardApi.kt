@file:Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")

package data

import domain.dto.entity.ApiResponse
import domain.dto.entity.ScoreboardScore
import domain.dto.request.ScoreboardRequestParams
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import utils.OptionalInject
import utils.OptionalSingleton

interface ScoreboardApi {

    suspend fun getScoreboard(params: ScoreboardRequestParams): ApiResponse<List<ScoreboardScore>>

}

@OptionalSingleton
class ScoreboardApiImp @OptionalInject constructor(
    private val httpClient: HttpClient
) : ScoreboardApi {

    override suspend fun getScoreboard(params: ScoreboardRequestParams): ApiResponse<List<ScoreboardScore>> {
        return httpClient.get("/scoreboard/${params.duration.value}/${params.type.value}/inc").body()
    }

}