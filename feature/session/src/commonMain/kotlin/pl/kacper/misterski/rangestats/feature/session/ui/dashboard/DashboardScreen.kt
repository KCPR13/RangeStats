package pl.kacper.misterski.rangestats.feature.session.ui.dashboard

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
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource
import pl.kacper.misterski.rangestats.core.domain.enums.TargetType
import pl.kacper.misterski.rangestats.core.domain.models.Session
import pl.kacper.misterski.rangestats.core.ui.component.TacScaffold
import pl.kacper.misterski.rangestats.core.ui.enums.BottomNavDestination
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.FontSize
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgCard
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgElevated
import pl.kacper.misterski.rangestats.core.ui.theme.TacBorder
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgPanel
import pl.kacper.misterski.rangestats.core.ui.theme.TacGreen
import pl.kacper.misterski.rangestats.core.ui.theme.TacRed
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextMuted
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextPrimary
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextSecondary
import rangestats.feature.session.generated.resources.Res
import rangestats.feature.session.generated.resources.common_percent_format
import rangestats.feature.session.generated.resources.dashboard_app_logo
import rangestats.feature.session.generated.resources.dashboard_distance_m_format
import rangestats.feature.session.generated.resources.dashboard_empty_dash
import rangestats.feature.session.generated.resources.dashboard_history_link
import rangestats.feature.session.generated.resources.dashboard_last_session
import rangestats.feature.session.generated.resources.dashboard_progress_label
import rangestats.feature.session.generated.resources.dashboard_score_label
import rangestats.feature.session.generated.resources.dashboard_stat_avg_score
import rangestats.feature.session.generated.resources.dashboard_stat_best
import rangestats.feature.session.generated.resources.dashboard_stat_sessions
import rangestats.feature.session.generated.resources.dashboard_stat_shots
import rangestats.feature.session.generated.resources.dashboard_subtitle
import rangestats.feature.session.generated.resources.dashboard_title
import rangestats.feature.session.generated.resources.dashboard_total_suffix

@Composable
fun DashboardScreen(
    state: DashboardUiModel,
    onAction: (DashboardAction) -> Unit,
    onNavigate: (BottomNavDestination) -> Unit,
) {
    TacScaffold(
        selectedNav = BottomNavDestination.Home,
        onNavigate = onNavigate,
        topBar = { DashboardHeader() },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Dimen.dp20, vertical = Dimen.dp16),
            verticalArrangement = Arrangement.spacedBy(Dimen.dp14),
        ) {
            StatsGrid(state = state)
            ProgressSection()
            LastSessionSection(
                session = state.lastSession,
                onHistoryClick = { onAction(DashboardAction.OpenHistory) },
            )
        }
    }
}

@Composable
private fun DashboardHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TacBgPanel)
            .safeContentPadding(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(
                text = stringResource(Res.string.dashboard_title),
                color = TacAccent,
                fontSize = FontSize.sp11,
                fontWeight = FontWeight.Medium,
                letterSpacing = androidx.compose.ui.unit.TextUnit( //TODO K
                    0.12f,
                    androidx.compose.ui.unit.TextUnitType.Em,
                ),
            )
            Text(
                text = stringResource(Res.string.dashboard_subtitle),
                color = TacTextMuted,
                fontSize = FontSize.sp13,
            )
        }
        Box(
            modifier = Modifier
                .size(Dimen.dp38)
                .background(TacBgElevated, RoundedCornerShape(Dimen.dp4))
                .border(Dimen.dp1, TacBorder, RoundedCornerShape(Dimen.dp4)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(Res.string.dashboard_app_logo),
                color = TacAccent,
                fontSize = FontSize.sp13,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
private fun StatsGrid(state: DashboardUiModel) { // TODO business logic
    val avgScoreStr = if (state.avgScore > 0f) "${state.avgScore.toInt()}%" else "—"
    val bestScoreStr = if (state.bestScore > 0f) "${state.bestScore.toInt()}%" else "—"
    val shotsStr = state.shotsLabel

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimen.dp8),
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Dimen.dp8),
        ) {
            StatCard(
                label = stringResource(Res.string.dashboard_stat_sessions),
                value = state.totalSessions.toString(),
                valueSuffix = stringResource(Res.string.dashboard_total_suffix),
                delta = null,
            )
            StatCard(
                label = stringResource(Res.string.dashboard_stat_shots),
                value = shotsStr,
                valueSuffix = null,
                delta = null,
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Dimen.dp8),
        ) {
            StatCard(
                label = stringResource(Res.string.dashboard_stat_avg_score),
                value = avgScoreStr,
                valueSuffix = null,
                delta = null,
            )
            StatCard(
                label = stringResource(Res.string.dashboard_stat_best),
                value = bestScoreStr,
                valueSuffix = null,
                delta = null,
            )
        }
    }
}

@Composable
private fun StatCard(
    label: String,
    value: String,
    valueSuffix: String?,
    delta: String?,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(TacBgCard, RoundedCornerShape(Dimen.dp8))
            .border(Dimen.dp1, TacBorder, RoundedCornerShape(Dimen.dp8))
            .padding(horizontal = Dimen.dp14, vertical = Dimen.dp12),
    ) {
        Text(
            text = label,
            color = TacTextMuted,
            fontSize = FontSize.sp10,
            letterSpacing = androidx.compose.ui.unit.TextUnit( // TODO
                0.1f,
                androidx.compose.ui.unit.TextUnitType.Em,
            ),
        )
        Spacer(Modifier.height(Dimen.dp4))
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = value,
                color = TacTextPrimary,
                fontSize = FontSize.sp24,
                fontWeight = FontWeight.SemiBold,
                lineHeight = FontSize.sp24,
            )
            if (valueSuffix != null) {
                Text(
                    text = valueSuffix,
                    color = TacTextMuted,
                    fontSize = FontSize.sp13,
                    modifier = Modifier.padding(start = Dimen.dp4, bottom = Dimen.dp4),
                )
            }
        }
        if (delta != null) {
            val isPositive = delta.startsWith("▲")  // TODO business logic
            Text(
                text = delta,
                color = if (isPositive) TacGreen else TacRed,
                fontSize = FontSize.sp11,
            )
        }
    }
}

@Composable
private fun ProgressSection() {
    Column {
        Text(
            text = stringResource(Res.string.dashboard_progress_label),
            color = TacTextMuted,
            fontSize = FontSize.sp10,
            letterSpacing = androidx.compose.ui.unit.TextUnit(
                0.12f,
                androidx.compose.ui.unit.TextUnitType.Em,
            ),
        )
        Spacer(Modifier.height(Dimen.dp8))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimen.dp100)
                .background(TacBgCard, RoundedCornerShape(Dimen.dp8))
                .border(Dimen.dp1, TacBorder, RoundedCornerShape(Dimen.dp8)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(Res.string.dashboard_empty_dash),
                color = TacTextMuted,
                fontSize = FontSize.sp12,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun LastSessionSection(
    session: Session?,
    onHistoryClick: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(Res.string.dashboard_last_session),
                color = TacTextMuted,
                fontSize = FontSize.sp10,
                letterSpacing = androidx.compose.ui.unit.TextUnit( //TODO
                    0.12f,
                    androidx.compose.ui.unit.TextUnitType.Em,
                ),
            )
            Text(
                text = stringResource(Res.string.dashboard_history_link),
                color = TacAccent,
                fontSize = FontSize.sp10,
                modifier = Modifier.clickable(onClick = onHistoryClick),
            )
        }
        Spacer(Modifier.height(Dimen.dp8))
        if (session != null) {
            LastSessionCard(session = session)
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TacBgCard, RoundedCornerShape(Dimen.dp8))
                    .border(Dimen.dp1, TacBorder, RoundedCornerShape(Dimen.dp8))
                    .padding(Dimen.dp16),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Brak sesji", //TODO
                    color = TacTextMuted,
                    fontSize = FontSize.sp12,
                )
            }
        }
    }
}

@Composable
private fun LastSessionCard(session: Session) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TacBgCard, RoundedCornerShape(Dimen.dp8))
            .border(Dimen.dp1, TacBorder, RoundedCornerShape(Dimen.dp8))
            .padding(horizontal = Dimen.dp14, vertical = Dimen.dp12),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(
                text = session.locationName,
                color = TacTextSecondary,
                fontSize = FontSize.sp13,
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = stringResource(Res.string.dashboard_distance_m_format, session.distanceMeters),
                color = TacTextMuted,
                fontSize = FontSize.sp11,
            )
        }
        val score = session.score
        if (score != null) {
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = stringResource(Res.string.common_percent_format, score.toInt()),
                    color = TacAccent,
                    fontSize = FontSize.sp20,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = FontSize.sp20,
                )
                Text(
                    text = stringResource(Res.string.dashboard_score_label),
                    color = TacTextMuted,
                    fontSize = FontSize.sp10,
                )
            }
        }
    }
}

@Preview
@Composable
private fun DashboardScreenPreview() {
    RangeStatsTheme {
        DashboardScreen(
            state = DashboardUiModel(
                totalSessions = 24,
                avgScore = 87f,
                totalShots = 1200,
                bestScore = 98f,
                lastSession = Session(
                    id = "preview",
                    weaponId = "w1",
                    locationName = "Strzelnica Łódź",
                    distanceMeters = 25,
                    targetType = TargetType.ISSF_ROUND,
                    shots = emptyList(),
                    startedAt = 0L,
                    finishedAt = null,
                    score = 91f,
                ),
            ),
            onAction = {},
            onNavigate = {},
        )
    }
}
