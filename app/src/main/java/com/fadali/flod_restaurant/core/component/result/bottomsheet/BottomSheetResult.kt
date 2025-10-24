package com.fadali.flod_restaurant.core.component.result.bottomsheet

import com.fadali.flod_restaurant.core.util.DeferredString

sealed class BottomSheetResult(
    open val message: DeferredString,
    open val primaryActionText: DeferredString,
    open val onPrimaryActionClicked: () -> Unit
) {
    data class Success(
        override val message: DeferredString,
        override val primaryActionText: DeferredString,
        override val onPrimaryActionClicked: () -> Unit
    ) : BottomSheetResult(
        message = message,
        primaryActionText = primaryActionText,
        onPrimaryActionClicked = onPrimaryActionClicked
    )

    data class Failure(
        override val message: DeferredString,
        override val primaryActionText: DeferredString,
        override val onPrimaryActionClicked: () -> Unit
    ) : BottomSheetResult(
        message = message,
        primaryActionText = primaryActionText,
        onPrimaryActionClicked = onPrimaryActionClicked
    )

    data object None : BottomSheetResult(
        message = DeferredString.StringValue(""),
        primaryActionText = DeferredString.StringValue(""),
        onPrimaryActionClicked = {}
    )
}
