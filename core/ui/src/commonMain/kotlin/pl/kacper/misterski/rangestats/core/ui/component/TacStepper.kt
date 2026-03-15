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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.Spacing
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgCard
import pl.kacper.misterski.rangestats.core.ui.theme.TacBorder
import pl.kacper.misterski.rangestats.core.ui.theme.TacOnAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextPrimary

//TODO hardcoded
@Composable
fun TacStepper(
    value: Int,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier,
    min: Int = 0,
    max: Int = Int.MAX_VALUE,
    label: String = value.toString(),
) {
    Row(
        modifier = modifier.height(Spacing.dp48),
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
                text = "−",
                fontSize = 20.sp,
                color = if (value > min) TacAccent else TacBorder,
            )
        }

        Spacer(modifier = Modifier.width(Spacing.dp12))

        Text(
            text = label,
            modifier = Modifier.width(Spacing.dp72),
            color = TacTextPrimary,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
        )

        Spacer(modifier = Modifier.width(Spacing.dp12))

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
                text = "+",
                fontSize = 20.sp,
                color = if (value < max) TacOnAccent else TacBorder,
            )
        }
    }
}

@Preview
@Composable
private fun TacStepperPreview() {
    RangeStatsTheme {
        TacStepper(value = 25, onDecrement = {}, onIncrement = {})
    }
}

@Preview
@Composable
private fun TacStepperAtMinPreview() {
    RangeStatsTheme {
        TacStepper(value = 0, onDecrement = {}, onIncrement = {}, min = 0)
    }
}
