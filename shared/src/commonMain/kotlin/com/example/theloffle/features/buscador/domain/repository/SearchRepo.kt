package com.example.theloffle.features.buscador.domain.repository

import com.example.theloffle.features.recetas.domain.model.Recipe

interface SearchRepo{
    suspend fun getFilterList(): List<String>
    suspend fun toggleFilter(filter: String): Boolean
    // TODO create User Class
    suspend fun searchUser(query: String): List<String>
    suspend fun searchRecipes(query: String, selectedFilters: Set<String>): List<Recipe>

}