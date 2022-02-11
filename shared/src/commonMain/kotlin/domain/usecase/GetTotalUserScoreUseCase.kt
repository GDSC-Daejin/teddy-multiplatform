package domain.usecase

import domain.LoadState
import domain.dto.entity.UserScore
import domain.dto.request.UserScoreRequestParams
import domain.repository.TeddyRepository
import kotlinx.coroutines.flow.Flow
import utils.OptionalInject
import utils.OptionalSingleton

@OptionalSingleton
class GetTotalUserScoreUseCase @OptionalInject constructor(
    private val teddyRepository: TeddyRepository
) : UseCase<UserScoreRequestParams, Flow<LoadState<UserScore>>> {

    override fun invoke(input: UserScoreRequestParams): Flow<LoadState<UserScore>> {
        return teddyRepository.getTotalUserScore(input)
    }

}