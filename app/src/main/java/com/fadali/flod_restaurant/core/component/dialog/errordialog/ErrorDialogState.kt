package com.fadali.flod_restaurant.core.component.dialog.errordialog

import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.core.util.DeferredString

data class ErrorDialogState(
    val title: DeferredString,
    val message: DeferredString,
    val primaryButtonText: DeferredString,
    val secondaryButtonText: DeferredString?,
    val isDismissible: Boolean,
    val onPrimaryBtnClicked: () -> Unit,
    val onSecondaryBtnClicked: () -> Unit,
    val onDismiss: () -> Unit,
) {
    companion object {
        val default = ErrorDialogState(
            title = DeferredString.StringResource(R.string.error_occurred),
            message = DeferredString.StringResource(R.string.something_went_wrong),
            primaryButtonText = DeferredString.StringResource(R.string.try_again),
            secondaryButtonText = null,
            isDismissible = true,
            onPrimaryBtnClicked = {},
            onSecondaryBtnClicked = {},
            onDismiss = {}
        )
    }
}
