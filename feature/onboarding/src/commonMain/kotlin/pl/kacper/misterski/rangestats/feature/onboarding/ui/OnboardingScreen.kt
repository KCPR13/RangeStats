package pl.kacper.misterski.rangestats.feature.onboarding.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgDeep
import pl.kacper.misterski.rangestats.core.ui.theme.TacBorder
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextMuted
import pl.kacper.misterski.rangestats.feature.onboarding.ui.pages.ArsenalPage
import pl.kacper.misterski.rangestats.feature.onboarding.ui.pages.CameraPage
import pl.kacper.misterski.rangestats.feature.onboarding.ui.pages.LocationPage
import pl.kacper.misterski.rangestats.feature.onboarding.ui.pages.ProfilePage
import pl.kacper.misterski.rangestats.feature.onboarding.ui.pages.WelcomePage
import rangestats.feature.onboarding.generated.resources.Res
import rangestats.feature.onboarding.generated.resources.onboarding_btn_back

@Composable
fun OnboardingScreen(
    state: OnboardingUiModel,
    onAction: (OnboardingAction) -> Unit,
) {
    val pagerState = rememberPagerState(
        initialPage = state.currentPage.ordinal,
        pageCount = { OnboardingUiModel.PAGE_COUNT },
    )

    LaunchedEffect(state.currentPage) {
        if (pagerState.currentPage != state.currentPage.ordinal) {
            pagerState.animateScrollToPage(state.currentPage.ordinal)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TacBgDeep),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimen.dp56)
                .padding(top = Dimen.dp20, bottom = Dimen.dp8),
        ) {
            if (state.showBackButton) {
                IconButton(
                    onClick = { onAction(OnboardingAction.PreviousPage) },
                    modifier = Modifier.align(Alignment.CenterStart).padding(start = Dimen.dp16),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                        contentDescription = stringResource(Res.string.onboarding_btn_back),
                        tint = TacAccent,
                    )
                }
            }
            PageIndicator(
                currentPage = state.currentPage.ordinal,
                pageCount = OnboardingUiModel.PAGE_COUNT,
                modifier = Modifier.align(Alignment.Center),
            )
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
            userScrollEnabled = false,
        ) { page ->
            when (OnboardingPage.entries[page]) {
                OnboardingPage.WELCOME -> WelcomePage(onAction = onAction)
                OnboardingPage.CAMERA -> CameraPage(onAction = onAction)
                OnboardingPage.LOCATION -> LocationPage(onAction = onAction)
                OnboardingPage.PROFILE -> ProfilePage(state = state, onAction = onAction)
                OnboardingPage.ARSENAL -> ArsenalPage(onAction = onAction)
            }
        }
    }
}

@Composable
private fun PageIndicator(
    currentPage: Int,
    pageCount: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(pageCount) { index ->
            val isActive = index == currentPage
            val isDone = index < currentPage
            Box(
                modifier = Modifier
                    .padding(horizontal = Dimen.dp4)
                    .height(Dimen.dp5)
                    .width(if (isActive) Dimen.dp18 else Dimen.dp5)
                    .clip(RoundedCornerShape(Dimen.dp3))
                    .background(
                        when {
                            isActive -> TacAccent
                            isDone -> TacTextMuted
                            else -> TacBorder
                        }
                    ),
            )
        }
    }
}

@Preview
@Composable
private fun OnboardingScreenPreview() {
    RangeStatsTheme {
        OnboardingScreen(state = OnboardingUiModel(currentPage = OnboardingPage.WELCOME), onAction = {})
    }
}
