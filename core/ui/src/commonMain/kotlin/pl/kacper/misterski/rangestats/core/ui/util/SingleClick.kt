package pl.kacper.misterski.rangestats.core.ui.util

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.semantics.Role
import kotlin.time.TimeSource

private const val DEFAULT_THROTTLE_MS = 1000L

@Composable
fun rememberSingleClick(
    throttleDurationMs: Long = DEFAULT_THROTTLE_MS,
    onClick: () -> Unit,
): () -> Unit {
    val currentOnClick by rememberUpdatedState(onClick)
    val lastClickMark = remember { mutableStateOf<TimeSource.Monotonic.ValueTimeMark?>(null) }

    return remember {
        {
            val now = TimeSource.Monotonic.markNow()
            val last = lastClickMark.value
            if (last == null || (now - last).inWholeMilliseconds > throttleDurationMs) {
                lastClickMark.value = now
                currentOnClick()
            }
        }
    }
}

fun Modifier.singleClickable(
    enabled: Boolean = true,
    throttleDurationMs: Long = DEFAULT_THROTTLE_MS,
    role: Role? = null,
    onClick: () -> Unit,
): Modifier =
    composed {
        val currentOnClick by rememberUpdatedState(onClick)
        val lastClickMark = remember { mutableStateOf<TimeSource.Monotonic.ValueTimeMark?>(null) }

        clickable(enabled = enabled, role = role) {
            val now = TimeSource.Monotonic.markNow()
            val last = lastClickMark.value
            if (last == null || (now - last).inWholeMilliseconds > throttleDurationMs) {
                lastClickMark.value = now
                currentOnClick()
            }
        }
    }
