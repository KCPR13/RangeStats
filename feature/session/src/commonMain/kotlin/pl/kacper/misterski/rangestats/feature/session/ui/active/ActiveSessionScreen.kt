package pl.kacper.misterski.rangestats.feature.session.ui.active

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.FontSize
import pl.kacper.misterski.rangestats.core.ui.theme.LetterSpacing
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgCard
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgDeep
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgElevated
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgPanel
import pl.kacper.misterski.rangestats.core.ui.theme.TacBorder
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextMuted
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextSecondary
import rangestats.feature.session.generated.resources.Res
import rangestats.feature.session.generated.resources.active_session_analyzing
import rangestats.feature.session.generated.resources.active_session_camera_hint
import rangestats.feature.session.generated.resources.active_session_finish
import rangestats.feature.session.generated.resources.active_session_header_meta_format
import rangestats.feature.session.generated.resources.active_session_prev_targets
import rangestats.feature.session.generated.resources.active_session_shot_icon
import rangestats.feature.session.generated.resources.active_session_stat_avg_score
import rangestats.feature.session.generated.resources.active_session_stat_hits
import rangestats.feature.session.generated.resources.active_session_stat_misses
import rangestats.feature.session.generated.resources.active_session_stat_targets
import rangestats.feature.session.generated.resources.active_session_target_icon
import rangestats.feature.session.generated.resources.active_session_target_label_format
import rangestats.feature.session.generated.resources.active_session_target_meta_format
import rangestats.feature.session.generated.resources.active_session_targets_added_format
import rangestats.feature.session.generated.resources.active_session_title
import rangestats.feature.session.generated.resources.common_nav_back
import rangestats.feature.session.generated.resources.common_percent_format

@Composable
fun ActiveSessionScreen(
    state: ActiveSessionUiModel,
    onAction: (ActiveSessionAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TacBgDeep),
    ) {
        ActiveSessionHeader(state = state, onBack = { onAction(ActiveSessionAction.Back) })
        SessionStatsBar(state = state)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimen.dp16, vertical = Dimen.dp14)
                .height(Dimen.dp100)
                .background(TacBgDeep, RoundedCornerShape(Dimen.dp8))
                .border(Dimen.dp1, TacBorder, RoundedCornerShape(Dimen.dp8)),
            contentAlignment = Alignment.Center,
        ) {
            CameraCorners()
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Dimen.dp8),
            ) {
                Text(
                    text = stringResource(Res.string.active_session_target_icon),
                    color = TacTextMuted,
                    fontSize = FontSize.sp32,
                )
                Text(
                    text = stringResource(Res.string.active_session_camera_hint),
                    color = TacTextMuted,
                    fontSize = FontSize.sp10,
                    letterSpacing = LetterSpacing.em6,
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimen.dp16),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(Res.string.active_session_prev_targets),
                color = TacTextMuted,
                fontSize = FontSize.sp9,
                letterSpacing = LetterSpacing.em12,
            )
            Text(
                text = stringResource(Res.string.active_session_targets_added_format, state.targetCount),
                color = TacTextMuted,
                fontSize = FontSize.sp10,
            )
        }
        Spacer(Modifier.height(Dimen.dp8))
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = Dimen.dp16),
            verticalArrangement = Arrangement.spacedBy(Dimen.dp5),
        ) {
            items(state.targets.reversed()) { target ->
                TargetRow(target = target)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimen.dp16),
        ) {
            FinishButton(onClick = { onAction(ActiveSessionAction.FinishSession) })
        }
    }
}

@Composable
private fun ActiveSessionHeader(state: ActiveSessionUiModel, onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TacBgPanel)
            .padding(horizontal = Dimen.dp16, vertical = Dimen.dp12),
        verticalAlignment = Alignment.CenterVertically,
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
        Spacer(Modifier.width(Dimen.dp12))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(Res.string.active_session_title),
                color = TacAccent,
                fontSize = FontSize.sp11,
                fontWeight = FontWeight.Medium,
                letterSpacing = LetterSpacing.em12,
            )
            Text(
                text = stringResource(Res.string.active_session_header_meta_format, state.locationName, state.caliber, state.distanceMeters),
                color = TacTextMuted,
                fontSize = FontSize.sp11,
            )
        }
        Text(
            text = state.elapsedTimeFormatted,
            color = TacTextMuted,
            fontSize = FontSize.sp10,
        )
    }
}

@Composable
private fun SessionStatsBar(state: ActiveSessionUiModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TacBgCard)
            .border(width = Dimen.dp1, color = TacBorder)
            .padding(horizontal = Dimen.dp16, vertical = Dimen.dp8),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        StatItem(value = state.targetCount.toString(), label = stringResource(Res.string.active_session_stat_targets))
        StatDivider()
        StatItem(value = stringResource(Res.string.common_percent_format, state.avgScore.toInt()), label = stringResource(Res.string.active_session_stat_avg_score))
        StatDivider()
        StatItem(value = state.totalHits.toString(), label = stringResource(Res.string.active_session_stat_hits))
        StatDivider()
        StatItem(value = state.totalMisses.toString(), label = stringResource(Res.string.active_session_stat_misses))
    }
}

@Composable
private fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            color = TacAccent,
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
private fun StatDivider() {
    Box(
        modifier = Modifier
            .width(Dimen.dp1)
            .height(Dimen.dp26)
            .background(TacBorder),
    )
}

@Composable
private fun CameraCorners() {
    val cornerSize = Dimen.dp16
    val strokeWidth = Dimen.dp2
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .size(cornerSize)
                .align(Alignment.TopStart)
                .padding(Dimen.dp8)
                .border(
                    width = strokeWidth,
                    color = TacAccent,
                    shape = RoundedCornerShape(topStart = Dimen.dp3),
                ),
        )
        Box(
            modifier = Modifier
                .size(cornerSize)
                .align(Alignment.TopEnd)
                .padding(Dimen.dp8)
                .border(
                    width = strokeWidth,
                    color = TacAccent,
                    shape = RoundedCornerShape(topEnd = Dimen.dp3),
                ),
        )
        Box(
            modifier = Modifier
                .size(cornerSize)
                .align(Alignment.BottomStart)
                .padding(Dimen.dp8)
                .border(
                    width = strokeWidth,
                    color = TacAccent,
                    shape = RoundedCornerShape(bottomStart = Dimen.dp3),
                ),
        )
        Box(
            modifier = Modifier
                .size(cornerSize)
                .align(Alignment.BottomEnd)
                .padding(Dimen.dp8)
                .border(
                    width = strokeWidth,
                    color = TacAccent,
                    shape = RoundedCornerShape(bottomEnd = Dimen.dp3),
                ),
        )
    }
}

@Composable
private fun TargetRow(target: TargetEntry) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TacBgCard, RoundedCornerShape(Dimen.dp8))
            .border(Dimen.dp1, TacBorder, RoundedCornerShape(Dimen.dp8))
            .padding(horizontal = Dimen.dp12, vertical = Dimen.dp8),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimen.dp12),
    ) {
        Box(
            modifier = Modifier
                .size(Dimen.dp32)
                .background(TacBgElevated, RoundedCornerShape(Dimen.dp4)),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = stringResource(Res.string.active_session_shot_icon), color = TacTextMuted, fontSize = FontSize.sp16)
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(Res.string.active_session_target_label_format, target.index),
                color = TacTextSecondary,
                fontSize = FontSize.sp11,
                fontWeight = FontWeight.Medium,
            )
            val analysisResult = target.analysisResult
            if (analysisResult != null) {
                val misses = analysisResult.zones[pl.kacper.misterski.rangestats.core.domain.enums.TargetZone.MISS] ?: 0
                val hits = analysisResult.shotCount - misses
                Text(
                    text = stringResource(Res.string.active_session_target_meta_format, hits, misses),
                    color = TacTextMuted,
                    fontSize = FontSize.sp10,
                )
            }
        }
        if (target.isAnalyzing) {
            Row(
                modifier = Modifier
                    .background(TacBgDeep, RoundedCornerShape(Dimen.dp4))
                    .border(Dimen.dp1, TacAccent, RoundedCornerShape(Dimen.dp4))
                    .padding(horizontal = Dimen.dp8, vertical = Dimen.dp4),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimen.dp4),
            ) {
                Box(
                    modifier = Modifier
                        .size(Dimen.dp5)
                        .background(TacAccent, CircleShape),
                )
                Text(
                    text = stringResource(Res.string.active_session_analyzing),
                    color = TacAccent,
                    fontSize = FontSize.sp9,
                    letterSpacing = LetterSpacing.em10,
                )
            }
        } else {
            target.analysisResult?.let {
                Text(
                    text = stringResource(Res.string.common_percent_format, it.score.toInt()),
                    color = TacAccent,
                    fontSize = FontSize.sp16,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}

@Composable
private fun FinishButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TacBgCard, RoundedCornerShape(Dimen.dp8))
            .border(Dimen.dp1, TacBorder, RoundedCornerShape(Dimen.dp8))
            .clickable(onClick = onClick)
            .padding(vertical = Dimen.dp12),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(Res.string.active_session_finish),
            color = TacTextMuted,
            fontSize = FontSize.sp11,
            letterSpacing = LetterSpacing.em10,
        )
    }
}

@Preview
@Composable
private fun ActiveSessionScreenPreview() {
    RangeStatsTheme {
        ActiveSessionScreen(
            state = ActiveSessionUiModel(
                sessionId = "1",
                locationName = "Strzelnica Łódź",
                caliber = "9mm",
                distanceMeters = 25,
                elapsedSeconds = 1122,
                targetCount = 3,
                avgScore = 87f,
                totalHits = 62,
                totalMisses = 8,
            ),
            onAction = {},
        )
    }
}
