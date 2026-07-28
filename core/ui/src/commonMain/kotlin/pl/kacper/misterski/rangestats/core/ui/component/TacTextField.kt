package pl.kacper.misterski.rangestats.core.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pl.kacper.misterski.rangestats.core.ui.theme.PrecisionTrackShapes
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgCard
import pl.kacper.misterski.rangestats.core.ui.theme.TacBorder
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextMuted
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextPrimary

@Composable
fun TacTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        label = label?.let { { Text(it) } },
        placeholder = placeholder?.let { { Text(it) } },
        shape = PrecisionTrackShapes.small,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = TacBgCard,
            unfocusedContainerColor = TacBgCard,
            disabledContainerColor = TacBgCard,
            focusedBorderColor = TacAccent,
            unfocusedBorderColor = TacBorder,
            disabledBorderColor = TacBorder,
            focusedTextColor = TacTextPrimary,
            unfocusedTextColor = TacTextPrimary,
            disabledTextColor = TacTextMuted,
            focusedLabelColor = TacAccent,
            unfocusedLabelColor = TacTextMuted,
            focusedPlaceholderColor = TacTextMuted,
            unfocusedPlaceholderColor = TacTextMuted,
            cursorColor = TacAccent,
        ),
    )
}

@Preview
@Composable
private fun TacTextFieldPreview() {
    RangeStatsTheme {
        TacTextField(
            value = "AR-15",
            onValueChange = {},
            label = "Nazwa broni",
        )
    }
}

@Preview
@Composable
private fun TacTextFieldEmptyPreview() {
    RangeStatsTheme {
        TacTextField(
            value = "",
            onValueChange = {},
            label = "Nazwa broni",
            placeholder = "Wpisz nazwę...",
        )
    }
}
