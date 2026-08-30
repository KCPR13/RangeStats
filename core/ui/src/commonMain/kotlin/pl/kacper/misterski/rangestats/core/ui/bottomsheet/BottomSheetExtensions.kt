package pl.kacper.misterski.rangestats.core.ui.bottomsheet

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.compose.DialogNavigatorDestinationBuilder
import androidx.navigation.get

fun NavGraphBuilder.bottomSheet(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    bottomSheetProperties: BottomSheetConfiguration = BottomSheetConfiguration(),
    content: @Composable (NavBackStackEntry) -> Unit,
) {
    destination(
        navDestination =
            DialogNavigatorDestinationBuilder(
                navigator = provider[DialogNavigator::class],
                route = route,
                dialogProperties = bottomSheetProperties.toDialogProperties(),
                content = content,
            ).apply {
                arguments.forEach { (argumentName, argument) -> argument(argumentName, argument) }
            },
    )
}
