package pl.kacper.misterski.rangestats.feature.settings.ui.weapon

import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.core.ui.component.WeaponIconUiModel

data class WeaponListUiModel(
    val weapons: List<WeaponRowUiModel> = emptyList(),
    val isLoading: Boolean = false,
) {
    data class WeaponRowUiModel(
        val name: String,
        val caliber: String,
        val type: WeaponType,
        val icon: WeaponIconUiModel,
    )
}
