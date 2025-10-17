package com.fadali.flod_restaurant.app

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fadali.flod_restaurant.app.navigation.NavigationRoot
import com.fadali.flod_restaurant.app.navigation.graphs.Graph
import com.fadali.flod_restaurant.app.navigation.navigateAndClearBackStack
import com.fadali.flod_restaurant.core.component.bottomnavbar.BottomNavBar
import com.fadali.flod_restaurant.core.component.bottomnavbar.BottomNavConfig
import com.fadali.flod_restaurant.core.component.dialog.AppLoadingDialog
import com.fadali.flod_restaurant.core.component.dialog.errordialog.AppErrorDialog
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.datastore.preferences.usecase.ToggleLanguageUseCase
import com.fadali.flod_restaurant.core.util.resolve
import com.fadali.flod_restaurant.i18n.LocalizationProvider
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.smyvo.core.ui.mvi.HandleEffects
import com.smyvo.core.ui.mvi.HandleState
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun App() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = koinInject()
    val showBottomBar by mainViewModel.showBottomBar.collectAsState()
    var showErrorDialog by rememberSaveable { mutableStateOf(false) }
    var errorDialogState by remember { mutableStateOf(ErrorDialogState.default) }

    val context = LocalContext.current
    val toggleLanguageUseCase: ToggleLanguageUseCase = koinInject()
    val coroutineScope = rememberCoroutineScope()

    FLODRestaurantTheme {
        LocalizationProvider {
            val systemUi = rememberSystemUiController()
            SideEffect {
                systemUi.setStatusBarColor(color = Color.Transparent, darkIcons = true)
                systemUi.setNavigationBarColor(color = Color.Transparent, darkIcons = true)
            }

            HandleState(mainViewModel.state) { state ->

                AppContent(
                    state = state,
                    sendEvent = mainViewModel::sendEvent,
                    showBottomBar = showBottomBar,
                    navController = navController
                )
            }
            HandleEffects(mainViewModel.effect) { effect ->
                when (effect) {
                    MainEffect.LanguageToggled -> {
                        coroutineScope.launch {
                            toggleLanguageUseCase(context = context)
                            mainViewModel.stopLoading()
                        }
                    }
                    is MainEffect.ShowError -> {
                        showErrorDialog = true
                        effect.dialogState?.let { errorDialogState = it }
                    }
                    MainEffect.NavigateToLogin -> navController.navigateAndClearBackStack(Graph.AuthGraph)
                }
            }
            AppErrorDialog(
                isVisible = showErrorDialog,
                title = errorDialogState.title.resolve(),
                message = errorDialogState.message.resolve(),
                primaryButtonText = errorDialogState.primaryButtonText.resolve(),
                secondaryButtonText = errorDialogState.secondaryButtonText?.resolve(),
                isDismissible = errorDialogState.isDismissible,
                onPrimaryBtnClicked = errorDialogState.onPrimaryBtnClicked,
                onDismiss = {
                    showErrorDialog = false
                    errorDialogState.onDismiss()
                }
            )
        }
    }
}

@Composable
private fun AppContent(
    state: MainState,
    sendEvent: (MainEvent) -> Unit,
    showBottomBar: Boolean,
    navController: NavHostController
) {
    AppLoadingDialog(state.isLoading)

    Scaffold(
        contentWindowInsets = WindowInsets(),
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(
                    navController = navController,
                    items = BottomNavConfig.items,
                )
            }
        }
    ) { paddingValues ->
        NavigationRoot(
            modifier = Modifier.padding(paddingValues),
            navController = navController
        )
    }
}
