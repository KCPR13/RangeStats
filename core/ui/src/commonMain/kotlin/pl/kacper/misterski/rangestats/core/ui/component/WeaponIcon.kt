package pl.kacper.misterski.rangestats.core.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdsClick
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource
import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.Spacing
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent
import rangestats.core.ui.generated.resources.Res
import rangestats.core.ui.generated.resources.weapon_pistol
import rangestats.core.ui.generated.resources.weapon_revolver
import rangestats.core.ui.generated.resources.weapon_rifle
import rangestats.core.ui.generated.resources.weapon_shotgun

//TODO set icons
@Composable
fun WeaponIcon(
    type: WeaponType,
    modifier: Modifier = Modifier,
    tint: Color = TacAccent,
) {
    val isPreview = LocalInspectionMode.current
    val (icon, contentDesc) = when (type) {
        WeaponType.PISTOL -> Icons.Default.GpsFixed to if (isPreview) "Pistol" else stringResource(Res.string.weapon_pistol)
        WeaponType.REVOLVER -> Icons.Default.AdsClick to if (isPreview) "Revolver" else stringResource(Res.string.weapon_revolver)
        WeaponType.SHOTGUN -> Icons.Default.LocalFireDepartment to if (isPreview) "Shotgun" else stringResource(Res.string.weapon_shotgun)
        WeaponType.RIFLE -> Icons.Default.Straighten to if (isPreview) "Rifle" else stringResource(Res.string.weapon_rifle)
    }
    Icon(
        imageVector = icon,
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
                WeaponIcon(type = type, modifier = Modifier.padding(Spacing.dp8))
            }
        }
    }
}
