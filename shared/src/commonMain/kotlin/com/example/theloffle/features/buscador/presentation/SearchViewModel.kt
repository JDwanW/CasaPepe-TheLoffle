package com.example.theloffle.features.buscador.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theloffle.features.buscador.domain.repository.SearchRepo
import com.example.theloffle.features.recetas.domain.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SearchUiState(
    val searchQuery: String = "",
    val filters: List<String> = emptyList(),
    val selectedFilters: Set<String> = emptySet(),
    val recipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = false
)

class SearchViewModel(
    private val repository: SearchRepo
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    init {
        loadFilters()
    }
    // Funcion que carga la lista de filtros de la repo
    private fun loadFilters() {
        viewModelScope.launch {
            val filterList = repository.getFilterList()
            _uiState.update { it.copy(filters = filterList) }
            performSearch()
        }
    }
    // Funcion que hace la busqueda tras notar cambio en el textfield
    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        performSearch()
    }
    // Funcion que actualiza la lista de recetas tras notar cambio en los filtros, en este caso puede haber mas de un filtro
    fun onToggleFilter(filter: String) {
        _uiState.update { state ->
            val updated = if (filter in state.selectedFilters) {
                state.selectedFilters - filter
            } else {
                state.selectedFilters + filter
            }
            state.copy(selectedFilters = updated)
        }
        performSearch()
    }
    // Funcion que realiza la busqueda
    private fun performSearch() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val results = repository.searchRecipes(
                selectedFilters = _uiState.value.selectedFilters
            )
            _uiState.update { it.copy(recipes = results, isLoading = false) }
        }
    }
}