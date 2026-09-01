package pl.kacper.misterski.rangestats.feature.onboarding.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgCard
import pl.kacper.misterski.rangestats.core.ui.theme.TacBorder
import rangestats.feature.onboarding.generated.resources.Res
import rangestats.feature.onboarding.generated.resources.ic_ob_camera

@Composable
internal fun OnboardingIconBox(
    painter: Painter,
    accentBorder: Boolean = true,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(Dimen.dp100)
            .clip(RoundedCornerShape(Dimen.dp18))
            .background(TacBgCard)
            .border(
                Dimen.dp1,
                if (accentBorder) TacAccent else TacBorder,
                RoundedCornerShape(Dimen.dp18),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(Dimen.dp48),
        )
    }
}

@Preview
@Composable
private fun OnboardingIconBoxPreview(){
    RangeStatsTheme {
        Box(Modifier.fillMaxSize()){
            OnboardingIconBox(
                painter = painterResource(Res.drawable.ic_ob_camera),
                accentBorder = true,
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}
