package domain.usecase

import domain.LoadState
import domain.dto.entity.ScoreboardScore
import domain.dto.request.ScoreboardRequestParams
import domain.repository.TeddyRepository
import kotlinx.coroutines.flow.Flow
import utils.OptionalInject
import utils.OptionalSingleton

@OptionalSingleton
class GetScoreboardUseCase @OptionalInject constructor(
    private val teddyRepository: TeddyRepository
) : UseCase<ScoreboardRequestParams, Flow<LoadState<List<ScoreboardScore>>>> {

    override fun invoke(input: ScoreboardRequestParams): Flow<LoadState<List<ScoreboardScore>>> {
        return teddyRepository.getScoreboard(input)
    }

}