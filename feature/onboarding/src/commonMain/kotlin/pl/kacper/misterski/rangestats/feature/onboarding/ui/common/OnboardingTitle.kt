package pl.kacper.misterski.rangestats.feature.onboarding.ui.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextPrimary

@Composable
fun OnboardingTitle(text: String) {
    Text(
        text = text,
        color = TacTextPrimary,
        fontSize = 32.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 40.sp,
    )
}

@Preview
@Composable
private fun OnboardingTitlePreview() {
    RangeStatsTheme {
        OnboardingTitle(text = "Welcome, Operator")
    }
}
