package com.fadali.flod_restaurant.app

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.CompositionLocalProvider
import com.fadali.flod_restaurant.core.permissions.PermissionRequester
import com.fadali.flod_restaurant.i18n.ContextUtils
import com.fadali.flod_restaurant.core.datastore.preferences.language.LanguageManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val languageManager: LanguageManager by inject()

    override fun attachBaseContext(newBase: Context) {
        // load saved language from new DataStore system
        val lang = runBlocking { languageManager.getCurrentLanguage(newBase) }
        val wrapped = ContextUtils.wrap(newBase, lang)
        super.attachBaseContext(wrapped)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PermissionRequester.requestPermissions(activity = this)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        enableEdgeToEdge()
        setContent { 
            CompositionLocalProvider(
                LocalActivityResultRegistryOwner provides this
            ) {
                App()
            }
        }
    }
}