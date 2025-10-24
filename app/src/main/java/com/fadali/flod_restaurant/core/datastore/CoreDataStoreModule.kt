package com.fadali.flod_restaurant.core.datastore

import com.fadali.flod_restaurant.core.datastore.preferences.AppPreferences
import com.fadali.flod_restaurant.core.datastore.preferences.language.LanguageManager
import com.fadali.flod_restaurant.core.datastore.preferences.language.LanguagePreferences
import com.fadali.flod_restaurant.core.datastore.preferences.usecase.GetCurrentLanguageUseCase
import com.fadali.flod_restaurant.core.datastore.preferences.usecase.OnboardingUseCase
import com.fadali.flod_restaurant.core.datastore.preferences.usecase.ThemeUseCase
import com.fadali.flod_restaurant.core.datastore.preferences.usecase.ToggleLanguageUseCase
import com.fadali.flod_restaurant.core.datastore.preferences.usecase.UserPrefsUseCase
import org.koin.dsl.module

val coreDataStoreModule = module {
    
    single<AppPreferences> {
        AppPreferences(dispatchers = get()) 
    }
    single<LanguagePreferences> { 
        LanguagePreferences(dispatchers = get()) 
    }
    single<LanguageManager> { 
        LanguageManager(languagePreferences = get())
    }
    
    factory<ToggleLanguageUseCase> {
        ToggleLanguageUseCase(languageManager = get()) 
    }
    factory<GetCurrentLanguageUseCase> { 
        GetCurrentLanguageUseCase(
            languageManager = get(),
            context = get()
        )
    }
    factory<ThemeUseCase> { 
        ThemeUseCase(appPreferences = get()) 
    }
    factory<OnboardingUseCase> { 
        OnboardingUseCase(
            appPreferences = get(),
            context = get(),
        )
    }
    factory<UserPrefsUseCase> {
        UserPrefsUseCase(
            appPreferences = get(),
            context = get(),
        )
    }
}
