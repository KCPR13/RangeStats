package pl.kacper.misterski.rangestats.feature.onboarding.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import pl.kacper.misterski.rangestats.core.ui.component.TacButton
import pl.kacper.misterski.rangestats.core.ui.theme.PrecisionTrackShapes
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgDeep
import pl.kacper.misterski.rangestats.core.ui.theme.TacBorder
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.FontSize
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextMuted
import pl.kacper.misterski.rangestats.feature.onboarding.ui.OnboardingAction
import pl.kacper.misterski.rangestats.feature.onboarding.ui.common.OnboardingDescription
import pl.kacper.misterski.rangestats.feature.onboarding.ui.common.OnboardingIconBox
import pl.kacper.misterski.rangestats.feature.onboarding.ui.common.OnboardingTitle
import rangestats.feature.onboarding.generated.resources.Res
import rangestats.feature.onboarding.generated.resources.ic_ob_weapon
import rangestats.feature.onboarding.generated.resources.onboarding_arsenal_add_icon
import rangestats.feature.onboarding.generated.resources.onboarding_arsenal_add_weapon
import rangestats.feature.onboarding.generated.resources.onboarding_arsenal_desc
import rangestats.feature.onboarding.generated.resources.onboarding_arsenal_title
import rangestats.feature.onboarding.generated.resources.onboarding_btn_done

@Composable
internal fun ArsenalPage(onAction: (OnboardingAction) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimen.dp20),
        verticalArrangement = Arrangement.spacedBy(Dimen.dp16)
    ) {

        Spacer(Modifier.height(Dimen.dp20))

        OnboardingIconBox(
            painter = painterResource(Res.drawable.ic_ob_weapon),
            accentBorder = true,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )


        OnboardingTitle(text = stringResource(Res.string.onboarding_arsenal_title))

        OnboardingDescription(text = stringResource(Res.string.onboarding_arsenal_desc))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(PrecisionTrackShapes.medium)
                .border(Dimen.dp1, TacBorder, PrecisionTrackShapes.medium)
                .clickable { } // TODO
                .padding(Dimen.dp12),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(Res.string.onboarding_arsenal_add_icon),
                color = TacTextMuted,
                fontSize = FontSize.sp18
            )
            Spacer(Modifier.width(Dimen.dp8))
            Text(
                text = stringResource(Res.string.onboarding_arsenal_add_weapon),
                color = TacTextMuted,
                fontSize = FontSize.sp18,
                letterSpacing = FontSize.sp0_1,
            )
        }

        Spacer(Modifier.weight(1f))

        TacButton(
            text = stringResource(Res.string.onboarding_btn_done),
            onClick = { onAction(OnboardingAction.Complete) },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(Dimen.dp32))
    }
}

@Preview
@Composable
private fun ArsenalPagePreview() {
    RangeStatsTheme {
        Box(modifier = Modifier.background(TacBgDeep)) {
            ArsenalPage(onAction = {})
        }
    }
}
