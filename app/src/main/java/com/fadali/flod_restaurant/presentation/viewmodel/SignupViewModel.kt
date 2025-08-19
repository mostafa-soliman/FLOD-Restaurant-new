package com.fadali.flod_restaurant.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.fadali.flod_restaurant.domain.usecase.SignUpUseCase
import com.fadali.flod_restaurant.presentation.ui.screens.signup.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.fadali.flod_restaurant.data.model.auth.SignUpRequest
import com.fadali.flod_restaurant.data.model.auth.SignUpResponse
import com.fadali.flod_restaurant.domain.repository.AuthRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val repo: AuthRepository

) : ViewModel() {

    var state by mutableStateOf<SignUpState>(SignUpState.Idle)
        private set

    fun signUp(name: String, email: String, mobile: String, birthday: String, gender: Gender) {
        viewModelScope.launch {
            state = SignUpState.Loading
            try {
                val parsed = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("d / M / yyyy"))
                val birthFormatted = parsed.format(DateTimeFormatter.ISO_LOCAL_DATE) // yyyy-MM-dd
                val phone = if (mobile.startsWith("+966")) {
                    "0" + mobile.removePrefix("+966")
                } else mobile
                val req = SignUpRequest(
                    name = name,
                    email = email,
                    mobileNumber = phone,
                    birthDate = birthFormatted,
                    gender = if (gender == Gender.Male) 1 else 2
                )
               val res = signUpUseCase(req)
                Log.e("SignupVM", "request = $req")

               // val res = repo.signUp(req)

                Log.e("SignupVM", "response = ${res.code()} , ${res.body()?.success} , ${res.body()?.message}")

                if (res.isSuccessful && res.body()?.success == true) {
                    state = SignUpState.Success(res.body()!!)
                } else {
                    state = SignUpState.Error(res.body()?.message ?: "Failed")
                }
            } catch (e: Exception) {
                state = SignUpState.Error(e.message ?: "Error")
            }
        }
    }

    fun resetState() {
        state = SignUpState.Idle
    }

    sealed class SignUpState {
        object Idle : SignUpState()
        object Loading : SignUpState()
        data class Success(val data: SignUpResponse) : SignUpState()
        data class Error(val message: String) : SignUpState()
    }
}
