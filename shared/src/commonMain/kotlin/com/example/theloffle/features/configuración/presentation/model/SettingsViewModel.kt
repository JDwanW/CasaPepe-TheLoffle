package com.example.theloffle.features.configuración.presentation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theloffle.features.inicio.domain.repository.AuthRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.update

data class PersonalDataUiState(
    val name: String = "",
    val surname: String = "",
    val username: String = "",
    val email: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
class SettingsViewModel(
    private val authRepo: AuthRepo
) : ViewModel() {

    private val _personalDataUiState = MutableStateFlow(PersonalDataUiState())

    val personalDataUiState: StateFlow<PersonalDataUiState> =
        _personalDataUiState.asStateFlow()

    //pasa la función al repository
    fun logout() {
        viewModelScope.launch {
            authRepo.logout()
        }
    }

    fun loadPersonalData() {
        viewModelScope.launch {

            _personalDataUiState.update {
                it.copy(isLoading = true, errorMessage = null)
            }

            try {
                val user = authRepo.getCurrentUser()

                if (user != null) {
                    _personalDataUiState.update {
                        it.copy(
                            name = user.name,
                            surname = user.surname,
                            username = user.username,
                            email = user.email,
                            isLoading = false
                        )
                    }
                } else {
                    _personalDataUiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Usuario no encontrado"
                        )
                    }
                }

            } catch (e: Exception) {
                _personalDataUiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error al cargar los datos"
                    )
                }
            }
        }
    }
}