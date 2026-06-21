package pl.kacper.misterski.rangestats.feature.session.ui.summary

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource
import pl.kacper.misterski.rangestats.core.domain.enums.TargetZone
import pl.kacper.misterski.rangestats.core.ui.component.TacButton
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.FontSize
import pl.kacper.misterski.rangestats.core.ui.theme.LetterSpacing
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccentDim
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgCard
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgDeep
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgPanel
import pl.kacper.misterski.rangestats.core.ui.theme.TacBorder
import pl.kacper.misterski.rangestats.core.ui.theme.TacRed
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextMuted
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextPrimary
import rangestats.feature.session.generated.resources.Res
import rangestats.feature.session.generated.resources.common_nav_back
import rangestats.feature.session.generated.resources.summary_location_today_format
import rangestats.feature.session.generated.resources.summary_save
import rangestats.feature.session.generated.resources.summary_score_label
import rangestats.feature.session.generated.resources.summary_session_meta_format
import rangestats.feature.session.generated.resources.summary_share
import rangestats.feature.session.generated.resources.summary_stat_hits
import rangestats.feature.session.generated.resources.summary_stat_misses
import rangestats.feature.session.generated.resources.summary_stat_targets
import rangestats.feature.session.generated.resources.summary_title
import rangestats.feature.session.generated.resources.summary_zone_distribution

@Composable
fun SessionSummaryScreen(
    state: SessionSummaryUiModel,
    onAction: (SessionSummaryAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TacBgDeep),
    ) {
        SummaryHeader(onBack = { onAction(SessionSummaryAction.Back) }, state = state)
        SummaryHero(state = state)
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Dimen.dp20, vertical = Dimen.dp16),
            verticalArrangement = Arrangement.spacedBy(Dimen.dp14),
        ) {
            StatsRow(state = state)
            HorizontalDivider(color = TacBorder, thickness = Dimen.dp1)
            ZoneDistributionSection(zones = state.zoneDistribution)
            TacButton(
                text = stringResource(Res.string.summary_save),
                onClick = { onAction(SessionSummaryAction.Save) },
                modifier = Modifier.fillMaxWidth(),
            )
            ShareButton(onClick = { onAction(SessionSummaryAction.Share) })
        }
    }
}

@Composable
private fun SummaryHeader(onBack: () -> Unit, state: SessionSummaryUiModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TacBgPanel)
            .padding(horizontal = Dimen.dp20, vertical = Dimen.dp12),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimen.dp12),
    ) {
        Box(
            modifier = Modifier
                .size(Dimen.dp32)
                .background(TacBgCard, RoundedCornerShape(Dimen.dp4))
                .border(Dimen.dp1, TacBorder, RoundedCornerShape(Dimen.dp4))
                .clickable(onClick = onBack),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = stringResource(Res.string.common_nav_back), color = TacTextMuted, fontSize = FontSize.sp18)
        }
        Column {
            Text(
                text = stringResource(Res.string.summary_title),
                color = TacAccent,
                fontSize = FontSize.sp11,
                fontWeight = FontWeight.Medium,
                letterSpacing = LetterSpacing.em12,
            )
            Text(
                text = stringResource(Res.string.summary_location_today_format, state.locationName),
                color = TacTextMuted,
                fontSize = FontSize.sp13,
            )
        }
    }
}

@Composable
private fun SummaryHero(state: SessionSummaryUiModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(TacBgCard)
            .border(width = Dimen.dp1, color = TacBorder)
            .padding(vertical = Dimen.dp16),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = if (state.score != null) "${state.score.toInt()}%" else "—", // TODO hardcoded
            color = TacAccent,
            fontSize = FontSize.sp48,
            fontWeight = FontWeight.Bold,
            lineHeight = FontSize.sp48,
        )
        Text(
            text = stringResource(Res.string.summary_score_label),
            color = TacTextMuted,
            fontSize = FontSize.sp9,
            letterSpacing = LetterSpacing.em14,
        )
        Spacer(Modifier.height(Dimen.dp4))
        Text(
            text = stringResource(Res.string.summary_session_meta_format, state.caliber, state.distanceMeters, state.durationMinutes),
            color = TacTextMuted,
            fontSize = FontSize.sp10,
        )
    }
}

@Composable
private fun StatsRow(state: SessionSummaryUiModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimen.dp8),
    ) {
        StatMini(
            value = state.targetCount.toString(),
            label = stringResource(Res.string.summary_stat_targets),
            modifier = Modifier.weight(1f),
        )
        StatMini(
            value = state.totalHits.toString(),
            label = stringResource(Res.string.summary_stat_hits),
            modifier = Modifier.weight(1f),
        )
        StatMini(
            value = state.totalMisses.toString(),
            label = stringResource(Res.string.summary_stat_misses),
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun StatMini(value: String, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(TacBgCard, RoundedCornerShape(Dimen.dp8))
            .border(Dimen.dp1, TacBorder, RoundedCornerShape(Dimen.dp8))
            .padding(vertical = Dimen.dp8),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = value,
            color = TacTextPrimary,
            fontSize = FontSize.sp16,
            fontWeight = FontWeight.SemiBold,
            lineHeight = FontSize.sp16,
        )
        Text(
            text = label,
            color = TacTextMuted,
            fontSize = FontSize.sp9,
        )
    }
}

@Composable
private fun ZoneDistributionSection(zones: Map<TargetZone, Int>) {
    val maxCount = zones.values.maxOrNull()?.coerceAtLeast(1) ?: 1
    Column(verticalArrangement = Arrangement.spacedBy(Dimen.dp14)) {
        Text(
            text = stringResource(Res.string.summary_zone_distribution),
            color = TacTextMuted,
            fontSize = FontSize.sp10,
            letterSpacing = LetterSpacing.em12,
        )
        Column(verticalArrangement = Arrangement.spacedBy(Dimen.dp5)) { // TODO business logic
            listOf(TargetZone.X, TargetZone.TEN, TargetZone.NINE, TargetZone.EIGHT, TargetZone.SEVEN, TargetZone.SIX, TargetZone.MISS).forEach { zone ->
                val count = zones[zone] ?: 0
                val fraction = count.toFloat() / maxCount
                ZoneRow(zone = zone, count = count, fraction = fraction)
            }
        }
    }
}

@Composable
private fun ZoneRow(zone: TargetZone, count: Int, fraction: Float) {
    val isMiss = zone == TargetZone.MISS
    val barColor = if (isMiss) TacRed else TacAccent
    val labelText = when (zone) { // TODO business logic
        TargetZone.X -> "X"
        TargetZone.TEN -> "10"
        TargetZone.NINE -> "9"
        TargetZone.EIGHT -> "8"
        TargetZone.SEVEN -> "7"
        TargetZone.SIX -> "6"
        TargetZone.MISS -> "✕"
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimen.dp8),
    ) {
        Text(
            text = labelText,
            color = if (isMiss) TacRed else TacTextMuted,
            fontSize = FontSize.sp10,
            modifier = Modifier.width(Dimen.dp18),
            textAlign = TextAlign.End,
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(Dimen.dp5)
                .background(TacBorder, RoundedCornerShape(Dimen.dp2)),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction.coerceIn(0f, 1f))
                    .height(Dimen.dp5)
                    .background(barColor, RoundedCornerShape(Dimen.dp2)),
            )
        }
        Text(
            text = count.toString(),
            color = if (isMiss) TacRed else TacTextMuted,
            fontSize = FontSize.sp10,
            modifier = Modifier.width(Dimen.dp24),
            textAlign = TextAlign.End,
        )
    }
}

@Composable
private fun ShareButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TacBgCard, RoundedCornerShape(Dimen.dp8))
            .border(Dimen.dp1, TacBorder, RoundedCornerShape(Dimen.dp8))
            .clickable(onClick = onClick)
            .padding(vertical = Dimen.dp12),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(Res.string.summary_share),
            color = TacTextMuted,
            fontSize = FontSize.sp11,
            letterSpacing = LetterSpacing.em10,
        )
    }
}

@Preview
@Composable
private fun SessionSummaryScreenPreview() {
    RangeStatsTheme {
        SessionSummaryScreen(
            state = SessionSummaryUiModel(
                locationName = "Strzelnica Łódź",
                caliber = "9mm",
                distanceMeters = 25,
                durationMinutes = 22,
                score = 89f,
                targetCount = 3,
                totalHits = 62,
                totalMisses = 8,
                zoneDistribution = mapOf(
                    TargetZone.TEN to 18,
                    TargetZone.NINE to 14,
                    TargetZone.EIGHT to 10,
                    TargetZone.SEVEN to 6,
                    TargetZone.SIX to 4,
                    TargetZone.MISS to 8,
                ),
            ),
            onAction = {},
        )
    }
}
