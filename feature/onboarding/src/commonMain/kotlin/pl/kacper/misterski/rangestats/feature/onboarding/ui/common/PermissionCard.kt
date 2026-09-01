package pl.kacper.misterski.rangestats.feature.onboarding.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.FontSize
import pl.kacper.misterski.rangestats.core.ui.theme.PrecisionTrackShapes
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgCard
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgElevated
import pl.kacper.misterski.rangestats.core.ui.theme.TacBorder
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextMuted
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextPrimary
import rangestats.feature.onboarding.generated.resources.Res
import rangestats.feature.onboarding.generated.resources.ic_ob_camera
import rangestats.feature.onboarding.generated.resources.onboarding_camera_perm_badge
import rangestats.feature.onboarding.generated.resources.onboarding_camera_perm_desc
import rangestats.feature.onboarding.generated.resources.onboarding_camera_perm_title


@Composable
internal fun PermissionCard(
    painter: Painter,
    title: String,
    description: String,
    badge: String,
    badgeAccent: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(PrecisionTrackShapes.large)
            .background(TacBgCard)
            .border(Dimen.dp1, TacBorder, PrecisionTrackShapes.large)
            .padding(horizontal = Dimen.dp16, vertical = Dimen.dp12),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(Dimen.dp40)
                .clip(RoundedCornerShape(Dimen.dp7))
                .background(if (badgeAccent) TacBgElevated else TacBgCard)
                .border(
                    Dimen.dp1,
                    if (badgeAccent) TacAccent else TacBorder,
                    RoundedCornerShape(Dimen.dp7),
                ),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.size(Dimen.dp20),
            )
        }
        Spacer(Modifier.width(Dimen.dp12))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = TacTextPrimary,
                fontSize = FontSize.sp16,
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = description,
                color = TacTextMuted,
                fontSize = FontSize.sp10,
            )
        }
        Box(
            modifier = Modifier
                .clip(PrecisionTrackShapes.extraSmall)
                .background(if (badgeAccent) TacBgElevated else TacBgCard)
                .border(
                    Dimen.dp1,
                    if (badgeAccent) TacAccent else TacBorder,
                    PrecisionTrackShapes.extraSmall,
                )
                .padding(horizontal = Dimen.dp8, vertical = Dimen.dp4),
        ) {
            Text(
                text = badge,
                color = if (badgeAccent) TacAccent else TacTextMuted,
                fontSize = FontSize.sp10,
            )
        }
    }
}

@Preview
@Composable
private fun PermissionCardPreview(){
    RangeStatsTheme {
        Box(Modifier.fillMaxSize()){
            PermissionCard(
                painter = painterResource(Res.drawable.ic_ob_camera),
                title = stringResource(Res.string.onboarding_camera_perm_title),
                description = stringResource(Res.string.onboarding_camera_perm_desc),
                badge = stringResource(Res.string.onboarding_camera_perm_badge),
                badgeAccent = true,
            )
        }
    }
}
