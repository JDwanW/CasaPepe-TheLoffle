package com.example.theloffle.features.diario.domain.repository

import com.example.theloffle.features.recetas.domain.model.Recipe

interface MealRegisterRepo {
    suspend fun getIngredientsList(): List<String>
    suspend fun getFilterList(): List<String>
    //suspend fun saveMealRecipe(recipe: Recipe, imageBytes: ByteArray?): Result<Unit>
    // has to be add to user recipe list
}