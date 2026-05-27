package pl.kacper.misterski.rangestats.feature.ballistics.domain.repository

import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.CaliberPreset

interface BallisticsRepository {
    fun getCaliberPresets(): List<CaliberPreset>
}
