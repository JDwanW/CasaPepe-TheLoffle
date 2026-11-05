package com.example.theloffle.features.recetas.data.repository

import com.example.theloffle.features.recetas.domain.repository.UpdatedFilter

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

class UpdatedFilterImpl : UpdatedFilter {
    private val selected = mutableSetOf<String>()
    private val filtersByFile = mutableMapOf<String, Set<String>>()

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
    // Todo: if theres no recipe in that filter show a message
    override suspend fun getRecipesInFile(filterName: String): List<String> {
        return if (filterName != "Italiana") {
            // Placeholder data. Later, read the real saved recipes from a file/DB here.
            List(10) { "Receta ${it + 1} de $filterName" }
        } else {
            emptyList()
        }
    }

}