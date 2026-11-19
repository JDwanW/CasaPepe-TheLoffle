package com.example.theloffle.features.recetas.domain.repository

import com.example.theloffle.features.recetas.domain.model.Recipe

interface RecipeRepo {
    suspend fun getFilterList(): List<String>
    suspend fun toggleFilter(filter: String): Boolean
    suspend fun applyFilterOnFile(fileName: String)
    suspend fun getRecipesInFile(fileName: String): List<Recipe>
    suspend fun getRecipeById(id: Int): Recipe?
    suspend fun getRecipesByFilters(filters: List<String>): List<Recipe>
    suspend fun saveRecipe(recipe: Recipe): Int
    suspend fun getPublishedRecipesByUser(author: String): List<Recipe>
    suspend fun getMyRecipes(): List<Recipe>
    suspend fun getMyPublishedRecipes(): List<Recipe>
    suspend fun getMySavedRecipes(): List<Recipe>
    suspend fun isRecipeSaved(recipeId: Int): Boolean
    suspend fun toggleSavedRecipe(recipeId: Int): Boolean
}