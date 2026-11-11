package com.example.theloffle.features.buscador.domain.repository

import com.example.theloffle.features.perfil.domain.model.User
import com.example.theloffle.features.recetas.domain.model.Recipe

interface SearchRepo{
    // Funcion que devuelve la lista de filtros
    suspend fun getFilterList(): List<String>
    // Funcion que hace toggle en filter pasado como argumento de entrada
    suspend fun toggleFilter(filter: String): Boolean
    // Funcion que hace la busqueda del usuario por query y la devuelve
    suspend fun searchUser(query: String): List<User>
    // Funcion que hace
    suspend fun searchRecipes(selectedFilters: Set<String>): List<Recipe>

}