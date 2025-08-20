package com.fadali.flod_restaurant.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadali.flod_restaurant.data.model.auth.LoginResponse
import com.fadali.flod_restaurant.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    var loginState by mutableStateOf<LoginState>(LoginState.Idle)
        private set

    fun login(number: String) = viewModelScope.launch {
        loginState = LoginState.Loading
        try {
            val res = loginUseCase(number)
            if (res.isSuccessful && res.body()?.success == true) {
                loginState = LoginState.Success(res.body()!!)
            } else {
                loginState = LoginState.Error(res.body()?.message ?: "Error")
            }
        } catch (e: Exception) {
            loginState = LoginState.Error(e.message ?: "Error")
        }
    }

    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        data class Success(val data: LoginResponse?) : LoginState()
        data class Error(val message: String) : LoginState()
    }
}

