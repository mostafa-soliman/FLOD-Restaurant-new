package com.fadali.flod_restaurant.app

import androidx.lifecycle.ViewModel
import com.fadali.flod_restaurant.app.navigation.routes.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val _showBottomBar = MutableStateFlow(false)
    val showBottomBar: StateFlow<Boolean> = _showBottomBar.asStateFlow()
    private val _currentRoute = MutableStateFlow<Route?>(null)
    val currentRoute: StateFlow<Route?> = _currentRoute.asStateFlow()

    fun updateCurrentRoute(route: Route?) {
        _currentRoute.value = route
        _showBottomBar.value = route?.showBottomNavBar == true
    }

}
