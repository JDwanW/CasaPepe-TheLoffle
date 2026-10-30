package com.example.theloffle.features.recetas.domain.repository

interface UpdatedFilter{
    suspend fun getFilterList(): List<String>
    suspend fun toggleFilter(filter: String): Boolean
    suspend fun applyFilterOnFile(fileName: String)
    suspend fun getRecipesInFile(fileName: String): List<String>
}