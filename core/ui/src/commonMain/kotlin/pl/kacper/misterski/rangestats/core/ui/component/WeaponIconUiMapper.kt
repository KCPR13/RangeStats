package pl.kacper.misterski.rangestats.core.ui.component

import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.core.ui.Res
import pl.kacper.misterski.rangestats.core.ui.ic_weapon_pistol
import pl.kacper.misterski.rangestats.core.ui.ic_weapon_revolver
import pl.kacper.misterski.rangestats.core.ui.ic_weapon_rifle
import pl.kacper.misterski.rangestats.core.ui.ic_weapon_shotgun
import pl.kacper.misterski.rangestats.core.ui.weapon_pistol
import pl.kacper.misterski.rangestats.core.ui.weapon_revolver
import pl.kacper.misterski.rangestats.core.ui.weapon_rifle
import pl.kacper.misterski.rangestats.core.ui.weapon_shotgun

fun WeaponType.toUiModel(): WeaponIconUiModel =
    when (this) {
        WeaponType.PISTOL -> WeaponIconUiModel(Res.drawable.ic_weapon_pistol, Res.string.weapon_pistol)
        WeaponType.REVOLVER -> WeaponIconUiModel(Res.drawable.ic_weapon_revolver, Res.string.weapon_revolver)
        WeaponType.SHOTGUN -> WeaponIconUiModel(Res.drawable.ic_weapon_shotgun, Res.string.weapon_shotgun)
        WeaponType.RIFLE -> WeaponIconUiModel(Res.drawable.ic_weapon_rifle, Res.string.weapon_rifle)
    }
