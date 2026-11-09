package com.example.theloffle.features.recetas.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theloffle.features.recetas.domain.repository.RecipeRepo
import com.example.theloffle.features.recetas.domain.model.File
import com.example.theloffle.features.recetas.domain.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Contrato de datos que representará toda la pantalla
data class RecipeUiState(
    val recipes: List<Recipe> = emptyList(),
    val filters: List<String> = emptyList(),
    val selectedFilters: Set<String> = emptySet(),
    val openedFile: File? = null,
    val isLoading: Boolean = false
)

class RecipeViewModel(
    private val repository: RecipeRepo
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeUiState())
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()
    private val _selectedRecipe = MutableStateFlow<Recipe?>(null)
    val selectedRecipe: StateFlow<Recipe?> = _selectedRecipe.asStateFlow()

    init {
        loadInitialFilters()
    }

    private fun loadInitialFilters() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val filterList = repository.getFilterList()
            _uiState.update {
                it.copy(
                    filters = filterList,
                    isLoading = false
                )
            }
        }
    }

    fun onToggleFilter(filter: String) {
        viewModelScope.launch {
            repository.toggleFilter(filter)
            _uiState.update { currentState ->
                currentState.copy(selectedFilters = setOf(filter))
            }
        }
    }
    fun onSelectFile(file: File) {
        viewModelScope.launch {
            _uiState.update { it.copy(openedFile = file, isLoading = true) }
            repository.applyFilterOnFile(file.name)
            val recipeList = repository.getRecipesInFile(file.name)
            _uiState.update {
                it.copy(
                    recipes = recipeList,
                    isLoading = false
                )
            }
        }
    }

    fun onCloseFile() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    openedFile = null,
                    recipes = emptyList(),
                    selectedFilters = emptySet()
                )
            }
        }
    }
    fun loadRecipeById(id: Int) {
        viewModelScope.launch {
            _selectedRecipe.value = repository.getRecipeById(id)
        }
    }
}