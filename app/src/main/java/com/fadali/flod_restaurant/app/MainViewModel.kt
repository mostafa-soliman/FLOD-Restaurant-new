package com.fadali.flod_restaurant.app

import com.fadali.flod_restaurant.app.navigation.routes.Route
import com.smyvo.core.ui.dispatchers.AppCoroutineDispatcher
import com.smyvo.core.ui.mvi.BaseViewModel
import com.smyvo.core.ui.network.DefaultExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(
    dispatchers: AppCoroutineDispatcher,
    defaultExceptionHandler: DefaultExceptionHandler
) : BaseViewModel<MainState, MainEvent, MainEffect>(
    appCoroutineDispatcher = dispatchers,
    defaultExceptionHandler = defaultExceptionHandler
) {
    private val _showBottomBar = MutableStateFlow(false)
    val showBottomBar: StateFlow<Boolean> = _showBottomBar.asStateFlow()
    private val _currentRoute = MutableStateFlow<Route?>(null)
    val currentRoute: StateFlow<Route?> = _currentRoute.asStateFlow()

    override fun initialState(): MainState = MainState()

    override fun handleEvent(event: MainEvent) {
        when (event) {
            MainEvent.CheckLoginStatus -> checkLoginStatus()
            MainEvent.OnToggleLanguage -> onToggleLanguage()
            MainEvent.OnLogout -> sendEffect(MainEffect.NavigateToLogin)
        }
    }

    fun updateCurrentRoute(route: Route?) {
        _currentRoute.value = route
        _showBottomBar.value = route?.showBottomNavBar == true
    }

    /**
     * Public method to stop loading after language toggle completes
     */
    fun stopLoading() {
        setState { copy(isLoading = false) }
    }

    private fun onToggleLanguage() {
        setState { copy(isLoading = true) }
        sendEffect(MainEffect.LanguageToggled)
    }

    private fun checkLoginStatus() {
        // TODO: Implement login status check logic here
        setState { copy(isLoggedIn = false) }
    }

}
