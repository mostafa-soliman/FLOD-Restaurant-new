package com.fadali.flod_restaurant.feature.subscription.ui.planstartingdate

import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.app.navigation.routes.SubscriptionRoutes
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.network.appconfig.PackageType
import com.fadali.flod_restaurant.core.util.DeferredString
import com.fadali.flod_restaurant.core.util.toUTCFormat
import com.fadali.flod_restaurant.feature.profile.domain.usecase.GetUserProfileUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.GetDeliveryTimeSlotsUseCase
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import com.smyvo.core.ui.mvi.BaseViewModel
import com.smyvo.core.ui.network.DefaultExceptionHandler
import java.time.LocalDate

class PlanStartingDateViewModel(
    private val getDeliveryTimeSLotsUseCase: GetDeliveryTimeSlotsUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    dispatchers: AppCoroutineDispatcher,
    defaultExceptionHandler: DefaultExceptionHandler
) : BaseViewModel<PlanStartingDateState, PlanStartingDateUiEvent, PlanStartingDateUiEffect>(
    appCoroutineDispatcher = dispatchers,
    defaultExceptionHandler = defaultExceptionHandler
) {

    override fun initialState(): PlanStartingDateState = PlanStartingDateState()

    override fun handleEvent(event: PlanStartingDateUiEvent) {
        when (event) {
            is PlanStartingDateUiEvent.LoadData -> loadData(event)
            is PlanStartingDateUiEvent.OnStartingDateSelected -> onStartingDateSelected(event)
            is PlanStartingDateUiEvent.OnSelectedDaysChanged -> onPickupDaysSelected(event)
            is PlanStartingDateUiEvent.OnTimeSlotSelected -> {
                setState { copy(selectedTimeSlotIndex = event.index) }
            }
            PlanStartingDateUiEvent.OnContinueBtnClicked -> onContinueClicked()
            PlanStartingDateUiEvent.OnBackPressed -> sendEffect(PlanStartingDateUiEffect.NavigateBack)
        }
    }

    private fun loadData(event: PlanStartingDateUiEvent.LoadData) {
        val packageDurationDays = event.args.packageDetails.durationDays
        val availableSelectedDays = if (packageDurationDays < PlanStartingDateState.MIN_DEFAULT_SELECTED_DAYS) {
            PlanStartingDateState.defaultSelectedDays.take(packageDurationDays).toSet()
        } else {
            PlanStartingDateState.defaultSelectedDays
        }

        setState {
            copy(
                args = event.args,
                selectedDays = availableSelectedDays,
            )
        }

        loadStartingDates()
        loadDeliveryTimeSlots()
        updatePhoneNumber()
    }

    private fun onStartingDateSelected(event: PlanStartingDateUiEvent.OnStartingDateSelected) {
        val selectedDate = currentState.startingDates.getOrNull(event.index)
        setState {
            copy(
                selectedStartingDateIndex = event.index,
                selectedStartingDate = selectedDate
            )
        }
    }

    private fun loadDeliveryTimeSlots() {
        if (currentState.args?.packageType == PackageType.PICKUP) return

        setState { copy(isLoading = true) }
        launchSafe {
            val slots = getDeliveryTimeSLotsUseCase(
                addressId = currentState.args?.deliveryAddress?.id ?: "",
                packageId = currentState.args?.packageDetails?.id ?: "",
            )
            setState {
                copy(
                    isLoading = false,
                    timeSlots = slots
                )
            }
        }
    }

    private fun updatePhoneNumber() {
        setState { copy(isLoading = true) }
        launchSafe {
            val profile = getUserProfileUseCase()
            setState { copy(isLoading = false) }
            setState { copy(phoneNumber = profile.phoneNumber) }
        }
    }

    private fun loadStartingDates() {
        val maxNumberOfDays = 3
        val today = LocalDate.now()
        var counter = 1
        val startingDates = mutableListOf<LocalDate>()
        val displayableStartingDates = mutableListOf<String>()
        while (displayableStartingDates.size < maxNumberOfDays) {
            val potentialDate = today.plusDays(counter.toLong())
            val dayOfMonth = potentialDate.dayOfMonth
            val month = potentialDate.month.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)
            startingDates.add(potentialDate)
            displayableStartingDates.add("$dayOfMonth $month")
            counter++
        }
        setState {
            copy(
                startingDates = startingDates,
                selectedStartingDate = startingDates.first(),
                displayableStartingDates = displayableStartingDates,
            )
        }
    }

    private fun onPickupDaysSelected(event: PlanStartingDateUiEvent.OnSelectedDaysChanged) {
        val packageDurationDays = currentState.args?.packageDetails?.durationDays ?: 0
        val availableSelectedDays = if (packageDurationDays < PlanStartingDateState.MIN_DEFAULT_SELECTED_DAYS) {
            event.days.take(packageDurationDays).toSet()
        } else {
            event.days
        }
        setState {
            copy(
                selectedDays = availableSelectedDays,
                isContinueBtnEnabled = availableSelectedDays.isNotEmpty()
            )
        }
    }

    private fun onContinueClicked() {
        val deliveryTimeSlot = if (currentState.timeSlots.isNotEmpty()) {
            currentState.timeSlots[currentState.selectedTimeSlotIndex]
        } else {
            null
        }
        currentState.args?.let {
            sendEffect(
                PlanStartingDateUiEffect.NavigateToNextScreen(
                    SubscriptionRoutes.SubscriptionReview(
                        productCategory = it.productCategory,
                        packageDetails = it.packageDetails,
                        packageType = it.packageType,
                        deliveryAddress = it.deliveryAddress,
                        pickupBranch = it.pickupBranch,
                        phoneNumber = currentState.phoneNumber,
                        startingDateUtc = currentState.selectedStartingDate?.toUTCFormat() ?: "",
                        displayableStartingDate = currentState.displayableStartingDates[currentState.selectedStartingDateIndex],
                        deliveryTimeSlot = deliveryTimeSlot,
                        selectedDays = currentState.selectedDays.toList(),
                    )
                )
            )
        }
    }

    override fun handleException(throwable: Throwable) {
        super.handleException(throwable)
        val errorMessage = if (throwable.message.isNullOrEmpty()) ErrorDialogState.default.message
        else DeferredString.StringValue(throwable.message!!)

        sendEffect(
            PlanStartingDateUiEffect.ShowError(
                dialogState = ErrorDialogState.default.copy(
                    message = errorMessage,
                    primaryButtonText = DeferredString.StringResource(R.string.ok),
                    onPrimaryBtnClicked = { sendEffect(PlanStartingDateUiEffect.NavigateBack) }
                )
            )
        )
    }

}