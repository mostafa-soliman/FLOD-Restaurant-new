package com.fadali.flod_restaurant.i18n

import androidx.annotation.StringRes
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import com.fadali.flod_restaurant.core.datastore.preferences.language.LanguageManager
import org.koin.compose.koinInject

val LocalAppLocale = compositionLocalOf { "en" }

val LocalAppLocaleState = compositionLocalOf<MutableState<String>> {
    error("LocalAppLocaleState not provided")
}

@Composable
fun LocalizationProvider(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val languageManager: LanguageManager = koinInject()
    
    // Use the new DataStore system to get the current language
    val currentLanguage by languageManager.getCurrentLanguageFlow(context).collectAsState(initial = "en")
    
    // Force recomposition and locale update when language changes
    LaunchedEffect(currentLanguage) {
        // Apply the language at the system level to ensure consistency
        languageManager.applySavedLanguage(context)
    }

    val dir = if (currentLanguage.equals("ar", true)) LayoutDirection.Rtl else LayoutDirection.Ltr

    // Create a locale-aware context for this composition
    val localeContext = remember(currentLanguage) {
        ContextUtils.wrap(context, currentLanguage)
    }

    CompositionLocalProvider(
        LocalAppLocale provides currentLanguage,
        LocalAppLocaleState provides remember { mutableStateOf(currentLanguage) },
        LocalLayoutDirection provides dir,
        // Provide the locale-wrapped context so LText can use it
        LocalContext provides localeContext
    ) {
        content()
    }
}

@Composable
fun LText(@StringRes resId: Int): String {
    val context = LocalContext.current
    return context.resources.getString(resId)
}
@Composable
fun LText(@StringRes resId: Int, vararg args: Any): String {
    val context = LocalContext.current
    return context.resources.getString(resId, *args)
}

@Composable
fun LPlural(@androidx.annotation.PluralsRes resId: Int, quantity: Int, vararg args: Any): String {
    val context = LocalContext.current
    return context.resources.getQuantityString(resId, quantity, *args)
}
@Composable
fun rememberLanguageController(): (String) -> Unit {
    val context = LocalContext.current
    val langState = LocalAppLocaleState.current
    val scope = rememberCoroutineScope()
    val languageManager: LanguageManager = koinInject()

    return { newLang ->
        if (langState.value != newLang) {
            scope.launch {
                // Use the new LanguageManager instead of the old LanguageStore
                languageManager.setLanguage(
                    context = context,
                    languageCode = newLang,
                    activity = null,
                    recreateActivity = false // We don't need to recreate for Compose
                )
                // The langState will be updated automatically through the Flow in LocalizationProvider
            }
        }
    }
}
