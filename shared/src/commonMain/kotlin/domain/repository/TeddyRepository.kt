package domain.repository

import domain.LoadState
import domain.dto.entity.ScoreboardScore
import domain.dto.entity.UserScore
import domain.dto.entity.UserStats
import domain.dto.request.ScoreboardRequestParams
import domain.dto.request.UserScoreRequestParams
import kotlinx.coroutines.flow.Flow

interface TeddyRepository {

    fun getUserStat(userId : String) : Flow<LoadState<UserStats>>

    fun getTotalUserScore(params: UserScoreRequestParams): Flow<LoadState<UserScore>>

    fun getScoreboard(params: ScoreboardRequestParams): Flow<LoadState<List<ScoreboardScore>>>

}