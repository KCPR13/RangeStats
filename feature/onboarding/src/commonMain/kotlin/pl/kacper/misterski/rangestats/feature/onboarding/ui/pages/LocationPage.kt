package pl.kacper.misterski.rangestats.feature.onboarding.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import pl.kacper.misterski.rangestats.core.ui.component.TacButton
import pl.kacper.misterski.rangestats.core.ui.component.TacSecondaryButton
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgDeep
import pl.kacper.misterski.rangestats.feature.onboarding.ui.OnboardingAction
import pl.kacper.misterski.rangestats.feature.onboarding.ui.common.OnboardingDescription
import pl.kacper.misterski.rangestats.feature.onboarding.ui.common.OnboardingIconBox
import pl.kacper.misterski.rangestats.feature.onboarding.ui.common.OnboardingTitle
import pl.kacper.misterski.rangestats.feature.onboarding.ui.common.PermissionCard
import rangestats.feature.onboarding.generated.resources.Res
import rangestats.feature.onboarding.generated.resources.ic_ob_location
import rangestats.feature.onboarding.generated.resources.onboarding_btn_allow_location
import rangestats.feature.onboarding.generated.resources.onboarding_btn_not_now
import rangestats.feature.onboarding.generated.resources.onboarding_location_desc
import rangestats.feature.onboarding.generated.resources.onboarding_location_perm_badge
import rangestats.feature.onboarding.generated.resources.onboarding_location_perm_desc
import rangestats.feature.onboarding.generated.resources.onboarding_location_perm_title
import rangestats.feature.onboarding.generated.resources.onboarding_location_title

@Composable
internal fun LocationPage(onAction: (OnboardingAction) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimen.dp20),
        verticalArrangement = Arrangement.spacedBy(Dimen.dp20)
    ) {
        Spacer(Modifier.height(Dimen.dp40))

        OnboardingIconBox(
            painter = painterResource(Res.drawable.ic_ob_location),
            accentBorder = true,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )

        OnboardingTitle(text = stringResource(Res.string.onboarding_location_title))

        OnboardingDescription(text = stringResource(Res.string.onboarding_location_desc))

        PermissionCard(
            painter = painterResource(Res.drawable.ic_ob_location),
            title = stringResource(Res.string.onboarding_location_perm_title),
            description = stringResource(Res.string.onboarding_location_perm_desc),
            badge = stringResource(Res.string.onboarding_location_perm_badge),
            badgeAccent = false,
        )

        Spacer(Modifier.weight(1f))

        TacButton(
            text = stringResource(Res.string.onboarding_btn_allow_location),
            onClick = { onAction(OnboardingAction.NextPage) },
            modifier = Modifier.fillMaxWidth(),
        )

        TacSecondaryButton(
            text = stringResource(Res.string.onboarding_btn_not_now),
            onClick = { onAction(OnboardingAction.NextPage) },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(Dimen.dp32))
    }
}

@Preview
@Composable
private fun LocationPagePreview() {
    RangeStatsTheme {
        Box(modifier = Modifier.background(TacBgDeep)) {
            LocationPage(onAction = {})
        }
    }
}
