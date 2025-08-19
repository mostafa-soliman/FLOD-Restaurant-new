package com.fadali.flod_restaurant.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadali.flod_restaurant.data.model.auth.VerifyRequest
import com.fadali.flod_restaurant.data.model.auth.VerifyResponse
import com.fadali.flod_restaurant.domain.usecase.VerifyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyViewModel @Inject constructor(
    private val verifyUseCase: VerifyUseCase
) : ViewModel() {

    var state by mutableStateOf<VerifyState>(VerifyState.Idle)

    fun verify(mobile: String, code: String) {
        viewModelScope.launch {
            state = VerifyState.Loading
            try {
                val res = verifyUseCase(VerifyRequest(mobile, code))
                if (res.isSuccessful && res.body()?.success == true) {
                    state = VerifyState.Success(res.body()!!)
                } else {
                    state = VerifyState.Error(res.body()?.message ?: "Error")
                }
            } catch (e: Exception) {
                state = VerifyState.Error(e.message ?: "Error")
            }
        }
    }

    sealed class VerifyState {
        object Idle : VerifyState()
        object Loading : VerifyState()
        data class Success(val data: VerifyResponse) : VerifyState()
        data class Error(val message: String) : VerifyState()
    }
}
