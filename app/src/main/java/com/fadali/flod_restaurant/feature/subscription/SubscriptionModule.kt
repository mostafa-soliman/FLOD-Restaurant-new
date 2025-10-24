package com.fadali.flod_restaurant.feature.subscription

import com.fadali.flod_restaurant.feature.subscription.data.SubscriptionRepoImpl
import com.fadali.flod_restaurant.feature.subscription.data.datasource.remote.SubscriptionRemoteDataSource
import com.fadali.flod_restaurant.feature.subscription.data.datasource.remote.SubscriptionRemoteDataSourceImpl
import com.fadali.flod_restaurant.feature.subscription.domain.SubscriptionRepo
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.CreateCustomerAddressUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.CreateGeideaPaymentUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.CreateSubscriptionUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.DeleteCustomerAddressUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.GetAddonsUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.GetBranchesUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.GetCustomerAddressesUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.GetDeliveryTimeSlotsUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.GetPackageByIdUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.GetPackagesUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.GetProductCategoriesUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.GetTermsAndConditionsUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.UpdateCustomerAddressUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.ValidatePromoCodeUseCase
import com.fadali.flod_restaurant.feature.subscription.ui.branchaddress.BranchAddressViewModel
import com.fadali.flod_restaurant.feature.subscription.ui.payment.PaymentViewModel
import com.fadali.flod_restaurant.feature.subscription.ui.planmenu.PlanMenuViewModel
import com.fadali.flod_restaurant.feature.subscription.ui.planstartingdate.PlanStartingDateViewModel
import com.fadali.flod_restaurant.feature.subscription.ui.review.ReviewViewModel
import com.fadali.flod_restaurant.feature.subscription.ui.selectplan.ui.SelectPlanViewModel
import com.fadali.flod_restaurant.feature.subscription.ui.terms.SubscriptionTermsAndConditionsViewModel
import com.fadali.flod_restaurant.feature.subscription.ui.viewpackage.ViewPackageViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val subscriptionModule = module {
    factory<SubscriptionRemoteDataSource> { SubscriptionRemoteDataSourceImpl(get()) }
    factory<SubscriptionRepo> { SubscriptionRepoImpl(get()) }

    factory { GetProductCategoriesUseCase(get()) }
    factory { GetBranchesUseCase(get()) }
    factory { GetCustomerAddressesUseCase(get()) }
    factory { CreateCustomerAddressUseCase(get()) }
    factory { UpdateCustomerAddressUseCase(get()) }
    factory { DeleteCustomerAddressUseCase(get()) }
    factory { GetPackagesUseCase(get()) }
    factory { GetDeliveryTimeSlotsUseCase(get()) }
    factory { GetAddonsUseCase(get()) }
    factory { ValidatePromoCodeUseCase(get()) }
    factory { CreateSubscriptionUseCase(get()) }
    factory { CreateGeideaPaymentUseCase(get()) }
    factory { GetPackageByIdUseCase(get()) }
    factory { GetTermsAndConditionsUseCase(get()) }

    viewModel<SelectPlanViewModel> {
        SelectPlanViewModel(
            getCustomerAddressesUseCase = get(),
            getBranchesUseCase = get(),
            getProductCategoriesUseCase = get(),
            getPackagesUseCase = get(),
            dispatchers = get(),
            defaultExceptionHandler = get()
        )
    }
    viewModel<BranchAddressViewModel> {
        BranchAddressViewModel(
            createCustomerAddressUseCase = get(),
            updateCustomerAddressUseCase = get(),
            getCustomerAddressesUseCase = get(),
            dispatchers = get(),
            defaultExceptionHandler = get()
        )
    }
    viewModel<PaymentViewModel> {
        PaymentViewModel(
            dispatchers = get(),
            defaultExceptionHandler = get()
        )
    }
    viewModel<ReviewViewModel> {
        ReviewViewModel(
            getAddonsUseCase = get(),
            getCurrentLanguageUseCase = get(),
            validatePromoCodeUseCase = get(),
            createSubscriptionUseCase = get(),
            createGeideaPaymentUseCase = get(),
            dispatchers = get(),
            defaultExceptionHandler = get()
        )
    }
    viewModel {
        PlanStartingDateViewModel(
            getDeliveryTimeSLotsUseCase = get(),
            getUserProfileUseCase = get(),
            dispatchers = get(),
            defaultExceptionHandler = get()
        )
    }
    viewModel {
        ViewPackageViewModel(
            getPackageByIdUseCase = get(),
            dispatchers = get(),
            defaultExceptionHandler = get()
        )
    }
    viewModel {
        PlanMenuViewModel(
            dispatchers = get(),
            defaultExceptionHandler = get()
        )
    }
    viewModel {
        SubscriptionTermsAndConditionsViewModel(
            getTermsAndConditionsUseCase = get(),
            dispatchers = get(),
            defaultExceptionHandler = get()
        )
    }
}

