package pl.kacper.misterski.rangestats.feature.onboarding.ui.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import pl.kacper.misterski.rangestats.core.ui.theme.FontSize
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextMuted

@Composable
fun OnboardingDescription(text: String) {
    Text(
        text = text,
        color = TacTextMuted,
        fontSize = FontSize.sp16,
        lineHeight = FontSize.sp20, // TODO special object?
    )
}

@Preview
@Composable
private fun OnboardingDescriptionPreview() {
    RangeStatsTheme {
        OnboardingDescription(text = "Track your sessions, analyse targets and improve your accuracy.")
    }
}
