package com.example.theloffle.features.inicio.presentation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theloffle.features.inicio.domain.repository.AuthRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LogInViewModell (
    private val repository: AuthRepo
): ViewModel(){

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    private val _isSuccess = MutableStateFlow<Boolean?>(null)
    val isSuccess = _isSuccess.asStateFlow()

    fun login(email: String, password: String){

        viewModelScope.launch {
            _isLoading.value = true

            val success = repository.login(
                email = email,
                password = password
            )

            _isSuccess.value = success
            _isLoading.value = false
        }
    }

    fun resetLogInState() {
        _isSuccess.value = null
    }
}