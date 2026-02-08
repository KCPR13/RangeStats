package pl.kacper.misterski.rangestats.ui.common.bottom_sheet

import androidx.compose.ui.window.DialogProperties

fun BottomSheetConfiguration.toDialogProperties() = DialogProperties(
    dismissOnBackPress = dismissOnBackPress,
    dismissOnClickOutside = dismissOnClickOutside,
    usePlatformDefaultWidth = usePlatformDefaultWidth
)
