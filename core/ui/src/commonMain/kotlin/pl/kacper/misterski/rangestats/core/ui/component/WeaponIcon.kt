package pl.kacper.misterski.rangestats.core.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import pl.kacper.misterski.rangestats.core.domain.enums.WeaponType
import pl.kacper.misterski.rangestats.core.ui.theme.Dimen
import pl.kacper.misterski.rangestats.core.ui.theme.RangeStatsTheme
import pl.kacper.misterski.rangestats.core.ui.theme.TacAccent

@Composable
fun WeaponIcon(
    model: WeaponIconUiModel,
    modifier: Modifier = Modifier,
    tint: Color = TacAccent,
) {
    Icon(
        painter = painterResource(model.icon),
        contentDescription = stringResource(model.title),
        modifier = modifier,
        tint = tint,
    )
}

@Preview
@Composable
private fun WeaponIconAllTypesPreview() {
    RangeStatsTheme {
        Row {
            WeaponType.entries.forEach { type ->
                WeaponIcon(model = type.toUiModel(), modifier = Modifier.padding(Dimen.dp8))
            }
        }
    }
}
