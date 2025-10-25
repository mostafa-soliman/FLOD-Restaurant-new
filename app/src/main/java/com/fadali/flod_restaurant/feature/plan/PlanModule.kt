package com.fadali.flod_restaurant.feature.plan

import com.fadali.flod_restaurant.feature.plan.data.PlanRepoImpl
import com.fadali.flod_restaurant.feature.plan.data.datasource.remote.PlanRemoteDataSource
import com.fadali.flod_restaurant.feature.plan.data.datasource.remote.PlanRemoteDataSourceImpl
import com.fadali.flod_restaurant.feature.plan.domain.PlanRepo
import com.fadali.flod_restaurant.feature.plan.domain.usecase.GetPackageDetailsUseCase
import com.fadali.flod_restaurant.feature.plan.domain.usecase.GetSubscriptionListUseCase
import com.fadali.flod_restaurant.feature.plan.ui.myplans.MyPlansViewModel
import com.fadali.flod_restaurant.feature.plan.ui.packagedetails.PackageDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val planModule = module {
    factory<PlanRemoteDataSource> { PlanRemoteDataSourceImpl(get()) }
    factory<PlanRepo> { PlanRepoImpl(get()) }

    factory { GetPackageDetailsUseCase(get()) }
    factory { GetSubscriptionListUseCase(get()) }

    viewModel {
        PackageDetailsViewModel(
            getPackageDetailsUseCase = get(),
            dispatchers = get(),
            defaultExceptionHandler = get()
        )
    }

    viewModel {
        MyPlansViewModel(
            getSubscriptionListUseCase = get(),
            dispatchers = get(),
            defaultExceptionHandler = get()
        )
    }
}
