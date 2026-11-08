package com.example.theloffle.features.diario.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theloffle.features.diario.domain.model.FoodPages
import com.example.theloffle.features.recetas.domain.repository.RecipeRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.theloffle.features.recetas.domain.model.Recipe

data class HomeUiState(
    val selectedMeal: FoodPages = FoodPages.NONE,
    val registeredMeals: Set<FoodPages> = emptySet(),
    val feedRecipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class HomeViewModel(
    private val repository: RecipeRepo
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadFeed()
    }

    fun loadFeed() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                // TODO: we gonna do a random select for this loadFeed
                val recipes = repository.getRecipesByFilters(listOf("Pizza"))
                _uiState.update {
                    it.copy(feedRecipes = recipes, isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = e.message, isLoading = false)
                }
            }
        }
    }

    fun onMealSelected(meal: FoodPages) {
        _uiState.update { it.copy(selectedMeal = meal) }
    }
}