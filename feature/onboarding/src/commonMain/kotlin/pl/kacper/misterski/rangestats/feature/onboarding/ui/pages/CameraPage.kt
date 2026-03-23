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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import pl.kacper.misterski.rangestats.core.ui.component.TacButton
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.FontSize
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgDeep
import pl.kacper.misterski.rangestats.core.ui.theme.TacRed
import pl.kacper.misterski.rangestats.core.ui.util.rememberSingleClick
import pl.kacper.misterski.rangestats.feature.onboarding.ui.common.OnboardingDescription
import pl.kacper.misterski.rangestats.feature.onboarding.ui.common.OnboardingIconBox
import pl.kacper.misterski.rangestats.feature.onboarding.ui.common.OnboardingTitle
import pl.kacper.misterski.rangestats.feature.onboarding.ui.common.PermissionCard
import rangestats.feature.onboarding.generated.resources.Res
import rangestats.feature.onboarding.generated.resources.ic_ob_camera
import rangestats.feature.onboarding.generated.resources.onboarding_btn_allow_camera
import rangestats.feature.onboarding.generated.resources.onboarding_btn_open_settings
import rangestats.feature.onboarding.generated.resources.onboarding_camera_desc
import rangestats.feature.onboarding.generated.resources.onboarding_camera_perm_badge
import rangestats.feature.onboarding.generated.resources.onboarding_camera_perm_desc
import rangestats.feature.onboarding.generated.resources.onboarding_camera_perm_title
import rangestats.feature.onboarding.generated.resources.onboarding_camera_permanently_denied_desc
import rangestats.feature.onboarding.generated.resources.onboarding_camera_required_desc
import rangestats.feature.onboarding.generated.resources.onboarding_camera_title

@Composable
internal fun CameraPage(
    showCameraRequired: Boolean,
    showCameraPermanentlyDenied: Boolean,
    requestCameraPermission: () -> Unit,
    openAppSettings: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimen.dp20),
        verticalArrangement = Arrangement.spacedBy(Dimen.dp16)
    ) {
        Spacer(Modifier.height(Dimen.dp20))

        OnboardingIconBox(
            painter = painterResource(Res.drawable.ic_ob_camera),
            accentBorder = true,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )

        OnboardingTitle(text = stringResource(Res.string.onboarding_camera_title))

        OnboardingDescription(text = stringResource(Res.string.onboarding_camera_desc))

        PermissionCard(
            painter = painterResource(Res.drawable.ic_ob_camera),
            title = stringResource(Res.string.onboarding_camera_perm_title),
            description = stringResource(Res.string.onboarding_camera_perm_desc),
            badge = stringResource(Res.string.onboarding_camera_perm_badge),
            badgeAccent = true,
        )

        if (showCameraRequired) {
            CameraRequiredInfo(isPermanentlyDenied = showCameraPermanentlyDenied)
        }

        Spacer(Modifier.weight(1f))

        if (showCameraPermanentlyDenied) {
            TacButton(
                text = stringResource(Res.string.onboarding_btn_open_settings),
                onClick = rememberSingleClick { openAppSettings() },
                modifier = Modifier.fillMaxWidth(),
            )
        } else {
            TacButton(
                text = stringResource(Res.string.onboarding_btn_allow_camera),
                onClick = rememberSingleClick { requestCameraPermission() },
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Spacer(Modifier.height(Dimen.dp32))
    }
}

@Composable
private fun CameraRequiredInfo(isPermanentlyDenied: Boolean) {
    val message = if (isPermanentlyDenied) {
        stringResource(Res.string.onboarding_camera_permanently_denied_desc)
    } else {
        stringResource(Res.string.onboarding_camera_required_desc)
    }
    Text(
        text = message,
        fontSize = FontSize.sp18,
        color = TacRed,
    )
}

@Preview
@Composable
private fun CameraPagePreview() {
    RangeStatsTheme {
        Box(modifier = Modifier.background(TacBgDeep)) {
            CameraPage(
                showCameraRequired = true,
                showCameraPermanentlyDenied = false,
                requestCameraPermission = { },
                openAppSettings = {},
            )
        }
    }
}

@Preview
@Composable
private fun CameraPagePermanentlyDeniedPreview() {
    RangeStatsTheme {
        Box(modifier = Modifier.background(TacBgDeep)) {
            CameraPage(
                showCameraRequired = true,
                showCameraPermanentlyDenied = true,
                requestCameraPermission = { },
                openAppSettings = { },
            )
        }
    }
}
