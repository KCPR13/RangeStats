package pl.kacper.misterski.rangestats.ui.common.utils

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.kacper.misterski.rangestats.core.AppRoutes
import pl.kacper.misterski.rangestats.core.getName

fun NavGraphBuilder.animatedDestination(
    route: AppRoutes,
    durationInMillis: Int = 200,
    content: @Composable () -> Unit,
) {
    composable(
        route.getName(),
        enterTransition = {
            fadeIn(
                animationSpec =
                    tween(
                        durationInMillis,
                        easing = LinearEasing,
                    ),
            ) +
                    slideIntoContainer(
                        animationSpec = tween(durationInMillis, easing = EaseIn),
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    )
        },
        exitTransition = {
            fadeOut(
                animationSpec =
                    tween(
                        durationInMillis,
                        easing = LinearEasing,
                    ),
            ) +
                    slideOutOfContainer(
                        animationSpec = tween(durationInMillis, easing = EaseOut),
                        towards = AnimatedContentTransitionScope.SlideDirection.End,
                    )
        },
    ) {
        content.invoke()
    }
}