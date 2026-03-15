package pl.kacper.misterski.rangestats.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val TacColorScheme = darkColorScheme(
    primary = TacAccent,
    onPrimary = TacOnAccent,
    primaryContainer = TacAccentDim,
    onPrimaryContainer = TacTextPrimary,
    secondary = TacBorderBright,
    onSecondary = TacTextPrimary,
    secondaryContainer = TacBgElevated,
    onSecondaryContainer = TacTextSecondary,
    background = TacBgDeep,
    onBackground = TacTextPrimary,
    surface = TacBgPanel,
    onSurface = TacTextPrimary,
    surfaceVariant = TacBgCard,
    onSurfaceVariant = TacTextSecondary,
    outline = TacBorder,
    outlineVariant = TacBorderBright,
    error = TacRed,
    onError = TacTextPrimary,
    scrim = TacScrim,
)

@Composable
fun RangeStatsTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = TacColorScheme,
        typography = rangeStatsTypography(),
        shapes = PrecisionTrackShapes,
        content = content,
    )
}
