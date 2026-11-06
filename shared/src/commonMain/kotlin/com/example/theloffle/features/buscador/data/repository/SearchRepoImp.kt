package com.example.theloffle.features.buscador.data.repository

import com.example.theloffle.features.buscador.domain.repository.SearchRepo
import com.example.theloffle.features.recetas.domain.model.Recipe

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

class SearchRepoImp : SearchRepo {
    private val selected = mutableSetOf<String>()

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
    //        TODO("Search user by username or similars")
    override suspend fun searchUser(query: String): List<String> {
        return emptyList()
    }
    //        TODO("Apply filter and give list of recipes according to that")
    override suspend fun searchRecipes(query: String, selectedFilters: Set<String>): List<Recipe> {
        return emptyList()
    }

}