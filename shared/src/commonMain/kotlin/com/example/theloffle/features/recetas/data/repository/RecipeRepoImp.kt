package com.example.theloffle.features.recetas.data.repository

import com.example.theloffle.core.UserSession
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

class RecipeRepoImp(
    private val localDataSource: RecipeLocalDataSource? = null,
    private val session: UserSession
) : RecipeRepo {
    private val selected = mutableSetOf<String>()
    private val filtersByFile = mutableMapOf<String, Set<String>>()
    private val allRecipes = MockRecipeDataSource.recipes

    init {
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
        val recipes = if (localDataSource != null) {
            // Usamos una corrutina en la busqueda
            withContext(workDispatcher) {
                localDataSource.getAll()
            }
        } else {
            allRecipes
        }
        return visible(recipes.filter { it.tags.contains(fileName) })
    }

    // busca por id
    override suspend fun getRecipeById(id: Int): Recipe? {
        val recipeFound = if (localDataSource != null) {
            withContext(workDispatcher) { localDataSource.getById(id) }
        } else {
            allRecipes.find { it.id == id }
        }
        return if (recipeFound != null && couldSee(recipeFound)) recipeFound else null
    }

    // Se obtienen las recetas que tienen los filtros
    override suspend fun getRecipesByFilters(filters: List<String>): List<Recipe> {
        if (filters.isEmpty()) return emptyList()
        val recipes = if (localDataSource != null) {
            // Usamos una corrutina para la busqueda
            withContext(workDispatcher) { localDataSource.getAll() }
        } else {
            allRecipes
        }
        return visible(recipes.filter { it.tags.containsAll(filters) })
    }

    override suspend fun saveRecipe(recipe: Recipe): Int {
        val author = session.current.value ?: return -1 // Si es null --> -1
        if (localDataSource == null) return -1
        // Usando corrutina devolvemos la id al insertar receta
        // El autor ponemos que sea el que ha iniciado la sesion
        return withContext(workDispatcher) {
            localDataSource.insert(recipe.copy(author = author))
        }
    }

    // Comprobamos la visibilidad para una receta concreta
    private fun couldSee(recipe: Recipe): Boolean {
        return recipe.published || recipe.author == session.current.value
    }

    // Devuelve lista de recetas que son visibles para el usuario
    private fun visible(recipeList: List<Recipe>): List<Recipe> {
        val actualUser = session.current.value
        return recipeList.filter { couldSee(it) }
    }
}