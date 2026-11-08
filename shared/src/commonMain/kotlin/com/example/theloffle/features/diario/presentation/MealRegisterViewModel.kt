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
    val selectedTags: List<String> = emptyList(),
    val description: String = "",
    val published: Boolean? = false,
    val rating: Double = 0.0,
    val imageUri: String? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)

class MealRegisterViewModel(
    private val mealRegisterRepo: MealRegisterRepo
) : ViewModel() {

    var uiState by mutableStateOf(MealRegisterUiState())
        private set

    init {
        loadFormInitialData()
    }

    private fun loadFormInitialData() {
        viewModelScope.launch {
            val filters = mealRegisterRepo.getFilterList()
            uiState = uiState.copy(selectedTags = filters)
        }
    }


    fun onTitleChanged(newName: String) {
        uiState = uiState.copy(name = newName)
    }

    fun onAddIngredient(ingredient: String) {
        if (ingredient.isNotBlank() && !uiState.ingredients.contains(ingredient)) {
            uiState = uiState.copy(ingredients = uiState.ingredients + ingredient)
        }
    }

    fun onRemoveIngredient(ingredient: String) {
        uiState = uiState.copy(ingredients = uiState.ingredients - ingredient)
    }

    fun onPublishRecipe(imageBytes: ByteArray?, isPublished: Boolean = true){

    }
    /*fun onPublishRecipe(imageBytes: ByteArray?, isPublished: Boolean = true) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)

            val newRecipe = Recipe(
                id = 0, // El repositorio se encarga de asignar el ID adecuado
                name = uiState.name,
                author = uiState.author,
                ingredients = uiState.ingredients,
                tags = uiState.selectedTags,
                description = "Hola",
                published = isPublished,
                imageUrl = null, // MealRegisterRepoImp procesa los imageBytes y asigna la URI file://
                rating = 4.5
            )

            try {
                mealRegisterRepo.saveMealRecipe(newRecipe, imageBytes)
                uiState = uiState.copy(isLoading = false, isSuccess = true)
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Error al guardar la receta"
                )
            }
        }
    }*/

    fun resetSuccessState() {
        uiState = uiState.copy(isSuccess = false)
    }
}