package pl.kacper.misterski.rangestats.feature.ballistics.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.CaliberPreset
import pl.kacper.misterski.rangestats.feature.ballistics.domain.repository.BallisticsRepository

class GetCaliberPresetsUseCase(
    private val ballisticsRepository: BallisticsRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {
    operator fun invoke(): Flow<List<CaliberPreset>> = flow {
        emit(ballisticsRepository.getCaliberPresets())
    }.flowOn(ioDispatcher)
}
