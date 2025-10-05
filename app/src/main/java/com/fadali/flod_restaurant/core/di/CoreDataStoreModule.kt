package com.fadali.flod_restaurant.core.di

import com.fadali.flod_restaurant.core.datastore.preferences.AppPreferences
import com.fadali.flod_restaurant.core.datastore.preferences.language.LanguageManager
import com.fadali.flod_restaurant.core.datastore.preferences.language.LanguagePreferences
import com.fadali.flod_restaurant.core.domain.usecase.GetCurrentLanguageUseCase
import com.fadali.flod_restaurant.core.domain.usecase.OnboardingUseCase
import com.fadali.flod_restaurant.core.domain.usecase.ThemeUseCase
import com.fadali.flod_restaurant.core.domain.usecase.ToggleLanguageUseCase
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
        GetCurrentLanguageUseCase(languageManager = get()) 
    }
    factory<ThemeUseCase> { 
        ThemeUseCase(appPreferences = get()) 
    }
    factory<OnboardingUseCase> { 
        OnboardingUseCase(appPreferences = get()) 
    }
}
