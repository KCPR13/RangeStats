package pl.kacper.misterski.rangestats.core.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.FontSize
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgCard
import pl.kacper.misterski.rangestats.core.ui.theme.TacBorder
import pl.kacper.misterski.rangestats.core.ui.theme.TacOnAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextPrimary
import rangestats.core.ui.generated.resources.Res
import rangestats.core.ui.generated.resources.core_minus
import rangestats.core.ui.generated.resources.core_plus

@Composable
fun TacStepper(
    value: Int,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
    min: Int,
    max: Int,
    modifier: Modifier = Modifier,
    label: String = value.toString(),
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        FilledIconButton(
            onClick = onDecrement,
            enabled = value > min,
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = TacBgCard,
                contentColor = TacAccent,
                disabledContainerColor = TacBgCard,
                disabledContentColor = TacBorder,
            ),
        ) {
            Text(
                text = stringResource(Res.string.core_minus),
                fontSize = FontSize.sp20,
                color = if (value > min) TacAccent else TacBorder,
            )
        }
        Text(
            text = label,
            modifier = Modifier.width(Dimen.dp72),
            color = TacTextPrimary,
            textAlign = TextAlign.Center,
            fontSize = FontSize.sp16,
        )

        FilledIconButton(
            onClick = onIncrement,
            enabled = value < max,
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = TacAccent,
                contentColor = TacOnAccent,
                disabledContainerColor = TacBgCard,
                disabledContentColor = TacBorder,
            ),
        ) {
            Text(
                text = stringResource(Res.string.core_plus),
                fontSize = FontSize.sp20,
                color = if (value < max) TacOnAccent else TacBorder,
            )
        }
    }
}

@Preview
@Composable
private fun TacStepperPreview() {
    RangeStatsTheme {
        TacStepper(
            value = 25, onDecrement = {}, onIncrement = {},
            min = 0,
            max = 100,
        )
    }
}

@Preview
@Composable
private fun TacStepperAtMinPreview() {
    RangeStatsTheme {
        TacStepper(
            value = 0, onDecrement = {}, onIncrement = {}, min = 0,
            max = 100,
        )
    }
}
