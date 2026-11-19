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
    val isLoading: Boolean = false,
    val selectedCollection: Int = 0, // = Mis recetas, 1 Publicadas, 2 Guardadas
    val collectionRecipes: List<Recipe> = emptyList()
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
        onSelectCollection(0)
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
        _uiState.update { state -> state.copy(
                openedFile = file,
                // De la lista completa de la pestaña solo los que tengan la etiqueta
                recipes = state.collectionRecipes.filter { it.tags.contains(file.name) }
            )
        }
    }

    // Restaura la lista completa
    fun onCloseFile() {
        _uiState.update { state -> state.copy(
                openedFile = null,
                recipes = state.collectionRecipes,
                selectedFilters = emptySet()
            )
        }
    }

    fun loadRecipeById(id: Int) {
        viewModelScope.launch {
            _selectedRecipe.value = repository.getRecipeById(id)
        }
    }

    fun onSelectCollection(index: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(selectedCollection = index, isLoading = true, selectedFilters = emptySet())
            }

            // 0 Mis recetas 1 Publicados, 2 Guardados
            val list = when (index) {
                0 -> repository.getMyRecipes()
                1 -> repository.getMyPublishedRecipes()
                else -> repository.getMySavedRecipes()
            }
            // Guardamos la lista completa
            _uiState.update { it.copy(collectionRecipes = list, recipes = list, isLoading = false) }
        }
    }
}