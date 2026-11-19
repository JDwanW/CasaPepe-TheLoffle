package com.example.theloffle.features.recetas.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theloffle.core.UserSession
import com.example.theloffle.features.recetas.domain.repository.RecipeRepo
import kotlinx.coroutines.launch
import com.example.theloffle.features.recetas.domain.model.Recipe

data class PublicationUiState(
    val recipe: Recipe? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSaved: Boolean = false,
    val isOwnRecipe: Boolean = false // Para ver si la receta es del usuario o no
)

class PublicationViewModel(
    private val recipeRepository: RecipeRepo,
    private val session: UserSession
) : ViewModel() {

    var uiState by mutableStateOf(PublicationUiState())
        private set
    
    fun loadRecipeById(recipeId: Int) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)
            try {
                val recipe = recipeRepository.getRecipeById(recipeId)
                // Miramos si el usuario ya la tiene guardada
                val saved = recipeRepository.isRecipeSaved(recipeId)
                // Comprobamos si la receta es del usuario de la sesion
                val own = recipe?.author == session.current.value
                uiState = uiState.copy(
                    recipe = recipe,
                    isLoading = false,
                    isSaved = saved,
                    isOwnRecipe = own
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

    // funcion nueva
    fun onToggleSaved() {
        // Si no hay receta salimos
        val id = uiState.recipe?.id ?: return
        viewModelScope.launch {
            // El repositorio devuelve el estado nuevo
            val nowSaved = recipeRepository.toggleSavedRecipe(id)
            uiState = uiState.copy(isSaved = nowSaved)
        }
    }
}