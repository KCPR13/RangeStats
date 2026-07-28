package pl.kacper.misterski.rangestats.core.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import pl.kacper.misterski.rangestats.core.ui.Res
import pl.kacper.misterski.rangestats.core.ui.core_minus
import pl.kacper.misterski.rangestats.core.ui.core_plus
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.FontSize
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgCard
import pl.kacper.misterski.rangestats.core.ui.theme.TacBorder
import pl.kacper.misterski.rangestats.core.ui.theme.TacOnAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextPrimary
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun TacStepper(
    value: Int,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
    min: Int,
    max: Int,
    modifier: Modifier = Modifier,
    label: String = value.toString(),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    buttonSize: Dp = Dimen.dp40,
    valueWidth: Dp = Dimen.dp72,
    valueFontSize: TextUnit = FontSize.sp16,
    iconFontSize: TextUnit = FontSize.sp20,
    onLabelClick: (() -> Unit)? = null,
) {
    val haptics = LocalHapticFeedback.current
    val decrementInteractionSource = remember { MutableInteractionSource() }
    val incrementInteractionSource = remember { MutableInteractionSource() }

    RepeatOnHoldEffect(
        interactionSource = decrementInteractionSource,
        enabled = { value > min },
        onClick = onDecrement,
        onRepeatTick = { haptics.performHapticFeedback(HapticFeedbackType.LongPress) },
    )
    RepeatOnHoldEffect(
        interactionSource = incrementInteractionSource,
        enabled = { value < max },
        onClick = onIncrement,
        onRepeatTick = { haptics.performHapticFeedback(HapticFeedbackType.LongPress) },
    )

    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        FilledIconButton(
            onClick = onDecrement,
            enabled = value > min,
            interactionSource = decrementInteractionSource,
            modifier = Modifier.size(buttonSize),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = TacAccent,
                contentColor = TacOnAccent,
                disabledContainerColor = TacBgCard,
                disabledContentColor = TacBorder,
            ),
        ) {
            Text(
                text = stringResource(Res.string.core_minus),
                fontSize = iconFontSize,
                color = if (value > min) TacOnAccent else TacBorder,
            )
        }
        Text(
            text = label,
            modifier = Modifier
                .width(valueWidth)
                .then(onLabelClick?.let { Modifier.clickable(onClick = it) } ?: Modifier),
            color = TacTextPrimary,
            textAlign = TextAlign.Center,
            fontSize = valueFontSize,
        )

        FilledIconButton(
            onClick = onIncrement,
            enabled = value < max,
            interactionSource = incrementInteractionSource,
            modifier = Modifier.size(buttonSize),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = TacAccent,
                contentColor = TacOnAccent,
                disabledContainerColor = TacBgCard,
                disabledContentColor = TacBorder,
            ),
        ) {
            Text(
                text = stringResource(Res.string.core_plus),
                fontSize = iconFontSize,
                color = if (value < max) TacOnAccent else TacBorder,
            )
        }
    }
}


@Composable
private fun RepeatOnHoldEffect(
    interactionSource: MutableInteractionSource,
    enabled: () -> Boolean,
    onClick: () -> Unit,
    onRepeatTick: () -> Unit,
) {
    val currentEnabled by rememberUpdatedState(enabled)
    val currentOnClick by rememberUpdatedState(onClick)
    val currentOnRepeatTick by rememberUpdatedState(onRepeatTick)

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collectLatest { interaction ->
            if (interaction is PressInteraction.Press && currentEnabled()) {
                delay(INITIAL_DELAY_MS.milliseconds)
                repeatUntilDisabled(currentEnabled) {
                    currentOnClick()
                    currentOnRepeatTick()
                }
            }
        }
    }
}

private suspend fun repeatUntilDisabled(enabled: () -> Boolean, onTick: () -> Unit) {
    var elapsed = 0L
    while (enabled()) {
        onTick()
        val interval = when {
            elapsed < TIER_1_MS -> TIER_1_INTERVAL_MS
            elapsed < TIER_2_MS -> TIER_2_INTERVAL_MS
            else -> TIER_3_INTERVAL_MS
        }
        delay(interval.milliseconds)
        elapsed += interval
    }
}

private const val INITIAL_DELAY_MS = 400L
private const val TIER_1_MS = 1_000L
private const val TIER_2_MS = 2_000L
private const val TIER_1_INTERVAL_MS = 150L
private const val TIER_2_INTERVAL_MS = 90L
private const val TIER_3_INTERVAL_MS = 50L

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
