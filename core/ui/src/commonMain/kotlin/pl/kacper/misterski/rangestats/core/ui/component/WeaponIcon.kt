package pl.kacper.misterski.rangestats.core.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent
import rangestats.core.ui.generated.resources.Res
import rangestats.core.ui.generated.resources.ic_weapon_pistol
import rangestats.core.ui.generated.resources.ic_weapon_revolver
import rangestats.core.ui.generated.resources.ic_weapon_rifle
import rangestats.core.ui.generated.resources.ic_weapon_shotgun
import rangestats.core.ui.generated.resources.weapon_pistol
import rangestats.core.ui.generated.resources.weapon_revolver
import rangestats.core.ui.generated.resources.weapon_rifle
import rangestats.core.ui.generated.resources.weapon_shotgun

@Composable
fun WeaponIcon(
    type: WeaponType,
    modifier: Modifier = Modifier,
    tint: Color = TacAccent,
) {
    val isPreview = LocalInspectionMode.current
    val (painter, contentDesc) = when (type) {
        WeaponType.PISTOL -> painterResource(Res.drawable.ic_weapon_pistol) to if (isPreview) "Pistol" else stringResource(
            Res.string.weapon_pistol
        )

        WeaponType.REVOLVER -> painterResource(Res.drawable.ic_weapon_revolver) to if (isPreview) "Revolver" else stringResource(
            Res.string.weapon_revolver
        )

        WeaponType.SHOTGUN -> painterResource(Res.drawable.ic_weapon_shotgun) to if (isPreview) "Shotgun" else stringResource(
            Res.string.weapon_shotgun
        )

        WeaponType.RIFLE -> painterResource(Res.drawable.ic_weapon_rifle) to if (isPreview) "Rifle" else stringResource(
            Res.string.weapon_rifle
        )
    }
    Icon(
        painter = painter,
        contentDescription = contentDesc,
        modifier = modifier,
        tint = tint,
    )
}

@Preview
@Composable
private fun WeaponIconAllTypesPreview() {
    RangeStatsTheme {
        Row {
            WeaponType.entries.forEach { type ->
                WeaponIcon(type = type, modifier = Modifier.padding(Dimen.dp8))
            }
        }
    }
}
