package pl.kacper.misterski.rangestats.feature.settings.ui.weapon

import pl.kacper.misterski.rangestats.core.domain.models.Weapon

data class WeaponListUiModel(
    val weapons: List<Weapon> = emptyList(),
    val isLoading: Boolean = false,
    val showAddSheet: Boolean = false,
)
