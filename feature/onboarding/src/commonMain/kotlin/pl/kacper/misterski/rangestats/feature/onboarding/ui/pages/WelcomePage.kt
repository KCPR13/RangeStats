package pl.kacper.misterski.rangestats.feature.onboarding.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import pl.kacper.misterski.rangestats.core.ui.component.TacButton
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.FontSize
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgCard
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgDeep
import pl.kacper.misterski.rangestats.core.ui.theme.TacBorder
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextSecondary
import pl.kacper.misterski.rangestats.feature.onboarding.ui.OnboardingAction
import pl.kacper.misterski.rangestats.feature.onboarding.ui.common.OnboardingDescription
import pl.kacper.misterski.rangestats.feature.onboarding.ui.common.OnboardingIconBox
import pl.kacper.misterski.rangestats.feature.onboarding.ui.common.OnboardingTitle
import rangestats.feature.onboarding.generated.resources.Res
import rangestats.feature.onboarding.generated.resources.ic_feature_ai
import rangestats.feature.onboarding.generated.resources.ic_feature_profile
import rangestats.feature.onboarding.generated.resources.ic_feature_stats
import rangestats.feature.onboarding.generated.resources.ic_ob_crosshair
import rangestats.feature.onboarding.generated.resources.onboarding_btn_start
import rangestats.feature.onboarding.generated.resources.onboarding_feature_ai
import rangestats.feature.onboarding.generated.resources.onboarding_feature_profile
import rangestats.feature.onboarding.generated.resources.onboarding_feature_stats
import rangestats.feature.onboarding.generated.resources.onboarding_welcome_desc
import rangestats.feature.onboarding.generated.resources.onboarding_welcome_title


@Composable
internal fun WelcomePage(onAction: (OnboardingAction) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimen.dp20),
        verticalArrangement = Arrangement.spacedBy(Dimen.dp20)

    ) {
        Spacer(Modifier.height(Dimen.dp40))

        OnboardingIconBox(
            painter = painterResource(Res.drawable.ic_ob_crosshair),
            accentBorder = true,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )

        OnboardingTitle(text = stringResource(Res.string.onboarding_welcome_title))

        OnboardingDescription(text = stringResource(Res.string.onboarding_welcome_desc))

        FeatureRow(
            stringResource(Res.string.onboarding_feature_profile),
            Res.drawable.ic_feature_profile
        )
        FeatureRow(
            stringResource(Res.string.onboarding_feature_ai),
            Res.drawable.ic_feature_ai
        )
        FeatureRow(
            stringResource(Res.string.onboarding_feature_stats),
            Res.drawable.ic_feature_stats
        )

        Spacer(Modifier.weight(1f))

        TacButton(
            text = stringResource(Res.string.onboarding_btn_start),
            onClick = { onAction(OnboardingAction.NextPage) },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(Dimen.dp32))
    }
}

@Composable
private fun FeatureRow(
    text: String,
    icon: DrawableResource
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(Dimen.dp56)
                .clip(RoundedCornerShape(Dimen.dp5))
                .background(TacBgCard)
                .border(Dimen.dp1, TacBorder, RoundedCornerShape(Dimen.dp5)),
            contentAlignment = Alignment.Center,
        ) {

            Image(
                painter = painterResource(icon),
                contentDescription = null,
                Modifier.size(Dimen.dp24)
            )

            Box(
                modifier = Modifier
                    .size(Dimen.dp4)
                    .clip(RoundedCornerShape(Dimen.dp2))
                    .background(TacAccent),
            )
        }
        Spacer(Modifier.width(Dimen.dp16))
        Text(text = text, color = TacTextSecondary, fontSize = FontSize.sp18)
    }
}

@Preview
@Composable
private fun WelcomePagePreview() {
    RangeStatsTheme {
        Box(modifier = Modifier.background(TacBgDeep)) {
            WelcomePage(onAction = {})
        }
    }
}
