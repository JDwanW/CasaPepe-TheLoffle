package com.example.theloffle.features.diario.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theloffle.features.diario.domain.model.FoodPages
import com.example.theloffle.features.diario.domain.repository.MealRegisterRepo
import com.example.theloffle.features.recetas.domain.model.Recipe
import kotlinx.coroutines.launch

data class MealRegisterUiState(
    val selectedMealType: FoodPages = FoodPages.NONE,
    val name: String = "",
    val author: String = "",
    val ingredients: List<String> = emptyList(),
    val availableIngredients: List<String> = emptyList(),
    val selectedTags: List<String> = emptyList(),
    val description: String = "",
    val published: Boolean? = false,
    val rating: Double = 0.0,
    val imageUri: String? = null,
    val selectedImageBytes: ByteArray? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)

class MealRegisterViewModel(
    private val repository: MealRegisterRepo
) : ViewModel() {

    var uiState by mutableStateOf(MealRegisterUiState())
        private set

    init {
        loadFormInitialData()
    }

    // Se llama desde MealRegisterScreenRoot con la comida que trajo la navegación
    fun initMealType(mealType: FoodPages) {
        uiState = uiState.copy(selectedMealType = mealType)
    }

    private fun loadFormInitialData() {
        viewModelScope.launch {
            val filters = repository.getFilterList()
            val ingredients = repository.getIngredientsList()
            uiState = uiState.copy(
                selectedTags = filters,
                availableIngredients = ingredients
            )
        }
    }

    fun onTitleChanged(newName: String) {
        uiState = uiState.copy(name = newName)
    }

    fun onDescriptionChanged(newDescription: String) {
        uiState = uiState.copy(description = newDescription)
    }

    fun onAddIngredient(ingredient: String) {
        if (ingredient.isNotBlank() && !uiState.ingredients.contains(ingredient)) {
            uiState = uiState.copy(ingredients = uiState.ingredients + ingredient)
        }
    }

    fun onRemoveIngredient(ingredient: String) {
        uiState = uiState.copy(ingredients = uiState.ingredients - ingredient)
    }

    // Guarda o publica asociando la comida (selectedMealType)
    fun onSaveRecipe(imageBytes: ByteArray?, isPublished: Boolean) {
        if (uiState.name.isBlank() || uiState.ingredients.isEmpty()) {
            uiState = uiState.copy(errorMessage = "El nombre y al menos un ingrediente son obligatorios.")
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)

            val newRecipe = Recipe(
                id = 0,
                name = uiState.name,
                author = uiState.author.ifBlank { "Usuario" },
                ingredients = uiState.ingredients,
                tags = uiState.selectedTags,
                description = uiState.description,
                published = isPublished,
                imageUrl = uiState.imageUri,
                rating = 0.0
            )

            val result = repository.saveMealRecipe(
                recipe = newRecipe,
                imageBytes = imageBytes,
                targetMeal = uiState.selectedMealType
            )

            result.onSuccess {
                uiState = uiState.copy(isLoading = false, isSuccess = true)
            }.onFailure { throwable ->
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = throwable.message ?: "Error al guardar la receta"
                )
            }
        }
    }

    fun resetSuccessState() {
        uiState = uiState.copy(isSuccess = false)
    }
}