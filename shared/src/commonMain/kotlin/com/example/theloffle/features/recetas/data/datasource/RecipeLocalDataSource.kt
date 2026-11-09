package com.example.theloffle.features.recetas.data.datasource

import com.example.theloffle.db.Recipes
import com.example.theloffle.db.TheLoffleDatabase
import com.example.theloffle.features.recetas.domain.model.Recipe
import kotlinx.serialization.json.Json

// Reads and writes recipes in the local database.
// It is the only class that knows how recipes are stored (as JSON).
// !! Recipes = bbdd, Recipe = class

class RecipeLocalDataSource(
    private val database: TheLoffleDatabase
) {
    // Json --> Recipe (We copy the row's id into the Json's id)
    fun toRecipe(fila: Recipes): Recipe = Json.decodeFromString<Recipe>(fila.json)
        .copy(id = fila.id.toInt())

    // Function that get all recipes as a list of Recipe that we transform from Jsons
    fun getAll(): List<Recipe> = database.recipeQueries.selectAll().executeAsList()
        .map { toRecipe(it) }

    // Returns a recipe by its Id
    fun getById(id: Int): Recipe? = database.recipeQueries.selectForId(id.toLong())
        // If it isn't null --> toRecipe
        .executeAsOneOrNull()?.let { toRecipe(it) }

    // Insert a Recipe into the bbdd
    fun insert(recipe: Recipe){
        database.recipeQueries.insertRecipe(
            name = recipe.name,
            // 1 True, 0 False. L for Long
            published = if(recipe.published) 1L else 0L,
            json = Json.encodeToString(recipe)
        )
    }
}

