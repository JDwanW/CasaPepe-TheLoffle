package com.example.theloffle.features.recetas.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theloffle.features.recetas.domain.repository.RecipeRepo
import kotlinx.coroutines.launch
import com.example.theloffle.features.recetas.domain.model.Recipe

data class PublicationUiState(
    val recipe: Recipe? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class PublicationViewModel(
    private val recipeRepository: RecipeRepo
) : ViewModel() {

    var uiState by mutableStateOf(PublicationUiState())
        private set
    
    fun loadRecipeById(recipeId: Int) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)
            try {
                val recipe = recipeRepository.getRecipeById(recipeId)
                uiState = uiState.copy(
                    recipe = recipe,
                    isLoading = false
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Error al cargar la publicación"
                )
            }
        }
    }

    fun onClosePublication() {
        uiState = PublicationUiState()
    }
}