package com.example.theloffle.features.buscador.data.repository

import com.example.theloffle.features.buscador.domain.repository.SearchRepo
import com.example.theloffle.features.recetas.domain.model.Recipe
import com.example.theloffle.features.recetas.domain.repository.RecipeRepo

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

class SearchRepoImp(
    private val recipeRepo: RecipeRepo
) : SearchRepo {
    private val selected = mutableSetOf<String>()

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
    //        TODO("Search user by username or similars")
    override suspend fun searchUser(query: String): List<String> {
        return emptyList()
    }
    override suspend fun searchRecipes(selectedFilters: Set<String>): List<Recipe> {
        if (selectedFilters.isEmpty()) return emptyList()
        // Lahmda to have all the recipes from all the filters
        val allRecipes = filters.flatMap { filterName ->
            recipeRepo.getRecipesInFile(filterName)
        }.distinctBy { recipe -> recipe.id }
        return allRecipes.filter { recipe ->
            recipe.tags.containsAll(selectedFilters)
        }
    }
}