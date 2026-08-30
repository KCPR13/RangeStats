package pl.kacper.misterski.rangestats.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import pl.kacper.misterski.rangestats.core.ui.theme.FontSize
import pl.kacper.misterski.rangestats.core.ui.theme.LetterSpacing
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgPanel

@Composable
fun TacTopBar(
    title: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .background(TacBgPanel)
                .safeContentPadding(),
    ) {
        Text(
            text = title,
            color = TacAccent,
            fontSize = FontSize.sp11,
            fontWeight = FontWeight.Medium,
            letterSpacing = LetterSpacing.em12,
        )
    }
}

@Preview
@Composable
private fun TacTopBarPreview() {
    RangeStatsTheme {
        TacTopBar(title = "HISTORIA")
    }
}
