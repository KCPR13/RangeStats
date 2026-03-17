package pl.kacper.misterski.rangestats.core.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.FontSize
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
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
        modifier = modifier.height(Dimen.dp56),
        enabled = enabled,
        shape = RoundedCornerShape(Dimen.dp8),
        colors = ButtonDefaults.buttonColors(
            containerColor = TacAccent,
            contentColor = TacOnAccent,
            disabledContainerColor = TacBgCard,
            disabledContentColor = TacBorderBright,
        ),
        contentPadding = PaddingValues(horizontal = Dimen.dp24, vertical = Dimen.dp12),
    ) {
        Text(text = text, fontSize = FontSize.sp18)
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
        modifier = modifier.height(Dimen.dp48),
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = TacTextPrimary,
            disabledContentColor = TacBorderBright,
        ),
        border = null,
        contentPadding = PaddingValues(horizontal = Dimen.dp24, vertical = Dimen.dp12),
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
