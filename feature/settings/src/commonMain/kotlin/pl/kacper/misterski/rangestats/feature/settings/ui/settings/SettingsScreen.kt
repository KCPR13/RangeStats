package pl.kacper.misterski.rangestats.feature.settings.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource
import pl.kacper.misterski.rangestats.core.domain.enums.UnitSystem
import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.core.domain.models.UserProfile
import pl.kacper.misterski.rangestats.core.ui.component.AnimatedLoader
import pl.kacper.misterski.rangestats.core.ui.component.TacButton
import pl.kacper.misterski.rangestats.core.ui.component.TacScaffold
import pl.kacper.misterski.rangestats.core.ui.component.toggle.TacSegmentedToggle
import pl.kacper.misterski.rangestats.core.ui.component.toggle.TacSegmentedToggleOptionUiModel
import pl.kacper.misterski.rangestats.core.ui.component.TacStepper
import pl.kacper.misterski.rangestats.core.ui.component.TacTopBar
import pl.kacper.misterski.rangestats.core.ui.component.WeaponIcon
import pl.kacper.misterski.rangestats.core.ui.component.toUiModel
import pl.kacper.misterski.rangestats.core.ui.core_placeholder_distance
import pl.kacper.misterski.rangestats.core.ui.core_x
import pl.kacper.misterski.rangestats.core.navigation.BottomNavDestination
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.FontSize
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgCard
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgElevated
import pl.kacper.misterski.rangestats.core.ui.theme.TacBorder
import pl.kacper.misterski.rangestats.core.ui.theme.TacRed
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextMuted
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextSecondary
import rangestats.feature.settings.generated.resources.Res
import rangestats.feature.settings.generated.resources.settings_add_weapon
import rangestats.feature.settings.generated.resources.settings_distance_label
import rangestats.feature.settings.generated.resources.settings_section_defaults
import rangestats.feature.settings.generated.resources.settings_section_weapons
import rangestats.feature.settings.generated.resources.settings_title
import rangestats.feature.settings.generated.resources.settings_units_label

@Composable
fun SettingsScreen(
    model: SettingsUiModel,
    onAction: (SettingsAction) -> Unit,
    onAddWeapon: () -> Unit,
    onBottomNavigate: (BottomNavDestination) -> Unit,
    modifier: Modifier = Modifier,
) {

    LaunchedEffect(Unit) {
        onAction(SettingsAction.OnStart)
    }

    TacScaffold(
        selectedNav = BottomNavDestination.Settings,
        onBottomNavigate = onBottomNavigate,
        modifier = modifier,
        topBar = { TacTopBar(title = stringResource(Res.string.settings_title)) },
    ) { padding ->
        AnimatedLoader(isLoading = model.isLoading, modifier = Modifier.padding(padding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            ) {
                Column(
                    modifier = Modifier.padding(vertical = Dimen.dp16),
                    verticalArrangement = Arrangement.spacedBy(Dimen.dp16),
                ) {
                    WeaponsSection(
                        weapons = model.weapons,
                        onAddWeapon = onAddWeapon,
                        onDeleteWeapon = { name -> onAction(SettingsAction.DeleteWeapon(name)) },
                    )
                    HorizontalDivider(color = TacBorder, thickness = Dimen.dp1)
                    DefaultSessionSection(model = model, onAction = onAction)
                }
            }
        }
    }
}

@Composable
private fun WeaponsSection(
    weapons: List<SettingsUiModel.WeaponUiModel>,
    onAddWeapon: () -> Unit,
    onDeleteWeapon: (String) -> Unit,
) {
    Column {
        SectionLabel(text = stringResource(Res.string.settings_section_weapons))
        Spacer(Modifier.height(Dimen.dp8))
        Column(verticalArrangement = Arrangement.spacedBy(Dimen.dp8)) {
            weapons.forEach { weapon ->
                WeaponRow(weapon = weapon, onDelete = { onDeleteWeapon(weapon.name) })
            }
            TacButton(
                text = stringResource(Res.string.settings_add_weapon),
                onClick = onAddWeapon,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun WeaponRow(weapon: SettingsUiModel.WeaponUiModel, onDelete: () -> Unit) {
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

@Composable
private fun DefaultSessionSection(
    model: SettingsUiModel,
    onAction: (SettingsAction) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(Dimen.dp8)) {
        SectionLabel(text = stringResource(Res.string.settings_section_defaults))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(TacBgCard, RoundedCornerShape(Dimen.dp8))
                .border(Dimen.dp1, TacBorder, RoundedCornerShape(Dimen.dp8))
                .padding(Dimen.dp14),
            verticalArrangement = Arrangement.spacedBy(Dimen.dp14),
        ) {
            DistanceRow(model = model, onAction = onAction)
            HorizontalDivider(color = TacBorder, thickness = Dimen.dp1)
            UnitsRow(
                unitOptions = model.unitOptions,
                onUnitSelected = { index -> onAction(SettingsAction.UnitSystemChanged(index)) },
            )
        }
    }
}

@Composable
private fun DistanceRow(
    model: SettingsUiModel,
    onAction: (SettingsAction) -> Unit,
) {
    val distanceDisplay = model.distanceDisplay
    val unitSuffix = stringResource(distanceDisplay.unitSuffix)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(Res.string.settings_distance_label),
            color = TacTextMuted,
            fontSize = FontSize.sp12,
        )
        TacStepper(
            value = distanceDisplay.value,
            onDecrement = { onAction(SettingsAction.DecrementDistance) },
            onIncrement = { onAction(SettingsAction.IncrementDistance) },
            min = distanceDisplay.min,
            max = distanceDisplay.max,
            label = stringResource(
                pl.kacper.misterski.rangestats.core.ui.Res.string.core_placeholder_distance,
                distanceDisplay.value,
                unitSuffix,
            ),
            buttonSize = Dimen.dp32,
            valueWidth = Dimen.dp48,
            valueFontSize = FontSize.sp12,
            iconFontSize = FontSize.sp16,
            onLabelClick = { onAction(SettingsAction.ShowDistanceEditDialog) },
        )
    }
    if (model.distanceEdit.isVisible) {
        DistanceEditDialog(
            inputText = model.distanceEdit.inputText,
            isValid = model.distanceEdit.isInputValid,
            unitSuffix = unitSuffix,
            min = distanceDisplay.min,
            max = distanceDisplay.max,
            onInputChanged = { onAction(SettingsAction.DistanceInputChanged(it)) },
            onDismiss = { onAction(SettingsAction.HideDistanceEditDialog) },
            onConfirm = { onAction(SettingsAction.ConfirmDistanceInput) },
        )
    }
}

@Composable
private fun UnitsRow(
    unitOptions: List<TacSegmentedToggleOptionUiModel>,
    onUnitSelected: (Int) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(Dimen.dp8)) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.settings_units_label),
            color = TacTextMuted,
            fontSize = FontSize.sp12,
            textAlign = TextAlign.Center
        )
        TacSegmentedToggle(
            options = unitOptions,
            onSelect = onUnitSelected,
            modifier = Modifier.padding(horizontal = Dimen.dp4),
            fullWidth = true,
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
                        WeaponType.PISTOL.toUiModel(),
                        "9mm"
                    ),
                    SettingsUiModel.WeaponUiModel(
                        "AR-15",
                        WeaponType.RIFLE.toUiModel(),
                        "5.56mm"
                    ),
                ),
            ),
            onAction = {},
            onAddWeapon = {},
            onBottomNavigate = {},
            modifier = Modifier,
        )
    }
}
