package com.example.theloffle.features.recetas.domain.repository

import com.example.theloffle.features.recetas.domain.model.Recipe

interface UpdatedFilter{
    suspend fun getFilterList(): List<String>
    suspend fun toggleFilter(filter: String): Boolean
    suspend fun applyFilterOnFile(fileName: String)
    suspend fun getRecipesInFile(fileName: String): List<Recipe>
}