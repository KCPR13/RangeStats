package pl.kacper.misterski.rangestats.core.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.Spacing
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgCard
import pl.kacper.misterski.rangestats.core.ui.theme.TacBorderBright
import pl.kacper.misterski.rangestats.core.ui.theme.TacOnAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextPrimary

@Composable
fun TacButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(Spacing.dp48),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = TacAccent,
            contentColor = TacOnAccent,
            disabledContainerColor = TacBgCard,
            disabledContentColor = TacBorderBright,
        ),
        contentPadding = PaddingValues(horizontal = Spacing.dp24, vertical = Spacing.dp12),
    ) {
        Text(text = text)
    }
}

@Composable
fun TacSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(Spacing.dp48),
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = TacTextPrimary,
            disabledContentColor = TacBorderBright,
        ),
        border = BorderStroke(Spacing.dp1, if (enabled) TacBorderBright else TacBgCard),
        contentPadding = PaddingValues(horizontal = Spacing.dp24, vertical = Spacing.dp12),
    ) {
        Text(text = text)
    }
}

@Preview
@Composable
private fun TacButtonPreview() {
    RangeStatsTheme {
        TacButton(text = "Rozpocznij sesję", onClick = {})
    }
}

@Preview
@Composable
private fun TacButtonDisabledPreview() {
    RangeStatsTheme {
        TacButton(text = "Rozpocznij sesję", onClick = {}, enabled = false)
    }
}

@Preview
@Composable
private fun TacSecondaryButtonPreview() {
    RangeStatsTheme {
        TacSecondaryButton(text = "Anuluj", onClick = {})
    }
}
