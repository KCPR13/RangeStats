package pl.kacper.misterski.rangestats.feature.settings.ui.weapon.add

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource
import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.core.ui.component.TacButton
import pl.kacper.misterski.rangestats.core.ui.component.TacChip
import pl.kacper.misterski.rangestats.core.ui.component.TacTextField
import pl.kacper.misterski.rangestats.core.ui.core_x
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.FontSize
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgElevated
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgPanel
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextMuted
import rangestats.feature.settings.generated.resources.Res
import rangestats.feature.settings.generated.resources.add_weapon_name_label
import rangestats.feature.settings.generated.resources.add_weapon_name_placeholder
import rangestats.feature.settings.generated.resources.add_weapon_save
import rangestats.feature.settings.generated.resources.add_weapon_title
import rangestats.feature.settings.generated.resources.add_weapon_type_label
import rangestats.feature.settings.generated.resources.weapon_badge_pistol
import rangestats.feature.settings.generated.resources.weapon_badge_revolver
import rangestats.feature.settings.generated.resources.weapon_badge_rifle
import rangestats.feature.settings.generated.resources.weapon_badge_shotgun

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddWeaponSheet(
    state: AddWeaponUiModel,
    onAction: (AddWeaponAction) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = TacBgPanel,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = Dimen.dp20, vertical = Dimen.dp16),
            verticalArrangement = Arrangement.spacedBy(Dimen.dp16),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(Res.string.add_weapon_title),
                    color = TacAccent,
                    fontSize = FontSize.sp11,
                    fontWeight = FontWeight.Medium,
                )
                Box(
                    modifier = Modifier
                        .size(Dimen.dp24)
                        .background(TacBgElevated, RoundedCornerShape(Dimen.dp4))
                        .clickable(onClick = onDismiss),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(pl.kacper.misterski.rangestats.core.ui.Res.string.core_x),
                        color = TacTextMuted,
                        fontSize = FontSize.sp12
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(Dimen.dp7)) {
                FieldLabel(text = stringResource(Res.string.add_weapon_name_label))
                TacTextField(
                    value = state.name,
                    onValueChange = { onAction(AddWeaponAction.NameChanged(it)) },
                    placeholder = stringResource(Res.string.add_weapon_name_placeholder),
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(Dimen.dp7)) {
                FieldLabel(text = stringResource(Res.string.add_weapon_type_label))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(Dimen.dp7)) {
                    WeaponType.entries.forEach { type ->
                        val label = when (type) {
                            WeaponType.PISTOL -> stringResource(Res.string.weapon_badge_pistol)
                            WeaponType.REVOLVER -> stringResource(Res.string.weapon_badge_revolver)
                            WeaponType.SHOTGUN -> stringResource(Res.string.weapon_badge_shotgun)
                            WeaponType.RIFLE -> stringResource(Res.string.weapon_badge_rifle)
                        }
                        TacChip(
                            label = label,
                            selected = state.selectedType == type,
                            onClick = { onAction(AddWeaponAction.TypeSelected(type)) },
                        )
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(Dimen.dp7)) {
                FieldLabel(text = stringResource(state.ammoLabelRes))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(Dimen.dp7)) {
                    items(state.ammoOptions) { option ->
                        TacChip(
                            label = option.label,
                            selected = option.selected,
                            onClick = { onAction(AddWeaponAction.AmmoSelected(option)) },
                        )
                    }
                }
            }

            TacButton(
                text = stringResource(Res.string.add_weapon_save),
                onClick = { onAction(AddWeaponAction.Save) },
                enabled = state.name.isNotBlank() && !state.isSaving,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(Dimen.dp16))
        }
    }
}

@Composable
private fun FieldLabel(text: String) {
    Text(
        text = text.uppercase(),
        color = TacTextMuted,
        fontSize = FontSize.sp9,
    )
}

@Preview
@Composable
private fun AddWeaponSheetContentPreview() {
    RangeStatsTheme {
        Column(
            modifier = Modifier
                .background(TacBgPanel)
                .padding(Dimen.dp20),
            verticalArrangement = Arrangement.spacedBy(Dimen.dp16),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    "NOWA BROŃ", // TODO hardcoded
                    color = TacAccent,
                    fontSize = FontSize.sp11,
                    fontWeight = FontWeight.Medium
                )
            }
            TacTextField(value = "Glock 19", onValueChange = {}, label = "Nazwa własna")
        }
    }
}
