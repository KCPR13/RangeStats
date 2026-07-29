package pl.kacper.misterski.rangestats.feature.settings.ui.settings

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
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
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(text = inputText, selection = TextRange(inputText.length)))
    }
    LaunchedEffect(inputText) {
        if (inputText != textFieldValue.text) {
            textFieldValue = TextFieldValue(text = inputText, selection = TextRange(inputText.length))
        }
    }

    TacDialog(
        title = stringResource(Res.string.settings_distance_dialog_title),
        onDismiss = onDismiss,
        onConfirm = onConfirm,
        confirmLabel = stringResource(Res.string.settings_distance_dialog_confirm),
        dismissLabel = stringResource(Res.string.settings_distance_dialog_cancel),
        confirmEnabled = isValid,
    ) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
            keyboardController?.show()
        }
        TacTextField(
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
                onInputChanged(it.text)
            },
            modifier = Modifier.focusRequester(focusRequester),
            label = stringResource(Res.string.settings_distance_dialog_input_label, unitSuffix),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { if (isValid) onConfirm() }),
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
