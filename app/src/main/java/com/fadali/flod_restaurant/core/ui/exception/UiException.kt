package com.fadali.flod_restaurant.core.ui.exception

import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.smyvo.core.utils.exception.BaseException

data class UiException(val dialogState: ErrorDialogState) : BaseException(null)
