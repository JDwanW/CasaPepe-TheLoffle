package com.example.theloffle.features.diario.data.datasource

import com.example.theloffle.db.TheLoffleDatabase
import com.example.theloffle.features.recetas.data.datasource.RecipeLocalDataSource
import com.example.theloffle.features.recetas.domain.model.Recipe

class MealLogLocalDataSource(
    private val localDataSource: TheLoffleDatabase,
    private val recipeData: RecipeLocalDataSource
) {
    // Funcion para registrar comida
    fun register(username: String, day: String, slot: String, recipeId: Int) {
        localDataSource.mealLogQueries.registerMeal(username, day, slot, recipeId.toLong())
    }

    // Borrar registro
    fun unregister(username: String, day: String, slot: String, recipeId: Int) {
        localDataSource.mealLogQueries.unregisterMeal(username, day, slot, recipeId.toLong())
    }

    fun recipesOfSlot(username: String, day: String, slot: String): List<Recipe> =
        localDataSource.mealLogQueries.selectMealsForSlot(username, day, slot)
            .executeAsList()
            .map { recipeData.toRecipe(it) }

    // Registros ya hechos
    fun usedSlots(username: String, day: String): List<String> {
        return localDataSource.mealLogQueries.selectRegisteredSlots(username, day).executeAsList()
    }
}