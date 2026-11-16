package com.example.theloffle.features.perfil.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theloffle.features.perfil.domain.repository.UserRepo
import com.example.theloffle.features.recetas.domain.model.Recipe
import com.example.theloffle.features.recetas.domain.repository.RecipeRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileUiState(
    val username: String = "Usuario",
    val profileImageUrl: String? = null,
    val publishedRecipes: List<Recipe> = emptyList(),
    val isOwnProfile: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    // Propiedad calculada automáticamente a partir de la lista
    val publishedCount: Int
        get() = publishedRecipes.size
}

class ProfileViewModel(
    private val targetUsername: String,
    private val currentUsername: String?,
    private val userRepository: UserRepo,
    private val recipeRepository: RecipeRepo
) : ViewModel() {
    private val isOwn = !currentUsername.isNullOrBlank() &&
            targetUsername.removePrefix("@").equals(currentUsername.removePrefix("@"), ignoreCase = true)
    private val _uiState = MutableStateFlow(ProfileUiState(isOwnProfile = isOwn))
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadUserProfile(targetUsername)
    }

    fun loadUserProfile(username: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                // Buscamos al usuario por su username único
                val user = userRepository.getUserByUsername(username)

                // Cargamos las recetas usando el username encontrado o el pasado por parámetro
                val userPublishedRecipes = recipeRepository.getPublishedRecipesByUser(username)

                _uiState.update {
                    it.copy(
                        username = username,
                        profileImageUrl = user?.profilePhoto,
                        publishedRecipes = userPublishedRecipes,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error al cargar el perfil"
                    )
                }
            }
        }
    }
}