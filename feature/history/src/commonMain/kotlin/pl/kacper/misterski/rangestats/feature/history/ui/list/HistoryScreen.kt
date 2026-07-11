package pl.kacper.misterski.rangestats.feature.history.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource
import pl.kacper.misterski.rangestats.core.ui.component.TacScaffold
import pl.kacper.misterski.rangestats.core.ui.enums.BottomNavDestination
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.FontSize
import pl.kacper.misterski.rangestats.core.ui.theme.LetterSpacing
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgCard
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgDeep
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgPanel
import pl.kacper.misterski.rangestats.core.ui.theme.TacBorder
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextMuted
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextPrimary
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextSecondary
import rangestats.feature.history.generated.resources.Res
import rangestats.feature.history.generated.resources.common_score_empty
import rangestats.feature.history.generated.resources.history_empty
import rangestats.feature.history.generated.resources.history_session_distance_format
import rangestats.feature.history.generated.resources.history_session_score_format
import rangestats.feature.history.generated.resources.history_session_shots_format
import rangestats.feature.history.generated.resources.history_title

@Composable
fun HistoryScreen(
    state: HistoryUiModel,
    onAction: (HistoryAction) -> Unit,
    onNavigate: (BottomNavDestination) -> Unit,
) {
    TacScaffold(
        selectedNav = BottomNavDestination.History,
        onNavigate = onNavigate,
        topBar = { HistoryTopBar() },
    ) { padding ->
        if (state.sessions.isEmpty() && !state.isLoading) {
            EmptyState(modifier = Modifier.padding(padding))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(Dimen.dp16),
                verticalArrangement = Arrangement.spacedBy(Dimen.dp8),
            ) {
                items(state.sessions) { session ->
                    SessionCard(
                        item = session,
                        onClick = { onAction(HistoryAction.OpenDetail(session.id)) },
                    )
                }
            }
        }
    }
}

@Composable
private fun HistoryTopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(TacBgPanel)
            .border(width = Dimen.dp1, color = TacBorder)
            .padding(horizontal = Dimen.dp20, vertical = Dimen.dp14),
    ) {
        Text(
            text = stringResource(Res.string.history_title),
            color = TacAccent,
            fontSize = FontSize.sp11,
            fontWeight = FontWeight.Medium,
            letterSpacing = LetterSpacing.em12,
        )
    }
}

@Composable
private fun SessionCard(item: SessionListItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TacBgCard, RoundedCornerShape(Dimen.dp8))
            .border(Dimen.dp1, TacBorder, RoundedCornerShape(Dimen.dp8))
            .clickable(onClick = onClick)
            .padding(horizontal = Dimen.dp16, vertical = Dimen.dp12),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimen.dp12),
    ) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(Dimen.dp2)) {
            Text(
                text = item.locationName,
                color = TacTextPrimary,
                fontSize = FontSize.sp13,
                fontWeight = FontWeight.Medium,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(Dimen.dp8)) {
                Text(
                    text = item.dateLabel,
                    color = TacTextMuted,
                    fontSize = FontSize.sp10,
                )
                Text(
                    text = stringResource(Res.string.history_session_distance_format, item.distanceMeters),
                    color = TacTextMuted,
                    fontSize = FontSize.sp10,
                )
                Text(
                    text = stringResource(Res.string.history_session_shots_format, item.shotCount),
                    color = TacTextMuted,
                    fontSize = FontSize.sp10,
                )
            }
        }
        if (item.score != null) {
            Text(
                text = stringResource(Res.string.history_session_score_format, item.score.toInt()),
                color = TacAccent,
                fontSize = FontSize.sp16,
                fontWeight = FontWeight.SemiBold,
            )
        } else {
            Text(
                text = stringResource(Res.string.common_score_empty),
                color = TacTextSecondary,
                fontSize = FontSize.sp16,
            )
        }
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize().background(TacBgDeep),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(Res.string.history_empty),
            color = TacTextMuted,
            fontSize = FontSize.sp13,
        )
    }
}

@Preview
@Composable
private fun HistoryScreenPreview() {
    RangeStatsTheme {
        HistoryScreen(
            state = HistoryUiModel(
                isLoading = false,
                sessions = listOf(
                    SessionListItem(
                        id = 1L,
                        locationName = "Strzelnica Łódź",
                        distanceMeters = 25,
                        dateLabel = "24.05.2025",
                        shotCount = 70,
                        score = 89f,
                    ),
                    SessionListItem(
                        id = 2L,
                        locationName = "Strzelnica Warszawa",
                        distanceMeters = 10,
                        dateLabel = "18.05.2025",
                        shotCount = 50,
                        score = null,
                    ),
                ),
            ),
            onAction = {},
            onNavigate = {},
        )
    }
}
