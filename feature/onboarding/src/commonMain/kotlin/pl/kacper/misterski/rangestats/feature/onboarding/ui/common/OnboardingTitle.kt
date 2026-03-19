package pl.kacper.misterski.rangestats.feature.onboarding.ui.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import pl.kacper.misterski.rangestats.core.ui.theme.FontSize
import pl.kacper.misterski.rangestats.core.ui.theme.LineHeight
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextPrimary

@Composable
fun OnboardingTitle(text: String) {
    Text(
        text = text,
        color = TacTextPrimary,
        fontSize = FontSize.sp32,
        fontWeight = FontWeight.SemiBold,
        lineHeight = LineHeight.sp40,
    )
}

@Preview
@Composable
private fun OnboardingTitlePreview() {
    RangeStatsTheme {
        OnboardingTitle(text = "Welcome, Operator")
    }
}
