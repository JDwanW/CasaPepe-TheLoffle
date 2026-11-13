package com.example.theloffle.features.inicio.presentation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theloffle.features.inicio.domain.repository.AuthRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: AuthRepo
): ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    private val _isSuccess = MutableStateFlow<Boolean?>(null)
    val isSuccess = _isSuccess.asStateFlow()

    fun register(email: String, password: String, name: String, surname: String, username: String) {


        viewModelScope.launch {
            _isLoading.value = true

            val success = repository.register(
                email = email,
                password = password,
                name = name,
                surname = surname,
                username = username
            )

            _isSuccess.value = success
            _isLoading.value = false
        }
    }

    fun resetRegistrationState() {
        _isSuccess.value = null
    }
}