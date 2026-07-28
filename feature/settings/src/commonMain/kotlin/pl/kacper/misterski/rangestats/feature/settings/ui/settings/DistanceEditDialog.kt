package pl.kacper.misterski.rangestats.feature.settings.ui.settings

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import org.jetbrains.compose.resources.stringResource
import pl.kacper.misterski.rangestats.core.ui.component.TacDialog
import pl.kacper.misterski.rangestats.core.ui.component.TacTextField
import pl.kacper.misterski.rangestats.core.ui.theme.FontSize
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextMuted
import rangestats.feature.settings.generated.resources.Res
import rangestats.feature.settings.generated.resources.settings_distance_dialog_cancel
import rangestats.feature.settings.generated.resources.settings_distance_dialog_confirm
import rangestats.feature.settings.generated.resources.settings_distance_dialog_input_label
import rangestats.feature.settings.generated.resources.settings_distance_dialog_range_hint
import rangestats.feature.settings.generated.resources.settings_distance_dialog_title

@Composable
fun DistanceEditDialog(
    inputText: String,
    isValid: Boolean,
    unitSuffix: String,
    min: Int,
    max: Int,
    onInputChanged: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    TacDialog(
        title = stringResource(Res.string.settings_distance_dialog_title),
        onDismiss = onDismiss,
        onConfirm = onConfirm,
        confirmLabel = stringResource(Res.string.settings_distance_dialog_confirm),
        dismissLabel = stringResource(Res.string.settings_distance_dialog_cancel),
        confirmEnabled = isValid,
    ) {
        TacTextField(
            value = inputText,
            onValueChange = onInputChanged,
            label = stringResource(Res.string.settings_distance_dialog_input_label, unitSuffix),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        if (!isValid && inputText.isNotEmpty()) {
            Text(
                text = stringResource(
                    Res.string.settings_distance_dialog_range_hint,
                    min,
                    max,
                    unitSuffix,
                ),
                color = TacTextMuted,
                fontSize = FontSize.sp12,
            )
        }
    }
}
