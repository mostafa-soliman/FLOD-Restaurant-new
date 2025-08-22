package com.fadali.flod_restaurant.i18n

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
val LocalAppLocale = compositionLocalOf { "en" }

val LocalAppLocaleState = compositionLocalOf<MutableState<String>> {
    error("LocalAppLocaleState not provided")
}

@Composable
fun LocalizationProvider(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val initial = remember { runBlocking { LanguageStore.load(context) } ?: "en" }
    val langState = remember { mutableStateOf(initial) }

    val dir = if (langState.value.equals("ar", true)) LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(
        LocalAppLocale provides langState.value,
        LocalAppLocaleState provides langState,
        LocalLayoutDirection provides dir
    ) {
        content()
    }
}

@Composable
fun LText(@StringRes resId: Int): String {
    val base = LocalContext.current
    val lang = LocalAppLocale.current
    val wrapped = remember(lang) { ContextUtils.wrap(base, lang) }
    return wrapped.resources.getString(resId)
}
@Composable
fun LText(@StringRes resId: Int, vararg args: Any): String {
    val base = LocalContext.current
    val lang = LocalAppLocale.current
    val wrapped = remember(lang) { ContextUtils.wrap(base, lang) }
    return wrapped.resources.getString(resId, *args)
}

@Composable
fun LPlural(@androidx.annotation.PluralsRes resId: Int, quantity: Int, vararg args: Any): String {
    val base = LocalContext.current
    val lang = LocalAppLocale.current
    val wrapped = remember(lang) { ContextUtils.wrap(base, lang) }
    return wrapped.resources.getQuantityString(resId, quantity, *args)
}
@Composable
fun rememberLanguageController(): (String) -> Unit {
    val context = LocalContext.current
    val langState = LocalAppLocaleState.current
    val scope = rememberCoroutineScope()

    return { newLang ->
        if (langState.value != newLang) {
            langState.value = newLang
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(newLang)
            )
            scope.launch {
                LanguageStore.save(context, newLang)
            }
        }
    }
}
