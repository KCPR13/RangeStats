package pl.kacper.misterski.rangestats.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter

@Composable
fun getIconTint(): ColorFilter =
    if (isSystemInDarkTheme()) {
        ColorFilter.tint(Color.White)
    } else {
        ColorFilter.tint(Color.Black)
    }