package pl.kacper.misterski.rangestats.feature.onboarding.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import pl.kacper.misterski.rangestats.core.ui.util.rememberSingleClick
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.core.ui.component.TacButton
import pl.kacper.misterski.rangestats.core.ui.component.WeaponIcon
import pl.kacper.misterski.rangestats.core.ui.component.toUiModel
import pl.kacper.misterski.rangestats.core.ui.theme.PrecisionTrackShapes
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgCard
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgDeep
import pl.kacper.misterski.rangestats.core.ui.theme.TacBgElevated
import pl.kacper.misterski.rangestats.core.ui.theme.TacBorder
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.FontSize
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextMuted
import pl.kacper.misterski.rangestats.core.ui.theme.TacTextSecondary
import pl.kacper.misterski.rangestats.core.ui.util.singleClickable
import pl.kacper.misterski.rangestats.feature.onboarding.ui.OnboardingAction
import pl.kacper.misterski.rangestats.feature.onboarding.ui.OnboardingUiModel
import pl.kacper.misterski.rangestats.feature.onboarding.ui.common.OnboardingDescription
import pl.kacper.misterski.rangestats.feature.onboarding.ui.common.OnboardingIconBox
import pl.kacper.misterski.rangestats.feature.onboarding.ui.common.OnboardingTitle
import rangestats.feature.onboarding.generated.resources.Res
import rangestats.feature.onboarding.generated.resources.ic_ob_weapon
import rangestats.feature.onboarding.generated.resources.onboarding_arsenal_add_icon
import rangestats.feature.onboarding.generated.resources.onboarding_arsenal_add_weapon
import rangestats.feature.onboarding.generated.resources.onboarding_arsenal_desc
import rangestats.feature.onboarding.generated.resources.onboarding_arsenal_title
import rangestats.feature.onboarding.generated.resources.onboarding_btn_done

@Composable
internal fun ArsenalPage(state: OnboardingUiModel, onAction: (OnboardingAction) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimen.dp20),
        verticalArrangement = Arrangement.spacedBy(Dimen.dp16)
    ) {

        Spacer(Modifier.height(Dimen.dp20))

        OnboardingIconBox(
            painter = painterResource(Res.drawable.ic_ob_weapon),
            accentBorder = true,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )


        OnboardingTitle(text = stringResource(Res.string.onboarding_arsenal_title))

        OnboardingDescription(text = stringResource(Res.string.onboarding_arsenal_desc))

        if (state.weapons.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(Dimen.dp8),
            ) {
                state.weapons.forEach { weapon -> ArsenalWeaponRow(weapon) }
            }
        } else {
            Spacer(Modifier.weight(1f))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(PrecisionTrackShapes.medium)
                .border(Dimen.dp1, TacBorder, PrecisionTrackShapes.medium)
                .singleClickable { onAction(OnboardingAction.AddNewWeapon) }
                .padding(Dimen.dp12),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(Res.string.onboarding_arsenal_add_icon),
                color = TacTextMuted,
                fontSize = FontSize.sp18
            )
            Spacer(Modifier.width(Dimen.dp8))
            Text(
                text = stringResource(Res.string.onboarding_arsenal_add_weapon),
                color = TacTextMuted,
                fontSize = FontSize.sp18,
                letterSpacing = FontSize.sp0_1,
            )
        }

        TacButton(
            text = stringResource(Res.string.onboarding_btn_done),
            onClick = rememberSingleClick { onAction(OnboardingAction.Complete) },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(Dimen.dp32))
    }
}

@Composable
private fun ArsenalWeaponRow(weapon: OnboardingUiModel.WeaponRowUiModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TacBgCard, RoundedCornerShape(Dimen.dp7))
            .border(Dimen.dp1, TacBorder, RoundedCornerShape(Dimen.dp7))
            .padding(horizontal = Dimen.dp14, vertical = Dimen.dp12),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(Dimen.dp38)
                .background(TacBgElevated, RoundedCornerShape(Dimen.dp7)),
            contentAlignment = Alignment.Center,
        ) {
            WeaponIcon(model = weapon.icon, modifier = Modifier.size(Dimen.dp24))
        }
        Spacer(Modifier.width(Dimen.dp12))
        Column {
            Text(
                text = weapon.name,
                color = TacTextSecondary,
                fontSize = FontSize.sp13,
                fontWeight = FontWeight.Medium,
            )
            Text(text = weapon.caliber, color = TacTextMuted, fontSize = FontSize.sp10)
        }
    }
}

@Preview
@Composable
private fun ArsenalPagePreview() {
    RangeStatsTheme {
        Box(modifier = Modifier.background(TacBgDeep)) {
            ArsenalPage(
                state = OnboardingUiModel(
                    weapons = listOf(
                        OnboardingUiModel.WeaponRowUiModel(
                            name = "Glock 17",
                            caliber = "9mm",
                            icon = WeaponType.PISTOL.toUiModel(),
                        ),
                    ),
                ),
                onAction = {},
            )
        }
    }
}
