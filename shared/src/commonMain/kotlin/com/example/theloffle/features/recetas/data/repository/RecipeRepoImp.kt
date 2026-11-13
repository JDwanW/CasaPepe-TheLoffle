package com.example.theloffle.features.recetas.data.repository

import com.example.theloffle.core.database.workDispatcher
import com.example.theloffle.features.recetas.data.datasource.MockRecipeDataSource
import com.example.theloffle.features.recetas.domain.repository.RecipeRepo
import com.example.theloffle.features.recetas.domain.model.Recipe
import com.example.theloffle.features.recetas.data.datasource.RecipeLocalDataSource
import kotlinx.coroutines.withContext

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

class RecipeRepoImp(private val localDataSource: RecipeLocalDataSource? = null) : RecipeRepo {
    private val selected = mutableSetOf<String>()
    private val filtersByFile = mutableMapOf<String, Set<String>>()
    private val allRecipes = MockRecipeDataSource.recipes

    init{
        localDataSource?.insertIfEmpty(MockRecipeDataSource.recipes)
    }

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
        val recipes = if(localDataSource != null){
            // Usamos una corrutina en la busqueda
            withContext(workDispatcher){
                localDataSource.getAll()
            }
        }
        else{
            allRecipes
        }
        return recipes.filter { it.tags.contains(fileName) }
    }

    // Si la bbdd existe busca por id
    override suspend fun getRecipeById(id: Int): Recipe? {
        return if(localDataSource != null){
            // Usamos una corrutina
            withContext(workDispatcher) { localDataSource.getById(id) }
        }
        else{
            allRecipes.find {it.id == id}
        }
    }

    // Se obtienen las recetas que tienen los filtros
    override suspend fun getRecipesByFilters(filters: List<String>): List<Recipe> {
        if (filters.isEmpty()) return emptyList()
        val recipes = if(localDataSource != null){
            // Usamos una corrutina para la busqueda
            withContext(workDispatcher){ localDataSource.getAll() }
        }
        else{
            allRecipes
        }
        return recipes.filter { it.tags.containsAll(filters) }
    }
}