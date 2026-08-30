package pl.kacper.misterski.rangestats.core.ui.bottomsheet

import androidx.compose.ui.window.DialogProperties

fun BottomSheetConfiguration.toDialogProperties() =
    DialogProperties(
        dismissOnBackPress = dismissOnBackPress,
        dismissOnClickOutside = dismissOnClickOutside,
        usePlatformDefaultWidth = usePlatformDefaultWidth,
    )
