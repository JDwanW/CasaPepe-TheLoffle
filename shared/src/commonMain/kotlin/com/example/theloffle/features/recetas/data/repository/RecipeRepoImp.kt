package com.example.theloffle.features.recetas.data.repository

import com.example.theloffle.features.recetas.data.datasource.MockRecipeDataSource
import com.example.theloffle.features.recetas.domain.repository.RecipeRepo
import com.example.theloffle.features.recetas.domain.model.Recipe

// Right now the list is hardcode but we can make it dynamilic from bbdd
private val filters = listOf(
    "Española",
    "Italiana",
    "Asiática",
    "China",
    "Hamburguesa",
    "Pizza",
    "Bebidas",
    "Cocktails",
    "Postre",
    "Mexicano",
    "Carnes",
    "Pescados",
    "Vegetariano",
    "Vegano",
    "Sin Alergias",
    "Sin Gluten",
    "Horno",
    "AirFryer",
    "Parrilla",
    "Sartén"
)

class RecipeRepoImp : RecipeRepo {
    private val selected = mutableSetOf<String>()
    private val filtersByFile = mutableMapOf<String, Set<String>>()
    // TODO: Create another Repo as proxy just for tests
    private val allRecipes = MockRecipeDataSource.recipes

    // Should be from a JSON
    override suspend fun getFilterList(): List<String> = filters
    override suspend fun toggleFilter(filter: String): Boolean {
        val exists = selected.contains(filter)
        selected.clear()
        if (!exists) {
            selected.add(filter)
        }
        return !exists
    }
    override suspend fun applyFilterOnFile(fileName: String) {
        filtersByFile[fileName] = selected.toSet()
    }
    override suspend fun getRecipesInFile(fileName: String): List<Recipe> {
        return allRecipes.filter { recipe -> recipe.tags.contains(fileName) }
    }
    override suspend fun getRecipeById(id: Int): Recipe? {
        return allRecipes.find { it.id == id }
    }
    override suspend fun getRecipesByFilters(filters: List<String>): List<Recipe> {
        if (filters.isEmpty()) return emptyList()
        return allRecipes.filter { recipe -> recipe.tags.containsAll(filters) }
    }
}