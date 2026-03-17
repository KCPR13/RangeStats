package pl.kacper.misterski.rangestats.feature.onboarding.ui.pages

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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import pl.kacper.misterski.rangestats.core.domain.enums.UnitSystem
import pl.kacper.misterski.rangestats.core.ui.component.TacButton
import pl.kacper.misterski.rangestats.core.ui.component.TacStepper
import pl.kacper.misterski.rangestats.core.ui.theme.PrecisionTrackShapes
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccentDim
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgCard
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgDeep
import pl.kacper.misterski.rangestats.core.ui.theme.TacBorder
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextMuted
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.FontSize
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextPrimary
import pl.kacper.misterski.rangestats.feature.onboarding.ui.OnboardingAction
import pl.kacper.misterski.rangestats.feature.onboarding.ui.OnboardingPage
import pl.kacper.misterski.rangestats.feature.onboarding.ui.OnboardingUiModel
import pl.kacper.misterski.rangestats.feature.onboarding.ui.common.OnboardingDescription
import pl.kacper.misterski.rangestats.feature.onboarding.ui.common.OnboardingIconBox
import pl.kacper.misterski.rangestats.feature.onboarding.ui.common.OnboardingTitle
import rangestats.feature.onboarding.generated.resources.Res
import rangestats.feature.onboarding.generated.resources.ic_ob_profile
import rangestats.feature.onboarding.generated.resources.onboarding_btn_next
import rangestats.feature.onboarding.generated.resources.onboarding_distance_hint
import rangestats.feature.onboarding.generated.resources.onboarding_label_distance
import rangestats.feature.onboarding.generated.resources.onboarding_label_name
import rangestats.feature.onboarding.generated.resources.onboarding_label_units
import rangestats.feature.onboarding.generated.resources.onboarding_placeholder_name
import rangestats.feature.onboarding.generated.resources.onboarding_profile_desc
import rangestats.feature.onboarding.generated.resources.onboarding_profile_title
import rangestats.feature.onboarding.generated.resources.onboarding_unit_imperial
import rangestats.feature.onboarding.generated.resources.onboarding_unit_metric

//TODO hardcoded
//TODO business logic
@Composable
internal fun ProfilePage(
    state: OnboardingUiModel,
    onAction: (OnboardingAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimen.dp20),
        verticalArrangement = Arrangement.spacedBy(Dimen.dp20)
    ) {
        Spacer(Modifier.height(Dimen.dp40))

        OnboardingIconBox(
            painter = painterResource(Res.drawable.ic_ob_profile),
            accentBorder = true,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )

        OnboardingTitle(text = stringResource(Res.string.onboarding_profile_title))

        OnboardingDescription(text = stringResource(Res.string.onboarding_profile_desc))

        SectionLabel(stringResource(Res.string.onboarding_label_name))

        BasicTextField(
            value = state.displayName,
            onValueChange = { onAction(OnboardingAction.UpdateDisplayName(it)) },
            singleLine = true,
            decorationBox = { inner ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(PrecisionTrackShapes.small)
                        .background(TacBgCard)
                        .border(
                            Dimen.dp1,
                            if (state.displayName.isNotEmpty()) TacAccent else TacBorder,
                            PrecisionTrackShapes.small,
                        )
                        .padding(horizontal = Dimen.dp12, vertical = Dimen.dp12),
                ) {
                    if (state.displayName.isEmpty()) {
                        Text(
                            text = stringResource(Res.string.onboarding_placeholder_name),
                            color = TacTextMuted,
                            fontSize = FontSize.sp13,
                        )
                    }
                    inner()
                }
            },
            textStyle = TextStyle(color = TacTextPrimary, fontSize = FontSize.sp13),
        )

        SectionLabel(stringResource(Res.string.onboarding_label_units))

        Row(horizontalArrangement = Arrangement.spacedBy(Dimen.dp8)) {
            UnitChip(
                label = stringResource(Res.string.onboarding_unit_metric),
                selected = state.unitSystem == UnitSystem.METRIC,
                onClick = { onAction(OnboardingAction.SelectUnitSystem(UnitSystem.METRIC)) },
                modifier = Modifier.weight(1f),
            )
            UnitChip(
                label = stringResource(Res.string.onboarding_unit_imperial),
                selected = state.unitSystem == UnitSystem.IMPERIAL,
                onClick = { onAction(OnboardingAction.SelectUnitSystem(UnitSystem.IMPERIAL)) },
                modifier = Modifier.weight(1f),
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SectionLabel(stringResource(Res.string.onboarding_label_distance))
            Text(
                text = stringResource(Res.string.onboarding_distance_hint),
                color = TacTextMuted,
                fontSize = FontSize.sp9,
            )
        }

        TacStepper(
            value = state.defaultDistanceMeters,
            onDecrement = { onAction(OnboardingAction.UpdateDistance(state.defaultDistanceMeters - 5)) },
            onIncrement = { onAction(OnboardingAction.UpdateDistance(state.defaultDistanceMeters + 5)) },
            min = 5,
            max = 1000,
            label = "${state.defaultDistanceMeters} m",
        )

        Spacer(Modifier.weight(1f))

        TacButton(
            text = stringResource(Res.string.onboarding_btn_next),
            onClick = { onAction(OnboardingAction.NextPage) },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(Dimen.dp32))
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text.uppercase(),
        color = TacTextMuted,
        fontSize = FontSize.sp9,
        letterSpacing = FontSize.sp0_1,
        modifier = Modifier.padding(bottom = Dimen.dp4),
    )
}

@Composable
private fun UnitChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(PrecisionTrackShapes.small)
            .background(if (selected) TacAccentDim else TacBgCard)
            .border(
                width = Dimen.dp1,
                color = if (selected) TacAccent else TacBorder,
                shape = PrecisionTrackShapes.small,
            )
            .clickable { onClick() }
            .padding(vertical = Dimen.dp8),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            color = if (selected) TacAccent else TacTextMuted,
            fontSize = FontSize.sp11,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun ProfilePagePreview() {
    RangeStatsTheme {
        Box(modifier = Modifier.background(TacBgDeep)) {
            ProfilePage(
                state = OnboardingUiModel(
                    currentPage = OnboardingPage.PROFILE,
                    displayName = "Operator"
                ),
                onAction = {},
            )
        }
    }
}
