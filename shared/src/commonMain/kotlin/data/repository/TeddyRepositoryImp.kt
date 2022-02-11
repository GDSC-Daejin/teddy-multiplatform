@file:Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")

package data.repository

import data.ScoreboardApi
import data.UserScoreApi
import domain.LoadState
import domain.dto.entity.ScoreboardScore
import domain.dto.entity.UserScore
import domain.dto.entity.UserStats
import domain.dto.request.ScoreboardRequestParams
import domain.dto.request.UserScoreRequestParams
import domain.repository.TeddyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import utils.OptionalInject
import utils.OptionalSingleton

@OptionalSingleton
class TeddyRepositoryImp @OptionalInject constructor(
    private val scoreboardApi: ScoreboardApi,
    private val userScoreApi: UserScoreApi,
) : TeddyRepository {

    override fun getUserStat(userId: String) = flow<LoadState<UserStats>> {
        emit(LoadState.Loading())
        userScoreApi.runCatching {
            getUserStat(userId)
        }.onFailure {
            emit(LoadState.Error(it))
        }.onSuccess {
            emit(LoadState.Success(it.data))
        }
    }

    override fun getTotalUserScore(params: UserScoreRequestParams) = flow<LoadState<UserScore>> {
        emit(LoadState.Loading())
        userScoreApi.runCatching {
            getTotalUserScore(params)
        }.onFailure {
            emit(LoadState.Error(it))
        }.onSuccess {
            emit(LoadState.Success(it.data))
        }
    }

    override fun getScoreboard(params: ScoreboardRequestParams) = flow<LoadState<List<ScoreboardScore>>> {
        emit(LoadState.Loading())
        scoreboardApi.runCatching {
            getScoreboard(params)
        }.onFailure {
            emit(LoadState.Error(it))
        }.onSuccess {
            emit(LoadState.Success(it.data))
        }
    }

}