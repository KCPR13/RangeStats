package pl.kacper.misterski.rangestats.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import pl.kacper.misterski.rangestats.core.ui.Res
import pl.kacper.misterski.rangestats.core.ui.ibm_plex_sans_medium
import pl.kacper.misterski.rangestats.core.ui.ibm_plex_sans_regular
import pl.kacper.misterski.rangestats.core.ui.oswald_regular
import pl.kacper.misterski.rangestats.core.ui.oswald_semibold
import pl.kacper.misterski.rangestats.core.ui.share_tech_mono_regular

@Composable
private fun oswaldFamily() =
    FontFamily(
        Font(Res.font.oswald_regular, FontWeight.Normal),
        Font(Res.font.oswald_semibold, FontWeight.SemiBold),
    )

@Composable
private fun ibmPlexSansFamily() =
    FontFamily(
        Font(Res.font.ibm_plex_sans_regular, FontWeight.Normal),
        Font(Res.font.ibm_plex_sans_medium, FontWeight.Medium),
    )

@Composable
private fun shareTechMonoFamily() =
    FontFamily(
        Font(Res.font.share_tech_mono_regular, FontWeight.Normal),
    )

@Composable
fun rangeStatsTypography(): Typography {
    val oswald = oswaldFamily()
    val ibmPlexSans = ibmPlexSansFamily()

    return Typography(
        // Display — Oswald (hero headlines, large stats)
        displayLarge =
            TextStyle(
                fontFamily = oswald,
                fontWeight = FontWeight.SemiBold,
                fontSize = 57.sp,
                lineHeight = 64.sp,
                letterSpacing = (-0.25).sp,
            ),
        displayMedium =
            TextStyle(
                fontFamily = oswald,
                fontWeight = FontWeight.SemiBold,
                fontSize = 45.sp,
                lineHeight = 52.sp,
            ),
        displaySmall =
            TextStyle(
                fontFamily = oswald,
                fontWeight = FontWeight.Normal,
                fontSize = 36.sp,
                lineHeight = 44.sp,
            ),
        // Headline — Oswald
        headlineLarge =
            TextStyle(
                fontFamily = oswald,
                fontWeight = FontWeight.SemiBold,
                fontSize = 32.sp,
                lineHeight = 40.sp,
            ),
        headlineMedium =
            TextStyle(
                fontFamily = oswald,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                lineHeight = 36.sp,
            ),
        headlineSmall =
            TextStyle(
                fontFamily = oswald,
                fontWeight = FontWeight.Normal,
                fontSize = 24.sp,
                lineHeight = 32.sp,
            ),
        // Title — Oswald (titleLarge) / IBM Plex Sans (medium/small)
        titleLarge =
            TextStyle(
                fontFamily = oswald,
                fontWeight = FontWeight.Normal,
                fontSize = 22.sp,
                lineHeight = 28.sp,
            ),
        titleMedium =
            TextStyle(
                fontFamily = ibmPlexSans,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.15.sp,
            ),
        titleSmall =
            TextStyle(
                fontFamily = ibmPlexSans,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp,
            ),
        // Body — IBM Plex Sans
        bodyLarge =
            TextStyle(
                fontFamily = ibmPlexSans,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
            ),
        bodyMedium =
            TextStyle(
                fontFamily = ibmPlexSans,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.25.sp,
            ),
        bodySmall =
            TextStyle(
                fontFamily = ibmPlexSans,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.4.sp,
            ),
        // Label — IBM Plex Sans
        labelLarge =
            TextStyle(
                fontFamily = ibmPlexSans,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp,
            ),
        labelMedium =
            TextStyle(
                fontFamily = ibmPlexSans,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.5.sp,
            ),
        labelSmall =
            TextStyle(
                fontFamily = ibmPlexSans,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.5.sp,
            ),
    )
}

// Monospace styles for ballistic values, codes, data readouts
@Immutable
data class MonoStyles(
    val data: TextStyle,
    val sectionLabel: TextStyle,
    val resultValue: TextStyle,
)

@Composable
fun rememberMonoStyles(): MonoStyles {
    val mono = shareTechMonoFamily()
    return remember(mono) {
        MonoStyles(
            data =
                TextStyle(
                    fontFamily = mono,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    letterSpacing = 0.5.sp,
                ),
            sectionLabel =
                TextStyle(
                    fontFamily = mono,
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp,
                    lineHeight = 14.sp,
                    letterSpacing = 1.sp,
                ),
            resultValue =
                TextStyle(
                    fontFamily = mono,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    lineHeight = 26.sp,
                ),
        )
    }
}
