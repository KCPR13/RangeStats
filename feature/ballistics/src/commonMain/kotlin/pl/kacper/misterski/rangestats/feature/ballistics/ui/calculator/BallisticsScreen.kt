package pl.kacper.misterski.rangestats.feature.ballistics.ui.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource
import pl.kacper.misterski.rangestats.core.ui.component.TacButton
import pl.kacper.misterski.rangestats.core.ui.component.TacChip
import pl.kacper.misterski.rangestats.core.ui.component.TacScaffold
import pl.kacper.misterski.rangestats.core.ui.component.TacTextField
import pl.kacper.misterski.rangestats.core.ui.component.TacTopBar
import pl.kacper.misterski.rangestats.core.ui.enums.BottomNavDestination
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.FontSize
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgCard
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgDeep
import pl.kacper.misterski.rangestats.core.ui.theme.TacBorder
import pl.kacper.misterski.rangestats.core.ui.theme.TacGreen
import pl.kacper.misterski.rangestats.core.ui.theme.TacRed
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextMuted
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextPrimary
import pl.kacper.misterski.rangestats.core.ui.theme.PrecisionTrackShapes
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextSecondary
import rangestats.feature.ballistics.generated.resources.Res
import rangestats.feature.ballistics.generated.resources.ballistics_btn_calculate
import rangestats.feature.ballistics.generated.resources.ballistics_field_bc
import rangestats.feature.ballistics.generated.resources.ballistics_field_bullet_mass
import rangestats.feature.ballistics.generated.resources.ballistics_field_distance
import rangestats.feature.ballistics.generated.resources.ballistics_field_muzzle_velocity
import rangestats.feature.ballistics.generated.resources.ballistics_field_scope_height
import rangestats.feature.ballistics.generated.resources.ballistics_field_zero_range
import rangestats.feature.ballistics.generated.resources.ballistics_result_energy
import rangestats.feature.ballistics.generated.resources.ballistics_result_drop
import rangestats.feature.ballistics.generated.resources.ballistics_result_tof
import rangestats.feature.ballistics.generated.resources.ballistics_result_velocity
import rangestats.feature.ballistics.generated.resources.ballistics_section_presets
import rangestats.feature.ballistics.generated.resources.ballistics_error_empty_fields
import rangestats.feature.ballistics.generated.resources.ballistics_section_result
import rangestats.feature.ballistics.generated.resources.ballistics_title

@Composable
fun BallisticsScreen(
    state: BallisticsUiModel,
    onAction: (BallisticsAction) -> Unit,
    onBottomNavigate: (BottomNavDestination) -> Unit,
) {
    TacScaffold(
        selectedNav = BottomNavDestination.Ballistics,
        onBottomNavigate = onBottomNavigate,
        topBar = { TacTopBar(title = stringResource(Res.string.ballistics_title)) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(TacBgDeep)
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(Dimen.dp16),
        ) {
            PresetsSection(
                presets = state.presets,
                selectedIndex = state.selectedPresetIndex,
                onSelect = { onAction(BallisticsAction.SelectPreset(it)) },
            )

            InputsSection(state = state, onAction = onAction)

            if (state.showEmptyFieldsError) {
                Text(
                    text = stringResource(Res.string.ballistics_error_empty_fields),
                    color = TacRed,
                    fontSize = FontSize.sp12,
                )
            }
            if (state.calculationError != null) {
                Text(
                    text = state.calculationError,
                    color = TacRed,
                    fontSize = FontSize.sp12,
                )
            }

            TacButton(
                text = stringResource(Res.string.ballistics_btn_calculate),
                onClick = { onAction(BallisticsAction.Calculate) },
                modifier = Modifier.fillMaxWidth(),
            )

            if (state.result != null) {
                ResultSection(result = state.result)
            }

            Spacer(modifier = Modifier.height(Dimen.dp16))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PresetsSection(
    presets: List<CaliberPresetUiItem>,
    selectedIndex: Int?,
    onSelect: (Int) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(Dimen.dp8)) {
        SectionLabel(stringResource(Res.string.ballistics_section_presets))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(Dimen.dp8),
            verticalArrangement = Arrangement.spacedBy(Dimen.dp8),
        ) {
            presets.forEachIndexed { index, preset ->
                TacChip(
                    label = preset.name,
                    selected = selectedIndex == index,
                    onClick = { onSelect(index) },
                )
            }
        }
    }
}

@Composable
private fun InputsSection(
    state: BallisticsUiModel,
    onAction: (BallisticsAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(TacBgCard, PrecisionTrackShapes.large)
            .border(Dimen.dp1, TacBorder, PrecisionTrackShapes.large)
            .padding(Dimen.dp16),
        verticalArrangement = Arrangement.spacedBy(Dimen.dp12),
    ) {
        TacTextField(
            value = state.muzzleVelocity,
            onValueChange = { onAction(BallisticsAction.UpdateMuzzleVelocity(it)) },
            label = stringResource(Res.string.ballistics_field_muzzle_velocity),
        )
        TacTextField(
            value = state.bulletMass,
            onValueChange = { onAction(BallisticsAction.UpdateBulletMass(it)) },
            label = stringResource(Res.string.ballistics_field_bullet_mass),
        )
        TacTextField(
            value = state.ballisticCoefficient,
            onValueChange = { onAction(BallisticsAction.UpdateBallisticCoefficient(it)) },
            label = stringResource(Res.string.ballistics_field_bc),
        )
        TacTextField(
            value = state.zeroRange,
            onValueChange = { onAction(BallisticsAction.UpdateZeroRange(it)) },
            label = stringResource(Res.string.ballistics_field_zero_range),
        )
        TacTextField(
            value = state.targetDistance,
            onValueChange = { onAction(BallisticsAction.UpdateTargetDistance(it)) },
            label = stringResource(Res.string.ballistics_field_distance),
        )
        TacTextField(
            value = state.scopeHeight,
            onValueChange = { onAction(BallisticsAction.UpdateScopeHeight(it)) },
            label = stringResource(Res.string.ballistics_field_scope_height),
        )
    }
}

@Composable
private fun ResultSection(result: BallisticsResultUiModel) {
    Column(verticalArrangement = Arrangement.spacedBy(Dimen.dp8)) {
        SectionLabel(stringResource(Res.string.ballistics_section_result))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Dimen.dp8),
        ) {
            ResultCard(
                label = stringResource(Res.string.ballistics_result_drop),
                value = result.dropLabel,
                valueColor = if (result.dropAboveLoS) TacGreen else TacRed,
                modifier = Modifier.weight(1f),
            )
            ResultCard(
                label = stringResource(Res.string.ballistics_result_velocity),
                value = result.velocityLabel,
                modifier = Modifier.weight(1f),
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Dimen.dp8),
        ) {
            ResultCard(
                label = stringResource(Res.string.ballistics_result_energy),
                value = result.energyLabel,
                modifier = Modifier.weight(1f),
            )
            ResultCard(
                label = stringResource(Res.string.ballistics_result_tof),
                value = result.timeOfFlightLabel,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun ResultCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    valueColor: androidx.compose.ui.graphics.Color = TacTextPrimary,
) {
    Column(
        modifier = modifier
            .background(TacBgCard, PrecisionTrackShapes.large)
            .border(Dimen.dp1, TacBorder, PrecisionTrackShapes.large)
            .padding(horizontal = Dimen.dp12, vertical = Dimen.dp12),
        verticalArrangement = Arrangement.spacedBy(Dimen.dp4),
    ) {
        Text(
            text = label,
            color = TacTextMuted,
            fontSize = FontSize.sp10,
        )
        Text(
            text = value,
            color = valueColor,
            fontSize = FontSize.sp18,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        color = TacTextSecondary,
        fontSize = FontSize.sp10,
        fontWeight = FontWeight.Medium,
    )
}

@Preview
@Composable
private fun BallisticsScreenEmptyPreview() {
    RangeStatsTheme {
        BallisticsScreen(
            state = BallisticsUiModel(
                presets = listOf(
                    CaliberPresetUiItem("9mm Para"),
                    CaliberPresetUiItem(".308 Win"),
                ),
            ),
            onAction = {},
            onBottomNavigate = {},
        )
    }
}

@Preview
@Composable
private fun BallisticsScreenWithResultPreview() {
    RangeStatsTheme {
        BallisticsScreen(
            state = BallisticsUiModel(
                presets = listOf(CaliberPresetUiItem(".308 Win")),
                selectedPresetIndex = 0,
                muzzleVelocity = "800",
                bulletMass = "175",
                ballisticCoefficient = "0.475",
                zeroRange = "100",
                targetDistance = "300",
                scopeHeight = "38",
                result = BallisticsResultUiModel(
                    dropLabel = "↓ 312 mm",
                    dropAboveLoS = false,
                    velocityLabel = "712 m/s",
                    energyLabel = "2451 J",
                    timeOfFlightLabel = "0.412 s",
                ),
            ),
            onAction = {},
            onBottomNavigate = {},
        )
    }
}
