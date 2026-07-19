package pl.kacper.misterski.rangestats.feature.settings.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource
import pl.kacper.misterski.rangestats.core.domain.enums.UnitSystem
import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.core.domain.models.UserProfile
import pl.kacper.misterski.rangestats.core.ui.component.AnimatedLoader
import pl.kacper.misterski.rangestats.core.ui.component.TacStepper
import pl.kacper.misterski.rangestats.core.ui.component.WeaponIcon
import pl.kacper.misterski.rangestats.core.ui.component.toUiModel
import pl.kacper.misterski.rangestats.core.ui.core_placeholder_distance
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.FontSize
import pl.kacper.misterski.rangestats.core.ui.theme.LetterSpacing.em12
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgCard
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgDeep
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgElevated
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgPanel
import pl.kacper.misterski.rangestats.core.ui.theme.TacBorder
import pl.kacper.misterski.rangestats.core.ui.theme.TacOnAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextMuted
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextPrimary
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextSecondary
import rangestats.feature.settings.generated.resources.Res
import rangestats.feature.settings.generated.resources.settings_add_weapon
import rangestats.feature.settings.generated.resources.settings_distance_label
import rangestats.feature.settings.generated.resources.settings_edit
import rangestats.feature.settings.generated.resources.settings_imperial
import rangestats.feature.settings.generated.resources.settings_metric
import rangestats.feature.settings.generated.resources.settings_section_defaults
import rangestats.feature.settings.generated.resources.settings_section_profile
import rangestats.feature.settings.generated.resources.settings_section_weapons
import rangestats.feature.settings.generated.resources.settings_title
import rangestats.feature.settings.generated.resources.settings_units_label

@Composable
fun SettingsScreen(
    model: SettingsUiModel,
    onAction: (SettingsAction) -> Unit,
    onNavigateToWeaponList: () -> Unit,
    modifier: Modifier = Modifier,
) {

    LaunchedEffect(Unit) {
        onAction(SettingsAction.OnStart)
    }

    AnimatedLoader(isLoading = model.isLoading, modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(TacBgDeep)
                .verticalScroll(rememberScrollState()),
        ) {
            SettingsHeader()
            Column(
                modifier = Modifier.padding(horizontal = Dimen.dp20, vertical = Dimen.dp16),
                verticalArrangement = Arrangement.spacedBy(Dimen.dp16),
            ) {
                ProfileSection(model = model)
                HorizontalDivider(color = TacBorder, thickness = Dimen.dp1)
                WeaponsSection(
                    weapons = model.weapons,
                    onAddWeapon = onNavigateToWeaponList,
                )
                HorizontalDivider(color = TacBorder, thickness = Dimen.dp1)
                DefaultSessionSection(
                    distanceMeters = model.profile.defaultDistanceMeters,
                    units = model.profile.units,
                    onAction = onAction,
                )
            }
        }
    }
}

@Composable
private fun SettingsHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(TacBgPanel)
            .padding(horizontal = Dimen.dp20, vertical = Dimen.dp14),
    ) {
        Text(
            text = stringResource(Res.string.settings_title),
            color = TacAccent,
            fontSize = FontSize.sp11,
            fontWeight = FontWeight.Medium,
            letterSpacing = em12,
        )
    }
}

@Composable
private fun ProfileSection(model: SettingsUiModel) {
    Column {
        SectionLabel(text = stringResource(Res.string.settings_section_profile))
        Spacer(Modifier.height(Dimen.dp8))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(TacBgCard, RoundedCornerShape(Dimen.dp8))
                .border(Dimen.dp1, TacBorder, RoundedCornerShape(Dimen.dp8))
                .padding(Dimen.dp14),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(Dimen.dp48)
                    .background(TacBgElevated, RoundedCornerShape(Dimen.dp8))
                    .border(Dimen.dp1, TacBorder, RoundedCornerShape(Dimen.dp8)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = model.displayNameAbbreviation,
                    color = TacAccent,
                    fontSize = FontSize.sp18,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Spacer(Modifier.width(Dimen.dp14))
            Text(
                text = model.profile.displayName,
                color = TacTextPrimary,
                fontSize = FontSize.sp14,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f),
            )
            Box(
                modifier = Modifier
                    .background(TacBgElevated, RoundedCornerShape(Dimen.dp4))
                    .border(Dimen.dp1, TacBorder, RoundedCornerShape(Dimen.dp4))
                    .padding(horizontal = Dimen.dp8, vertical = Dimen.dp7),
            ) {
                Text(
                    text = stringResource(Res.string.settings_edit),
                    color = TacAccent,
                    fontSize = FontSize.sp9,
                )
            }
        }
    }
}

@Composable
private fun WeaponsSection(
    weapons: List<SettingsUiModel.WeaponUiModel>,
    onAddWeapon: () -> Unit,
) {
    Column {
        SectionLabel(text = stringResource(Res.string.settings_section_weapons))
        Spacer(Modifier.height(Dimen.dp8))
        Column(verticalArrangement = Arrangement.spacedBy(Dimen.dp8)) {
            weapons.forEach { weapon ->
                WeaponRow(weapon = weapon)
            }
            AddWeaponButton(
                label = stringResource(Res.string.settings_add_weapon),
                onClick = onAddWeapon,
            )
        }
    }
}

@Composable
private fun WeaponRow(weapon: SettingsUiModel.WeaponUiModel) {
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
            WeaponIcon(model = weapon.icon, modifier = Modifier.size(Dimen.dp24))
        }
        Spacer(Modifier.width(Dimen.dp12))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = weapon.name,
                color = TacTextSecondary,
                fontSize = FontSize.sp13,
                fontWeight = FontWeight.Medium
            )
            Text(text = weapon.ammoLabel, color = TacTextMuted, fontSize = FontSize.sp10)
        }
        Box(
            modifier = Modifier
                .background(TacBgElevated, RoundedCornerShape(Dimen.dp3))
                .padding(horizontal = Dimen.dp7, vertical = Dimen.dp2),
        ) {
            Text(text = weapon.badgeText, color = TacTextMuted, fontSize = FontSize.sp9)
        }
    }
}

@Composable
private fun AddWeaponButton(label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimen.dp7))
            .border(Dimen.dp1, TacBorder, RoundedCornerShape(Dimen.dp7))
            .background(TacBgCard)
            .clickable(onClick = onClick)
            .padding(Dimen.dp12),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            color = TacTextMuted,
            fontSize = FontSize.sp10,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun DefaultSessionSection(
    distanceMeters: Int,
    units: UnitSystem,
    onAction: (SettingsAction) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(Dimen.dp8)) {
        SectionLabel(text = stringResource(Res.string.settings_section_defaults))
        Row(
            horizontalArrangement = Arrangement.spacedBy(Dimen.dp8),
            modifier = Modifier.height(IntrinsicSize.Max)
        ) {
            DistanceTile(
                distanceMeters = distanceMeters,
                onDecrement = { onAction(SettingsAction.DecrementDistance) },
                onIncrement = { onAction(SettingsAction.IncrementDistance) },
                modifier = Modifier.weight(1f),
            )
            UnitsTile(
                units = units,
                onAction = onAction,
                modifier = Modifier.weight(1f).fillMaxHeight(),
            )
        }

    }
}

@Composable
private fun DistanceTile(
    distanceMeters: Int,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(TacBgCard, RoundedCornerShape(Dimen.dp8))
            .border(Dimen.dp1, TacBorder, RoundedCornerShape(Dimen.dp8))
            .padding(vertical = Dimen.dp14),

        verticalArrangement = Arrangement.spacedBy(Dimen.dp8)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.settings_distance_label),
            color = TacTextMuted,
            fontSize = FontSize.sp12,
            textAlign = TextAlign.Center
        )
        TacStepper(
            modifier = Modifier.fillMaxSize(),
            value = distanceMeters,
            onDecrement = onDecrement,
            onIncrement = onIncrement,
            min = 5,
            max = 300,
            label = stringResource(
                pl.kacper.misterski.rangestats.core.ui.Res.string.core_placeholder_distance,
                distanceMeters
            )
        )
    }
}

@Composable
private fun UnitsTile(
    units: UnitSystem,
    onAction: (SettingsAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(TacBgCard, RoundedCornerShape(Dimen.dp8))
            .border(Dimen.dp1, TacBorder, RoundedCornerShape(Dimen.dp8))
            .padding(vertical = Dimen.dp14),
        verticalArrangement = Arrangement.spacedBy(Dimen.dp8)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.settings_units_label),
            color = TacTextMuted,
            fontSize = FontSize.sp12,
            textAlign = TextAlign.Center

        )
        UnitToggle(units = units, onAction = onAction)
    }
}

@Composable
private fun UnitToggle(
    units: UnitSystem,
    onAction: (SettingsAction) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = Dimen.dp4)
            .background(TacBgElevated, RoundedCornerShape(Dimen.dp4)),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        UnitOption(
            label = stringResource(Res.string.settings_metric),
            selected = units == UnitSystem.METRIC,
            onClick = { onAction(SettingsAction.UnitSystemChanged(UnitSystem.METRIC)) },
        )
        UnitOption(
            label = stringResource(Res.string.settings_imperial),
            selected = units == UnitSystem.IMPERIAL,
            onClick = { onAction(SettingsAction.UnitSystemChanged(UnitSystem.IMPERIAL)) },
        )
    }
}

@Composable
private fun UnitOption(label: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(Dimen.dp3))
            .background(if (selected) TacAccent else TacBgElevated)
            .clickable(onClick = onClick)
            .padding(Dimen.dp12, vertical = Dimen.dp5),
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = label,
            color = if (selected) TacOnAccent else TacTextMuted,
            fontSize = FontSize.sp10,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text.uppercase(),
        color = TacTextMuted,
        fontSize = FontSize.sp10,
    )
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    RangeStatsTheme {
        SettingsScreen(
            model = SettingsUiModel(
                profile = UserProfile("Operator", UnitSystem.METRIC, 25),
                weapons = listOf(
                    SettingsUiModel.WeaponUiModel(
                        "Glock 17",
                        "pistol",
                        WeaponType.PISTOL.toUiModel(),
                        "9mm"
                    ),
                    SettingsUiModel.WeaponUiModel(
                        "AR-15",
                        "carbine",
                        WeaponType.RIFLE.toUiModel(),
                        "5.56mm"
                    ),
                ),
            ),
            onAction = {},
            onNavigateToWeaponList = {},
        )
    }
}
