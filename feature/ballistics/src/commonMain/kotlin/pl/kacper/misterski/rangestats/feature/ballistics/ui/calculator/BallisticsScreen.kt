package pl.kacper.misterski.rangestats.feature.ballistics.ui.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource
import pl.kacper.misterski.rangestats.core.ui.component.TacButton
import pl.kacper.misterski.rangestats.core.ui.component.TacChip
import pl.kacper.misterski.rangestats.core.ui.component.TacScaffold
import pl.kacper.misterski.rangestats.core.ui.component.toggle.TacSegmentedToggle
import pl.kacper.misterski.rangestats.core.ui.component.toggle.TacSegmentedToggleOptionUiModel
import pl.kacper.misterski.rangestats.core.ui.component.TacTextField
import pl.kacper.misterski.rangestats.core.ui.component.TacTopBar
import pl.kacper.misterski.rangestats.core.navigation.BottomNavDestination
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.FontSize
import pl.kacper.misterski.rangestats.core.ui.theme.PrecisionTrackShapes
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgCard
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgDeep
import pl.kacper.misterski.rangestats.core.ui.theme.TacBorder
import pl.kacper.misterski.rangestats.core.ui.theme.TacGreen
import pl.kacper.misterski.rangestats.core.ui.theme.TacRed
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextMuted
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextPrimary
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextSecondary
import pl.kacper.misterski.rangestats.core.ui.theme.rememberMonoStyles
import pl.kacper.misterski.rangestats.feature.ballistics.domain.model.BcModel
import rangestats.feature.ballistics.generated.resources.Res
import rangestats.feature.ballistics.generated.resources.ballistics_btn_calculate
import rangestats.feature.ballistics.generated.resources.ballistics_error_empty_fields
import rangestats.feature.ballistics.generated.resources.ballistics_field_bc
import rangestats.feature.ballistics.generated.resources.ballistics_field_bullet_mass
import rangestats.feature.ballistics.generated.resources.ballistics_field_distance
import rangestats.feature.ballistics.generated.resources.ballistics_field_muzzle_velocity
import rangestats.feature.ballistics.generated.resources.ballistics_field_scope_height
import rangestats.feature.ballistics.generated.resources.ballistics_field_zero_range
import rangestats.feature.ballistics.generated.resources.ballistics_placeholder_bc
import rangestats.feature.ballistics.generated.resources.ballistics_placeholder_bullet_mass
import rangestats.feature.ballistics.generated.resources.ballistics_placeholder_distance
import rangestats.feature.ballistics.generated.resources.ballistics_placeholder_muzzle_velocity
import rangestats.feature.ballistics.generated.resources.ballistics_result_drop
import rangestats.feature.ballistics.generated.resources.ballistics_result_drop_mm_format
import rangestats.feature.ballistics.generated.resources.ballistics_result_energy
import rangestats.feature.ballistics.generated.resources.ballistics_result_energy_format
import rangestats.feature.ballistics.generated.resources.ballistics_result_tof
import rangestats.feature.ballistics.generated.resources.ballistics_result_tof_format
import rangestats.feature.ballistics.generated.resources.ballistics_result_velocity
import rangestats.feature.ballistics.generated.resources.ballistics_result_velocity_format
import rangestats.feature.ballistics.generated.resources.ballistics_section_bullet_data
import rangestats.feature.ballistics.generated.resources.ballistics_section_result
import rangestats.feature.ballistics.generated.resources.ballistics_section_shot_data
import rangestats.feature.ballistics.generated.resources.ballistics_sign_down
import rangestats.feature.ballistics.generated.resources.ballistics_title

//TODO calculation is bad
@Composable
fun BallisticsScreen(
    state: BallisticsUiModel,
    onAction: (BallisticsAction) -> Unit,
    onBottomNavigate: (BottomNavDestination) -> Unit,
) {
    LaunchedEffect(Unit) {
        onAction(BallisticsAction.OnStart)
    }

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
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Dimen.dp16),
        ) {
            Spacer(Modifier.height(Dimen.dp16))
            PresetsSection(
                presets = state.presets,
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
            if (state.calculationErrorRes != null) {
                Text(
                    text = stringResource(state.calculationErrorRes),
                    color = TacRed,
                    fontSize = FontSize.sp12,
                )
            }

            TacButton(
                text = stringResource(Res.string.ballistics_btn_calculate),
                onClick = { onAction(BallisticsAction.Calculate) },
                enabled = state.isCalculateEnabled,
                modifier = Modifier.fillMaxWidth(),
            )

            if (state.result != null) {
                ResultSection(result = state.result)
            }

            Spacer(modifier = Modifier.height(Dimen.dp16))
        }
    }
}

@Composable
private fun PresetsSection(
    presets: List<BallisticsUiModel.CaliberPresetUiItem>,
    onSelect: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(Dimen.dp8),
    ) {
        presets.forEach { preset ->
            TacChip(
                label = preset.name,
                selected = preset.selected,
                onClick = { onSelect(preset.name) },
            )
        }
    }
}

@Composable
private fun InputsSection(
    state: BallisticsUiModel,
    onAction: (BallisticsAction) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val muzzleVelocityFocus = remember { FocusRequester() } // TODO ugly
    val bulletMassFocus = remember { FocusRequester() }
    val ballisticCoefficientFocus = remember { FocusRequester() }
    val zeroRangeFocus = remember { FocusRequester() }
    val targetDistanceFocus = remember { FocusRequester() }
    val scopeHeightFocus = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(TacBgCard, PrecisionTrackShapes.large)
            .border(Dimen.dp1, TacBorder, PrecisionTrackShapes.large)
            .padding(Dimen.dp16),
        verticalArrangement = Arrangement.spacedBy(Dimen.dp12),
    ) {
        SectionLabel(stringResource(Res.string.ballistics_section_bullet_data))
        TacTextField(
            value = state.muzzleVelocity,
            onValueChange = { onAction(BallisticsAction.UpdateMuzzleVelocity(it)) },
            modifier = Modifier.focusRequester(muzzleVelocityFocus),
            label = stringResource(Res.string.ballistics_field_muzzle_velocity),
            placeholder = stringResource(Res.string.ballistics_placeholder_muzzle_velocity),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { bulletMassFocus.requestFocus() }),
        )
        TacTextField(
            value = state.bulletMass,
            onValueChange = { onAction(BallisticsAction.UpdateBulletMass(it)) },
            modifier = Modifier.focusRequester(bulletMassFocus),
            label = stringResource(Res.string.ballistics_field_bullet_mass),
            placeholder = stringResource(Res.string.ballistics_placeholder_bullet_mass),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { ballisticCoefficientFocus.requestFocus() }),
        )
        BcInputField(
            value = state.ballisticCoefficient,
            bcModelOptions = state.bcModelOptions,
            onBcModelSelected = { index -> onAction(BallisticsAction.SelectBcModel(index)) },
            onValueChange = { onAction(BallisticsAction.UpdateBallisticCoefficient(it)) },
            focusRequester = ballisticCoefficientFocus,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { zeroRangeFocus.requestFocus() }),
        )

        HorizontalDivider(color = TacBorder, thickness = Dimen.dp1)

        SectionLabel(stringResource(Res.string.ballistics_section_shot_data))
        TacTextField(
            value = state.zeroRange,
            onValueChange = { onAction(BallisticsAction.UpdateZeroRange(it)) },
            modifier = Modifier.focusRequester(zeroRangeFocus),
            label = stringResource(Res.string.ballistics_field_zero_range),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { targetDistanceFocus.requestFocus() }),
        )
        TacTextField(
            value = state.targetDistance,
            onValueChange = { onAction(BallisticsAction.UpdateTargetDistance(it)) },
            modifier = Modifier.focusRequester(targetDistanceFocus),
            label = stringResource(Res.string.ballistics_field_distance),
            placeholder = stringResource(Res.string.ballistics_placeholder_distance),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { scopeHeightFocus.requestFocus() }),
        )
        TacTextField(
            value = state.scopeHeight,
            onValueChange = { onAction(BallisticsAction.UpdateScopeHeight(it)) },
            modifier = Modifier.focusRequester(scopeHeightFocus),
            label = stringResource(Res.string.ballistics_field_scope_height),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    onAction(BallisticsAction.Calculate)
                },
            ),
        )
    }
}

@Composable
private fun BcInputField(
    value: String,
    bcModelOptions: List<TacSegmentedToggleOptionUiModel>,
    onBcModelSelected: (Int) -> Unit,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
) {
    Column(verticalArrangement = Arrangement.spacedBy(Dimen.dp4)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(Res.string.ballistics_field_bc),
                color = TacTextMuted,
                fontSize = FontSize.sp12,
            )
            TacSegmentedToggle(options = bcModelOptions, onSelect = onBcModelSelected)
        }
        TacTextField(
            modifier = Modifier
                .padding(top = Dimen.dp8)
                .focusRequester(focusRequester),
            value = value,
            onValueChange = onValueChange,
            placeholder = stringResource(Res.string.ballistics_placeholder_bc),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
        )
    }
}

@Composable
private fun ResultSection(result: BallisticsUiModel.BallisticsResultUiModel) {
    Column(verticalArrangement = Arrangement.spacedBy(Dimen.dp8)) {
        SectionLabel(stringResource(Res.string.ballistics_section_result))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Dimen.dp8),
        ) {
            ResultCard(
                label = stringResource(Res.string.ballistics_result_drop),
                value = stringResource(
                    result.dropFormatRes,
                    stringResource(result.dropSignRes),
                    result.dropDisplayValue,
                ),
                valueColor = if (result.dropAboveLoS) TacGreen else TacRed,
                modifier = Modifier.weight(1f),
            )
            ResultCard(
                label = stringResource(Res.string.ballistics_result_velocity),
                value = stringResource(Res.string.ballistics_result_velocity_format, result.remainingVelocityMs),
                modifier = Modifier.weight(1f),
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Dimen.dp8),
        ) {
            ResultCard(
                label = stringResource(Res.string.ballistics_result_energy),
                value = stringResource(Res.string.ballistics_result_energy_format, result.energyJoules),
                modifier = Modifier.weight(1f),
            )
            ResultCard(
                label = stringResource(Res.string.ballistics_result_tof),
                value = stringResource(Res.string.ballistics_result_tof_format, result.timeOfFlightDisplayValue),
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
        style = rememberMonoStyles().sectionLabel,
        color = TacTextSecondary,
    )
}

@Preview
@Composable
private fun BallisticsScreenEmptyPreview() {
    RangeStatsTheme {
        BallisticsScreen(
            state = BallisticsUiModel(
                presets = listOf(
                    BallisticsUiModel.CaliberPresetUiItem(".223 Remington"),
                    BallisticsUiModel.CaliberPresetUiItem("5.56x45 NATO"),
                    BallisticsUiModel.CaliberPresetUiItem("6.5 Creedmoor"),
                    BallisticsUiModel.CaliberPresetUiItem(".308 Win"),
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
                presets = listOf(
                    BallisticsUiModel.CaliberPresetUiItem(".223 Remington"),
                    BallisticsUiModel.CaliberPresetUiItem("5.56x45 NATO"),
                    BallisticsUiModel.CaliberPresetUiItem("6.5 Creedmoor"),
                    BallisticsUiModel.CaliberPresetUiItem(".308 Win", selected = true),
                ),
                muzzleVelocity = "800",
                bulletMass = "175",
                ballisticCoefficient = "0.475",
                bcModel = BcModel.G7,
                zeroRange = "100",
                targetDistance = "300",
                scopeHeight = "38",
                result = BallisticsUiModel.BallisticsResultUiModel(
                    dropFormatRes = Res.string.ballistics_result_drop_mm_format,
                    dropSignRes = Res.string.ballistics_sign_down,
                    dropDisplayValue = "312",
                    dropAboveLoS = false,
                    remainingVelocityMs = 712,
                    energyJoules = 2451,
                    timeOfFlightDisplayValue = "0.412",
                ),
            ),
            onAction = {},
            onBottomNavigate = {},
        )
    }
}
