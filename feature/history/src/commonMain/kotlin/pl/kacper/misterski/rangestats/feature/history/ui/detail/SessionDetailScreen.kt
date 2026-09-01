package pl.kacper.misterski.rangestats.feature.history.ui.detail

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
import pl.kacper.misterski.rangestats.core.ui.component.AnimatedLoader
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.FontSize
import pl.kacper.misterski.rangestats.core.ui.theme.LetterSpacing
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgCard
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgDeep
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgPanel
import pl.kacper.misterski.rangestats.core.ui.theme.TacBorder
import pl.kacper.misterski.rangestats.core.ui.theme.TacRed
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextMuted
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextPrimary
import rangestats.feature.history.generated.resources.Res
import rangestats.feature.history.generated.resources.common_nav_back
import rangestats.feature.history.generated.resources.detail_delete
import rangestats.feature.history.generated.resources.detail_meta_format
import rangestats.feature.history.generated.resources.detail_score_label
import rangestats.feature.history.generated.resources.detail_stat_hits
import rangestats.feature.history.generated.resources.detail_stat_misses
import rangestats.feature.history.generated.resources.detail_stat_shots
import rangestats.feature.history.generated.resources.detail_title
import rangestats.feature.history.generated.resources.common_percent_format
import rangestats.feature.history.generated.resources.common_score_empty
import rangestats.feature.history.generated.resources.detail_zone_distribution

@Composable
fun SessionDetailScreen(
    state: SessionDetailUiModel,
    onAction: (SessionDetailAction) -> Unit,
) {
    AnimatedLoader(isLoading = state.isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(TacBgDeep),
        ) {
            DetailHeader(state = state, onBack = { onAction(SessionDetailAction.Back) })
            DetailHero(state = state)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = Dimen.dp20, vertical = Dimen.dp16),
                verticalArrangement = Arrangement.spacedBy(Dimen.dp14),
            ) {
                StatsRow(state = state)
                HorizontalDivider(color = TacBorder, thickness = Dimen.dp1)
                ZoneDistributionSection(zoneRows = state.zoneRows)
                DeleteButton(onClick = { onAction(SessionDetailAction.Delete) })
            }
        }
    }
}

@Composable
private fun DetailHeader(state: SessionDetailUiModel, onBack: () -> Unit) {
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
                text = stringResource(Res.string.detail_title),
                color = TacAccent,
                fontSize = FontSize.sp11,
                fontWeight = FontWeight.Medium,
                letterSpacing = LetterSpacing.em12,
            )
            Text(
                text = state.dateLabel,
                color = TacTextMuted,
                fontSize = FontSize.sp13,
            )
        }
    }
}

@Composable
private fun DetailHero(state: SessionDetailUiModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(TacBgCard)
            .border(width = Dimen.dp1, color = TacBorder)
            .padding(vertical = Dimen.dp16),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = if (state.score != null) {
                stringResource(Res.string.common_percent_format, state.score.toInt())
            } else {
                stringResource(Res.string.common_score_empty)
            },
            color = TacAccent,
            fontSize = FontSize.sp48,
            fontWeight = FontWeight.Bold,
            lineHeight = FontSize.sp48,
        )
        Text(
            text = stringResource(Res.string.detail_score_label),
            color = TacTextMuted,
            fontSize = FontSize.sp9,
            letterSpacing = LetterSpacing.em14,
        )
        Spacer(Modifier.height(Dimen.dp4))
        Text(
            text = stringResource(
                Res.string.detail_meta_format,
                state.locationName,
                state.distanceMeters,
                state.durationMinutes,
            ),
            color = TacTextMuted,
            fontSize = FontSize.sp10,
        )
    }
}

@Composable
private fun StatsRow(state: SessionDetailUiModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimen.dp8),
    ) {
        StatMini(
            value = state.shotCount.toString(),
            label = stringResource(Res.string.detail_stat_shots),
            modifier = Modifier.weight(1f),
        )
        StatMini(
            value = state.totalHits.toString(),
            label = stringResource(Res.string.detail_stat_hits),
            modifier = Modifier.weight(1f),
        )
        StatMini(
            value = state.totalMisses.toString(),
            label = stringResource(Res.string.detail_stat_misses),
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
        Text(text = label, color = TacTextMuted, fontSize = FontSize.sp9)
    }
}

@Composable
private fun ZoneDistributionSection(zoneRows: List<ZoneRowUiModel>) {
    Column(verticalArrangement = Arrangement.spacedBy(Dimen.dp14)) {
        Text(
            text = stringResource(Res.string.detail_zone_distribution),
            color = TacTextMuted,
            fontSize = FontSize.sp10,
            letterSpacing = LetterSpacing.em12,
        )
        Column(verticalArrangement = Arrangement.spacedBy(Dimen.dp5)) {
            zoneRows.forEach { row -> ZoneRow(row = row) }
        }
    }
}

@Composable
private fun ZoneRow(row: ZoneRowUiModel) {
    val barColor = if (row.isMiss) TacRed else TacAccent
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimen.dp8),
    ) {
        Text(
            text = row.label,
            color = if (row.isMiss) TacRed else TacTextMuted,
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
                    .fillMaxWidth(row.fraction.coerceIn(0f, 1f))
                    .height(Dimen.dp5)
                    .background(barColor, RoundedCornerShape(Dimen.dp2)),
            )
        }
        Text(
            text = row.count.toString(),
            color = if (row.isMiss) TacRed else TacTextMuted,
            fontSize = FontSize.sp10,
            modifier = Modifier.width(Dimen.dp24),
            textAlign = TextAlign.End,
        )
    }
}

@Composable
private fun DeleteButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TacBgCard, RoundedCornerShape(Dimen.dp8))
            .border(Dimen.dp1, TacRed, RoundedCornerShape(Dimen.dp8))
            .clickable(onClick = onClick)
            .padding(vertical = Dimen.dp12),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(Res.string.detail_delete),
            color = TacRed,
            fontSize = FontSize.sp11,
            letterSpacing = LetterSpacing.em10,
        )
    }
}

@Preview
@Composable
private fun SessionDetailScreenPreview() {
    RangeStatsTheme {
        SessionDetailScreen(
            state = SessionDetailUiModel(
                isLoading = false,
                locationName = "Strzelnica Łódź",
                distanceMeters = 25,
                dateLabel = "24.05.2025",
                durationMinutes = 22,
                score = 89f,
                totalHits = 62,
                totalMisses = 8,
                shotCount = 70,
                zoneRows = listOf(
                    ZoneRowUiModel(label = "X", count = 0, fraction = 0f, isMiss = false),
                    ZoneRowUiModel(label = "10", count = 18, fraction = 1f, isMiss = false),
                    ZoneRowUiModel(label = "9", count = 14, fraction = 0.78f, isMiss = false),
                    ZoneRowUiModel(label = "8", count = 10, fraction = 0.56f, isMiss = false),
                    ZoneRowUiModel(label = "7", count = 6, fraction = 0.33f, isMiss = false),
                    ZoneRowUiModel(label = "6", count = 4, fraction = 0.22f, isMiss = false),
                    ZoneRowUiModel(label = "✕", count = 8, fraction = 0.44f, isMiss = true),
                ),
            ),
            onAction = {},
        )
    }
}
