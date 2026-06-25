package pl.kacper.misterski.rangestats.feature.settings.ui.weapon

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource
import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.core.domain.models.Weapon
import pl.kacper.misterski.rangestats.core.ui.component.TacButton
import pl.kacper.misterski.rangestats.core.ui.component.WeaponIcon
import pl.kacper.misterski.rangestats.core.ui.core_x
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.FontSize
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgCard
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgDeep
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgElevated
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgPanel
import pl.kacper.misterski.rangestats.core.ui.theme.TacBorder
import pl.kacper.misterski.rangestats.core.ui.theme.TacRed
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextMuted
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextSecondary
import rangestats.feature.settings.generated.resources.Res
import rangestats.feature.settings.generated.resources.settings_add_weapon
import rangestats.feature.settings.generated.resources.weapon_badge_pistol
import rangestats.feature.settings.generated.resources.weapon_badge_revolver
import rangestats.feature.settings.generated.resources.weapon_badge_rifle
import rangestats.feature.settings.generated.resources.weapon_badge_shotgun
import rangestats.feature.settings.generated.resources.weapon_list_subtitle
import rangestats.feature.settings.generated.resources.weapon_list_title

@Composable
fun WeaponListScreen(
    state: WeaponListUiModel,
    onAction: (WeaponListAction) -> Unit,
    onBack: () -> Unit,
    showAddWeapon: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        onAction(WeaponListAction.OnStart)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TacBgDeep),
    ) {
        WeaponListHeader(
            title = stringResource(Res.string.weapon_list_title),
            subtitle = stringResource(Res.string.weapon_list_subtitle),
            onBack = onBack,
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(Dimen.dp20),
            verticalArrangement = Arrangement.spacedBy(Dimen.dp8),
        ) {
            state.weapons.forEach { weapon ->
                WeaponListRow(
                    weapon = weapon,
                    onDelete = { onAction(WeaponListAction.DeleteWeapon(weapon.name)) },
                )
            }
            TacButton(
                text = stringResource(Res.string.settings_add_weapon),
                onClick = showAddWeapon,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun WeaponListHeader(title: String, subtitle: String, onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TacBgPanel)
            .padding(horizontal = Dimen.dp8, vertical = Dimen.dp14),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = TacAccent,
            )
        }
        Spacer(Modifier.width(Dimen.dp8))
        Column {
            Text(
                text = title,
                color = TacAccent,
                fontSize = FontSize.sp11,
                fontWeight = FontWeight.Medium
            )
            Text(text = subtitle, color = TacTextMuted, fontSize = FontSize.sp13)
        }
    }
}

@Composable
private fun WeaponListRow(
    weapon: Weapon,
    onDelete: () -> Unit,
) {
    val badgeText = when (weapon.type) {
        WeaponType.PISTOL -> stringResource(Res.string.weapon_badge_pistol)
        WeaponType.REVOLVER -> stringResource(Res.string.weapon_badge_revolver)
        WeaponType.SHOTGUN -> stringResource(Res.string.weapon_badge_shotgun)
        WeaponType.RIFLE -> stringResource(Res.string.weapon_badge_rifle)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TacBgCard, RoundedCornerShape(Dimen.dp7))
            .border(Dimen.dp1, TacBorder, RoundedCornerShape(Dimen.dp7))
            .padding(horizontal = Dimen.dp14, vertical = Dimen.dp12),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(Dimen.dp38)
                .background(TacBgElevated, RoundedCornerShape(Dimen.dp7)),
            contentAlignment = Alignment.Center,
        ) {
            WeaponIcon(type = weapon.type, modifier = Modifier.size(Dimen.dp24))
        }
        Spacer(Modifier.width(Dimen.dp12))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = weapon.name,
                color = TacTextSecondary,
                fontSize = FontSize.sp13,
                fontWeight = FontWeight.Medium
            )
            Text(text = weapon.caliber, color = TacTextMuted, fontSize = FontSize.sp10)
        }
        Box(
            modifier = Modifier
                .background(TacBgElevated, RoundedCornerShape(Dimen.dp3))
                .padding(horizontal = Dimen.dp7, vertical = Dimen.dp2),
        ) {
            Text(text = badgeText, color = TacTextMuted, fontSize = FontSize.sp9)
        }
        Spacer(Modifier.width(Dimen.dp8))
        IconButton(onClick = onDelete) {
            Text(
                text = stringResource(pl.kacper.misterski.rangestats.core.ui.Res.string.core_x),
                color = TacRed,
                fontSize = FontSize.sp14
            )
        }
    }
}

@Preview
@Composable
private fun WeaponListScreenPreview() {
    RangeStatsTheme {
        WeaponListScreen(
            state = WeaponListUiModel(
                weapons = listOf(
                    Weapon("Glock 17", WeaponType.PISTOL, "9mm"),
                    Weapon("AR-15", WeaponType.RIFLE, "5.56mm"),
                ),
            ),
            onAction = {},
            onBack = {},
            showAddWeapon = { }
        )
    }
}
