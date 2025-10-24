package com.fadali.flod_restaurant.feature.subscription.ui.planstartingdate

import com.fadali.flod_restaurant.app.navigation.routes.SubscriptionRoutes
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.network.appconfig.AppConfig
import com.fadali.flod_restaurant.core.network.appconfig.DayOfWeek
import com.fadali.flod_restaurant.feature.subscription.domain.model.timeslots.DeliveryTimeSlot
import com.smyvo.core.ui.mvi.UiEffect
import com.smyvo.core.ui.mvi.UiEvent
import com.smyvo.core.ui.mvi.UiState
import java.time.LocalDate

data class PlanStartingDateState(
    val isLoading: Boolean = false,
    val selectedDays: Set<DayOfWeek> = defaultSelectedDays,
    val startingDates: List<LocalDate> = emptyList(),
    val selectedStartingDate: LocalDate? = null,
    val displayableStartingDates: List<String> = emptyList(),
    val selectedStartingDateIndex: Int = 0,
    val daysOfWeek: List<DayOfWeek> = AppConfig.default.availableDaysOfWeek,
    val timeSlots: List<DeliveryTimeSlot> = emptyList(),
    val selectedTimeSlotIndex: Int = 0,
    val error: String? = null,
    val args: SubscriptionRoutes.PlanStartingDate? = null,
    val phoneNumber: String = "",
    val isContinueBtnEnabled: Boolean = true,
) : UiState {
    companion object {
        val defaultSelectedDays: Set<DayOfWeek> =
            AppConfig.default.availableDaysOfWeek.toSet() - setOf(DayOfWeek.SATURDAY)

        const val MIN_DEFAULT_SELECTED_DAYS = 5

    }
}

sealed class PlanStartingDateUiEvent : UiEvent {
    data class LoadData(val args: SubscriptionRoutes.PlanStartingDate) : PlanStartingDateUiEvent()
    data class OnStartingDateSelected(val index: Int) : PlanStartingDateUiEvent()
    data class OnSelectedDaysChanged(val days: Set<DayOfWeek>) : PlanStartingDateUiEvent()
    data class OnTimeSlotSelected(val index: Int) : PlanStartingDateUiEvent()
    object OnContinueBtnClicked : PlanStartingDateUiEvent()
    object OnBackPressed : PlanStartingDateUiEvent()

}

sealed class PlanStartingDateUiEffect : UiEffect {
    data class NavigateToNextScreen(val route: SubscriptionRoutes) : PlanStartingDateUiEffect()
    data object NavigateBack : PlanStartingDateUiEffect()
    data class ShowError(val dialogState: ErrorDialogState?) : PlanStartingDateUiEffect()
}