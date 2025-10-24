package com.fadali.flod_restaurant.feature.profile

import com.fadali.flod_restaurant.feature.profile.data.ProfileRepoImpl
import com.fadali.flod_restaurant.feature.profile.data.datasource.remote.ProfileRemoteDataSource
import com.fadali.flod_restaurant.feature.profile.data.datasource.remote.ProfileRemoteDataSourceImpl
import com.fadali.flod_restaurant.feature.profile.domain.ProfileRepo
import com.fadali.flod_restaurant.feature.profile.domain.usecase.CompleteUserDataUseCase
import com.fadali.flod_restaurant.feature.profile.domain.usecase.GetActivitiesLookupUseCase
import com.fadali.flod_restaurant.feature.profile.domain.usecase.GetAllergiesLookupUseCase
import com.fadali.flod_restaurant.feature.profile.domain.usecase.GetGoalsLookupUseCase
import com.fadali.flod_restaurant.feature.profile.domain.usecase.GetUserProfileUseCase
import com.fadali.flod_restaurant.feature.profile.domain.usecase.UpdateUserAllergiesUseCase
import com.fadali.flod_restaurant.feature.profile.ui.allergies.AllergiesViewModel
import com.fadali.flod_restaurant.feature.profile.ui.completeuserdata.CompleteUserDataViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {
    factory<ProfileRemoteDataSource> {
        ProfileRemoteDataSourceImpl(networkHandler = get())
    }
    factory<ProfileRepo> {
        ProfileRepoImpl(remoteDataSource = get())
    }
    factory<GetActivitiesLookupUseCase> {
        GetActivitiesLookupUseCase(repo = get())
    }
    factory<GetGoalsLookupUseCase> {
        GetGoalsLookupUseCase(repo = get())
    }
    factory<GetAllergiesLookupUseCase> {
        GetAllergiesLookupUseCase(repo = get())
    }
    factory<CompleteUserDataUseCase> {
        CompleteUserDataUseCase(repo = get())
    }
    factory<UpdateUserAllergiesUseCase> {
        UpdateUserAllergiesUseCase(repo = get())
    }
    factory { GetUserProfileUseCase(get()) }

    viewModel<CompleteUserDataViewModel> {
        CompleteUserDataViewModel(
            getActivitiesLookupUseCase = get(),
            getGoalsLookupUseCase = get(),
            completeUserDataUseCase = get(),
            dispatchers = get(),
            defaultExceptionHandler = get()
        )
    }
    viewModel<AllergiesViewModel> {
        AllergiesViewModel(
            getAllergiesLookupUseCase = get(),
            updateUserAllergiesUseCase = get(),
            dispatchers = get(),
            defaultExceptionHandler = get()
        )
    }
}
