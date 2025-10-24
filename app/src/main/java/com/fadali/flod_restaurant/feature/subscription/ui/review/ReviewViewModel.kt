package com.fadali.flod_restaurant.feature.subscription.ui.review

import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.app.navigation.routes.SubscriptionRoutes
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.datastore.preferences.language.LanguageManager
import com.fadali.flod_restaurant.core.datastore.preferences.usecase.GetCurrentLanguageUseCase
import com.fadali.flod_restaurant.core.ui.exception.UiException
import com.fadali.flod_restaurant.core.util.DeferredString
import com.fadali.flod_restaurant.feature.subscription.domain.model.payment.CreateGeideaPaymentRequest
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.CreateGeideaPaymentUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.CreateSubscriptionUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.GetAddonsUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.ValidatePromoCodeUseCase
import com.fadali.flod_restaurant.feature.subscription.ui.review.mapper.getSubscriptionCreationRequest
import com.fadali.flod_restaurant.feature.subscription.ui.review.model.AddonUiModel
import com.fadali.flod_restaurant.feature.subscription.ui.review.model.ConfirmationInfo
import com.fadali.flod_restaurant.feature.subscription.ui.review.model.PromoCodeValidationState
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import com.smyvo.core.ui.mvi.BaseViewModel
import com.smyvo.core.ui.network.DefaultExceptionHandler
import kotlin.reflect.KClass

class ReviewViewModel(
    private val getAddonsUseCase: GetAddonsUseCase,
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val validatePromoCodeUseCase: ValidatePromoCodeUseCase,
    private val createSubscriptionUseCase: CreateSubscriptionUseCase,
    private val createGeideaPaymentUseCase: CreateGeideaPaymentUseCase,
    dispatchers: AppCoroutineDispatcher,
    defaultExceptionHandler: DefaultExceptionHandler
) : BaseViewModel<SubscriptionReviewState, SubscriptionReviewEvent, SubscriptionReviewEffect>(
    appCoroutineDispatcher = dispatchers,
    defaultExceptionHandler = defaultExceptionHandler
) {

    override fun initialState(): SubscriptionReviewState = SubscriptionReviewState()

    override fun handleEvent(event: SubscriptionReviewEvent) {
        when (event) {
            is SubscriptionReviewEvent.LoadData -> loadData(event)
            is SubscriptionReviewEvent.OnAddOnQuantityChange -> onAddOnQuantityChange(
                event.index,
                event.quantity
            )
            is SubscriptionReviewEvent.OnPromoCodeChange -> onPromoCodeChange(event.promoCode)
            is SubscriptionReviewEvent.OnTermsToggled -> setState { copy(isTermsAccepted = event.accepted) }
            SubscriptionReviewEvent.OnBackClicked -> sendEffect(SubscriptionReviewEffect.NavigateBack)
            SubscriptionReviewEvent.OnApplyPromoCode -> applyPromoCode()
            SubscriptionReviewEvent.OnNavigateToTermsAndConditions -> onNavigateToTermsAndConditions()
            SubscriptionReviewEvent.OnConfirmClicked -> onConfirmBtnClicked()
        }
    }

    private fun loadData(event: SubscriptionReviewEvent.LoadData) {
        val deliveryTimeSlot = event.args.deliveryTimeSlot?.let {
            "${it.startTime}-${it.endTime}"
        } ?: ""

        setState {
            copy(
                args = event.args,
                confirmationInfo = ConfirmationInfo(
                    mealsPerDay = event.args.packageDetails.mealsPerDay,
                    numberOfDays = event.args.packageDetails.durationDays,
                    startingDate = event.args.displayableStartingDate,
                    deliveryTimeSlot = deliveryTimeSlot,
                    deliveryAddress = event.args.deliveryAddress?.displayName ?: "",
                    streetName = event.args.deliveryAddress?.streetName ?: "",
                    buildingNumber = event.args.deliveryAddress?.buildingName ?: "",
                    flatNumber = event.args.deliveryAddress?.flatNumber?.toString() ?: "",
                    phoneNumber = event.args.phoneNumber,
                    pickUpBranchAddress = event.args.pickupBranch?.displayName ?: "",
                )
            )
        }
        loadAddons()
    }

    private fun loadAddons() {
        if (currentState.addOns != null) return

        setState { copy(isLoading = true) }
        launchSafe {
            val addons = getAddonsUseCase().map { addonDomainModel ->
                val name =
                    if (getCurrentLanguageUseCase.getCurrentLanguage() == LanguageManager.ARABIC) {
                        addonDomainModel.titleAr
                    } else {
                        addonDomainModel.titleEn
                    }
                AddonUiModel(
                    id = addonDomainModel.id,
                    name = name,
                    price = addonDomainModel.price,
                    quantity = 0,
                    imageUrl = addonDomainModel.imageUrl,
                    nutritionInfo = addonDomainModel.nutritionInfo,
                )
            }
            setState {
                copy(
                    isLoading = false,
                    addOns = addons
                )
            }
        }
    }

    private fun onPromoCodeChange(promoCode: String) {
        setState {
            copy(
                promoCodeText = promoCode,
                promoCodeValidationState = PromoCodeValidationState.NONE
            )
        }
    }

    private fun onAddOnQuantityChange(index: Int, quantity: Int) {
        setState {
            val updatedAddOns = addOns?.mapIndexed { i, addOn ->
                if (i == index) addOn.copy(quantity = quantity) else addOn
            }
            copy(addOns = updatedAddOns)
        }
    }

    private fun applyPromoCode() {
        val promoCode = currentState.promoCodeText.trim()
        if (promoCode.isEmpty()) {
            setState {
                copy(promoCodeValidationState = PromoCodeValidationState.EMPTY)
            }
            return
        }

        launchSafe {
            setState { copy(isLoading = true) }
            val response = validatePromoCodeUseCase(promoCode)
            setState {
                copy(
                    isLoading = false,
                    appliedPromoCode = response,
                    promoCodeValidationState = if (response != null) PromoCodeValidationState.VALID
                    else PromoCodeValidationState.INVALID
                )
            }
        }
    }

    private fun onNavigateToTermsAndConditions() {
        createSubscription(nextScreen = SubscriptionRoutes.TermsAndConditions::class)
    }

    private fun onConfirmBtnClicked() {
        if (!currentState.isTermsAccepted) {
            onNavigateToTermsAndConditions()
        } else {
            createSubscription(nextScreen = SubscriptionRoutes.Payment::class)
        }
    }

    private fun <T : SubscriptionRoutes> createSubscription(nextScreen: KClass<T>) {
        if (currentState.args == null) {
            sendEffect(
                SubscriptionReviewEffect.ShowError(
                    ErrorDialogState.default.copy(
                        message = DeferredString.StringResource(R.string.subscription_data_missing),
                        primaryButtonText = DeferredString.StringResource(R.string.ok),
                        onPrimaryBtnClicked = { sendEffect(SubscriptionReviewEffect.NavigateBack) },
                        isDismissible = false
                    )
                )
            )
            return
        }

        setState { copy(isLoading = true) }
        launchSafe {
            val createSubscriptionRequest = getSubscriptionCreationRequest(
                packageDetails = currentState.args?.packageDetails!!,
                addons = currentState.addOns ?: emptyList(),
                pickupBranch = currentState.args?.pickupBranch,
                deliveryAddress = currentState.args?.deliveryAddress,
                startingDateUtc = currentState.args?.startingDateUtc!!,
                packageType = currentState.args?.packageType!!,
                promoCodeId = currentState.appliedPromoCode?.id,
                selectedDays = currentState.args?.selectedDays!!,
                deliveryTimeSlot = currentState.args?.deliveryTimeSlot,
            )
            val subscriptionId = if (currentState.createdSubscriptionId == null) {
                createSubscriptionUseCase(createSubscriptionRequest)
            } else {
                currentState.createdSubscriptionId!!
            }

            val paymentUrl = createGeideaPaymentUseCase(
                request = CreateGeideaPaymentRequest(subscriptionId = subscriptionId)
            ).paymentUrl

            setState {
                copy(
                    isLoading = false,
                    createdSubscriptionId = subscriptionId,
                    paymentUrl = paymentUrl
                )
            }
            val nextScreenRoute = when (nextScreen) {
                SubscriptionRoutes.TermsAndConditions::class -> {
                    SubscriptionRoutes.TermsAndConditions(paymentUrl = paymentUrl)
                }
                SubscriptionRoutes.Payment::class -> {
                    SubscriptionRoutes.Payment(paymentUrl = paymentUrl)
                }
                else -> null
            }
            nextScreenRoute?.let {
                sendEffect(SubscriptionReviewEffect.NavigateToNextScreen(route = it))
            }
        }
    }

    override fun handleException(throwable: Throwable) {
        super.handleException(throwable)
        setState { copy(isLoading = false) }

        when (throwable) {
            is UiException -> {
                sendEffect(SubscriptionReviewEffect.ShowError(throwable.dialogState))
            }
            else -> {
                val errorMessage = if (throwable.message.isNullOrEmpty()) {
                    ErrorDialogState.default.message
                } else {
                    DeferredString.StringValue(throwable.message!!)
                }
                sendEffect(
                    SubscriptionReviewEffect.ShowError(
                        ErrorDialogState.default.copy(
                            message = errorMessage,
                            primaryButtonText = DeferredString.StringResource(R.string.ok)
                        )
                    )
                )
            }
        }
    }
}
