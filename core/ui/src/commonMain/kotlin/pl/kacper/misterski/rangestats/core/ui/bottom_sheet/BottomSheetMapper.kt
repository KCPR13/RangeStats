package pl.kacper.misterski.rangestats.core.ui.bottom_sheet

import androidx.compose.ui.window.DialogProperties

fun BottomSheetConfiguration.toDialogProperties() = DialogProperties(
    dismissOnBackPress = dismissOnBackPress,
    dismissOnClickOutside = dismissOnClickOutside,
    usePlatformDefaultWidth = usePlatformDefaultWidth
)
