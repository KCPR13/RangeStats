package pl.kacper.misterski.rangestats.core.ui.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import pl.kacper.misterski.rangestats.core.ui.Res
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgDeep
import pl.kacper.misterski.rangestats.core.ui.theme.TacScrim

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun TacLoadingScreen(
    modifier: Modifier = Modifier,
    backgroundColor: Color = TacBgDeep,
) {
    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("files/rangestats_loader.json").decodeToString(),
        )
    }
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = Compottie.IterateForever,
    )

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(backgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter =
                rememberLottiePainter(
                    composition = composition,
                    progress = { progress },
                ),
            contentDescription = null,
            modifier = Modifier.size(Dimen.dp100),
        )
    }
}

@Composable
fun AnimatedLoader(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    backgroundColor: Color = TacBgDeep,
    content: @Composable () -> Unit,
) {
    Crossfade(
        targetState = isLoading,
        modifier =
            modifier
                .fillMaxSize()
                .background(backgroundColor),
    ) { loading ->
        if (loading) {
            TacLoadingScreen(backgroundColor = Color.Transparent)
        } else {
            content()
        }
    }
}

@Preview
@Composable
private fun TacLoadingScreenPreview() {
    RangeStatsTheme {
        TacLoadingScreen()
    }
}

@Preview
@Composable
private fun TacLoadingScreenScrimPreview() {
    RangeStatsTheme {
        TacLoadingScreen(backgroundColor = TacScrim)
    }
}
