package pl.kacper.misterski.rangestats.feature.session.ui.new

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource
import pl.kacper.misterski.rangestats.core.domain.enums.TargetType
import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.core.domain.models.Weapon
import pl.kacper.misterski.rangestats.core.ui.component.TacButton
import pl.kacper.misterski.rangestats.core.ui.component.TacChip
import pl.kacper.misterski.rangestats.core.ui.component.TacStepper
import pl.kacper.misterski.rangestats.core.ui.component.TacTextField
import pl.kacper.misterski.rangestats.core.ui.component.WeaponIcon
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.FontSize
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgCard
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgDeep
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgElevated
import pl.kacper.misterski.rangestats.core.ui.theme.TacBorder
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgPanel
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextMuted
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextSecondary
import rangestats.feature.session.generated.resources.Res
import rangestats.feature.session.generated.resources.common_checkmark
import rangestats.feature.session.generated.resources.common_nav_back
import rangestats.feature.session.generated.resources.new_session_add_weapon
import rangestats.feature.session.generated.resources.new_session_distance_label
import rangestats.feature.session.generated.resources.new_session_distance_label_format
import rangestats.feature.session.generated.resources.new_session_location_label
import rangestats.feature.session.generated.resources.new_session_location_placeholder
import rangestats.feature.session.generated.resources.new_session_start
import rangestats.feature.session.generated.resources.new_session_subtitle
import rangestats.feature.session.generated.resources.new_session_target_label
import rangestats.feature.session.generated.resources.new_session_title
import rangestats.feature.session.generated.resources.new_session_weapon_label
import rangestats.feature.session.generated.resources.target_bullseye
import rangestats.feature.session.generated.resources.target_issf
import rangestats.feature.session.generated.resources.target_silhouette

@Composable
fun NewSessionScreen(
    state: NewSessionUiModel,
    onAction: (NewSessionAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TacBgDeep),
    ) {
        NewSessionHeader(onBack = { onAction(NewSessionAction.Back) })
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Dimen.dp20, vertical = Dimen.dp16),
            verticalArrangement = Arrangement.spacedBy(Dimen.dp14),
        ) {
            SectionItem(label = stringResource(Res.string.new_session_location_label)) {
                TacTextField(
                    value = state.locationName,
                    onValueChange = { onAction(NewSessionAction.LocationChanged(it)) },
                    placeholder = stringResource(Res.string.new_session_location_placeholder),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            HorizontalDivider(color = TacBorder, thickness = Dimen.dp1)
            SectionItem(label = stringResource(Res.string.new_session_weapon_label)) {
                WeaponList(
                    weapons = state.weapons,
                    selectedWeaponName = state.selectedWeaponName,
                    onSelect = { onAction(NewSessionAction.WeaponSelected(it)) },
                )
            }
            HorizontalDivider(color = TacBorder, thickness = Dimen.dp1)
            SectionItem(label = stringResource(Res.string.new_session_distance_label)) {
                TacStepper(
                    value = state.distanceMeters,
                    onDecrement = { onAction(NewSessionAction.DecrementDistance) },
                    onIncrement = { onAction(NewSessionAction.IncrementDistance) },
                    min = 5, //  TODO K hardcoded
                    max = 300,
                    label = stringResource(Res.string.new_session_distance_label_format, state.distanceMeters),
                )
            }
            HorizontalDivider(color = TacBorder, thickness = Dimen.dp1)
            SectionItem(label = stringResource(Res.string.new_session_target_label)) {
                Row(horizontalArrangement = Arrangement.spacedBy(Dimen.dp8)) {
                    TargetType.entries.forEach { type ->
                        TacChip(
                            label = when (type) {
                                TargetType.ISSF_ROUND -> stringResource(Res.string.target_issf)
                                TargetType.SILHOUETTE -> stringResource(Res.string.target_silhouette)
                                TargetType.BULLSEYE -> stringResource(Res.string.target_bullseye)
                            },
                            selected = state.targetType == type,
                            onClick = { onAction(NewSessionAction.TargetTypeSelected(type)) },
                        )
                    }
                }
            }
            Spacer(Modifier.height(Dimen.dp4))
            TacButton(
                text = stringResource(Res.string.new_session_start),
                onClick = { onAction(NewSessionAction.StartSession) },
                enabled = state.canStart && !state.isLoading,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun NewSessionHeader(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TacBgPanel)
            .padding(horizontal = Dimen.dp20, vertical = Dimen.dp12),
        horizontalArrangement = Arrangement.spacedBy(Dimen.dp12),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .size(Dimen.dp32)
                .background(TacBgCard, RoundedCornerShape(Dimen.dp4))
                .border(Dimen.dp1, TacBorder, RoundedCornerShape(Dimen.dp4))
                .clickable(onClick = onBack),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = stringResource(Res.string.common_nav_back), color = TacTextMuted, fontSize = FontSize.sp18)
        }
        Column {
            Text(
                text = stringResource(Res.string.new_session_title),
                color = TacAccent,
                fontSize = FontSize.sp11,
                fontWeight = FontWeight.Medium,
                letterSpacing = androidx.compose.ui.unit.TextUnit( // TODO
                    0.12f,
                    androidx.compose.ui.unit.TextUnitType.Em,
                ),
            )
            Text(
                text = stringResource(Res.string.new_session_subtitle),
                color = TacTextMuted,
                fontSize = FontSize.sp13,
            )
        }
    }
}

@Composable
private fun SectionItem(
    label: String,
    content: @Composable () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(Dimen.dp8)) {
        Text(
            text = label,
            color = TacTextMuted,
            fontSize = FontSize.sp10,
            letterSpacing = androidx.compose.ui.unit.TextUnit(// TODO
                0.12f,
                androidx.compose.ui.unit.TextUnitType.Em,
            ),
        )
        content()
    }
}

@Composable
private fun WeaponList(
    weapons: List<Weapon>,
    selectedWeaponName: String?,
    onSelect: (String) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(Dimen.dp8)) {
        weapons.forEach { weapon ->
            WeaponRow(
                weapon = weapon,
                selected = weapon.name == selectedWeaponName,
                onClick = { onSelect(weapon.name) },
            )
        }
        Text(
            text = stringResource(Res.string.new_session_add_weapon),
            color = TacAccent,
            fontSize = FontSize.sp10,
        )
    }
}

@Composable
private fun WeaponRow(
    weapon: Weapon,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (selected) TacBgCard else TacBgCard,
                RoundedCornerShape(Dimen.dp8),
            )
            .border(
                Dimen.dp1,
                if (selected) TacAccent else TacBorder,
                RoundedCornerShape(Dimen.dp8),
            )
            .clickable(onClick = onClick)
            .padding(horizontal = Dimen.dp14, vertical = Dimen.dp12),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimen.dp12),
    ) {
        Box(
            modifier = Modifier
                .size(Dimen.dp38)
                .background(TacBgElevated, RoundedCornerShape(Dimen.dp8)),
            contentAlignment = Alignment.Center,
        ) {
            WeaponIcon(
                type = weapon.type,
                modifier = Modifier.size(Dimen.dp24),
                tint = if (selected) TacAccent else TacTextMuted,
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = weapon.name,
                color = if (selected) TacTextSecondary else TacTextMuted,
                fontSize = FontSize.sp13,
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = weapon.caliber,
                color = TacTextMuted,
                fontSize = FontSize.sp10,
            )
        }
       Box(
            modifier = Modifier
                .background(TacBgElevated, RoundedCornerShape(Dimen.dp3))
                .padding(horizontal = Dimen.dp7, vertical = Dimen.dp2),
        ) {
            Text(
                text = weapon.type.name.lowercase().replaceFirstChar { it.uppercase() },
                color = TacTextMuted,
                fontSize = FontSize.sp9,
            )
        }
        if (selected) {
            Text(text = stringResource(Res.string.common_checkmark), color = TacAccent, fontSize = FontSize.sp14)
        }
    }
}

@Preview
@Composable
private fun NewSessionScreenPreview() {
    RangeStatsTheme {
        NewSessionScreen(
            state = NewSessionUiModel(
                locationName = "Strzelnica Łódź",
                weapons = listOf(
                    Weapon( "Glock 17", WeaponType.PISTOL, "9mm", null),
                    Weapon( "AR-15", WeaponType.RIFLE, "5.56", null),
                ),
                selectedWeaponName = "1",
                distanceMeters = 25,
                targetType = TargetType.ISSF_ROUND,
                canStart = true,
            ),
            onAction = {},
        )
    }
}
