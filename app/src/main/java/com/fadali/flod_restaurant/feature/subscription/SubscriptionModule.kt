package com.fadali.flod_restaurant.feature.subscription

import com.fadali.flod_restaurant.feature.subscription.data.SubscriptionRepoImpl
import com.fadali.flod_restaurant.feature.subscription.data.datasource.remote.SubscriptionRemoteDataSource
import com.fadali.flod_restaurant.feature.subscription.data.datasource.remote.SubscriptionRemoteDataSourceImpl
import com.fadali.flod_restaurant.feature.subscription.domain.SubscriptionRepo
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.CreateCustomerAddressUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.DeleteCustomerAddressUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.GetBranchesUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.GetCustomerAddressesUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.GetPackagesUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.GetProductCategoriesUseCase
import com.fadali.flod_restaurant.feature.subscription.domain.usecase.UpdateCustomerAddressUseCase
import com.fadali.flod_restaurant.feature.subscription.selectplan.ui.SelectPlanViewModel
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
}
