package com.fadali.flod_restaurant.feature.profile

import com.fadali.flod_restaurant.feature.profile.data.ProfileRepoImpl
import com.fadali.flod_restaurant.feature.profile.data.remote.ProfileRemoteDataSource
import com.fadali.flod_restaurant.feature.profile.data.remote.ProfileRemoteDataSourceImpl
import com.fadali.flod_restaurant.feature.profile.domain.ProfileRepo
import com.fadali.flod_restaurant.feature.profile.domain.usecase.CompleteUserDataUseCase
import com.fadali.flod_restaurant.feature.profile.domain.usecase.GetActivitiesLookupUseCase
import com.fadali.flod_restaurant.feature.profile.domain.usecase.GetGoalsLookupUseCase
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
    factory<CompleteUserDataUseCase> {
        CompleteUserDataUseCase(repo = get())
    }

    viewModel<CompleteUserDataViewModel> {
        CompleteUserDataViewModel(
            getActivitiesLookupUseCase = get(),
            getGoalsLookupUseCase = get(),
            completeUserDataUseCase = get(),
            dispatchers = get(),
            defaultExceptionHandler = get()
        )
    }
}
