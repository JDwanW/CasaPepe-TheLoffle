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

    override suspend fun getPublishedRecipesByUser(author: String): List<Recipe> {
        // El nombre puede llegar con "@" delante
        val cleanName = author.removePrefix("@")
        // Sin base de datos (seria en web por como esta ahora) usamos las recetas de ejemplo
        val recipes = if (localDataSource != null) {
            withContext(workDispatcher) { localDataSource.getAll() }
        } else {
            allRecipes
        }
        // Solo los publicados cuyo autor coincide
        return recipes.filter { recipe ->
            recipe.published && recipe.author.removePrefix("@").equals(cleanName, ignoreCase = true)
        }
    }

    override suspend fun getMyRecipes(): List<Recipe> {
        // Primero miramos que este la sesion iniciada y que exista la bbdd
        val username = session.current.value ?: return emptyList()
        if (localDataSource == null) return emptyList()

        return withContext(workDispatcher) {
            localDataSource.getMyRecipes(username)
        }
    }

    override suspend fun getMyPublishedRecipes(): List<Recipe> {
        // Primero miramos que este la sesion iniciada y que exista la bbdd
        val username = session.current.value ?: return emptyList()
        if (localDataSource == null) return emptyList()

        return withContext(workDispatcher) {
            localDataSource.getMyPublishedRecipes(username)
        }
    }

    override suspend fun getMySavedRecipes(): List<Recipe> {
        // Primero miramos que este la sesion iniciada y que exista la bbdd
        val username = session.current.value ?: return emptyList()
        if (localDataSource == null) return emptyList()

        return withContext(workDispatcher) {
            // Si teniamos alguna receta guardada que elimina el usuario nos deja de salir
            visible(localDataSource.getMySavedRecipes(username))
        }
    }

    // Devuelve si la receta esta guardada o no
    override suspend fun isRecipeSaved(recipeId: Int): Boolean {
        // Miramos que haya sesion iniciado y bbdd
        val username = session.current.value ?: return false
        if(localDataSource == null) return false
        return withContext(workDispatcher){
            localDataSource.isSaved(username, recipeId)
        }
    }

    // Si pulsas el boton la guardas y si ya estaba guardada la quitas
    override suspend fun toggleSavedRecipe(recipeId: Int): Boolean {
        // Miramos si hay sesion iniciada y bbdd
        val username = session.current.value ?: return false
        if (localDataSource == null) return false

        // Leer el estado y cambiarlo
        return withContext(workDispatcher) {
            if (localDataSource.isSaved(username, recipeId)) {
                localDataSource.removeFromSaved(username, recipeId)
                false
            } else {
                localDataSource.addToSaved(username, recipeId)
                true
            }
        }
    }

    override suspend fun searchRecipes(
        query: String,
        filters: List<String>
    ): List<Recipe> {
        val text = query.trim()
        // Sin texto ni filtros no buscamos nada
        if (text.isEmpty() && filters.isEmpty()) return emptyList()
        // Con bbdd leemos del disco en el dispatcher de trabajo
        // Si falla la bbdd leemos los de ejemplo
        val recipes = if (localDataSource != null) {
            withContext(workDispatcher) { localDataSource.getAll() }
        } else {
            allRecipes
        }
        return visible(recipes.filter { recipe ->
            // Filtra las recetas que contienen esos ingredientes, tags y nombre
            recipe.tags.containsAll(filters) &&
                    (text.isEmpty() ||
                            recipe.ingredients.any { it.contains(text, ignoreCase = true) } ||
                            recipe.tags.any { it.contains(text, ignoreCase = true) } ||
                            recipe.name.contains(text, ignoreCase = true))
        })
    }
}