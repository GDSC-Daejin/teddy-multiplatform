package domain.usecase

import domain.LoadState
import domain.dto.entity.UserStats
import domain.repository.TeddyRepository
import kotlinx.coroutines.flow.Flow
import utils.OptionalInject
import utils.OptionalSingleton

@OptionalSingleton
class GetUserStatsUseCase @OptionalInject constructor(
    private val teddyRepository: TeddyRepository
) : UseCase<String, Flow<LoadState<UserStats>>> {

    override fun invoke(input: String): Flow<LoadState<UserStats>> =
        teddyRepository.getUserStat(input)

}