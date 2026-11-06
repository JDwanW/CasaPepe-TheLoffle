package com.example.theloffle.features.recetas.data.repository

import com.example.theloffle.features.recetas.domain.model.Ingrediente
import com.example.theloffle.features.recetas.domain.repository.RecipeRepo
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

class RecipeRepoImp : RecipeRepo {
    private val selected = mutableSetOf<String>()
    private val filtersByFile = mutableMapOf<String, Set<String>>()

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
    override suspend fun applyFilterOnFile(fileName: String) {
        filtersByFile[fileName] = selected.toSet()
    }
    // Todo: it should be reach from
    //  his local BBDD if user doesnt have connection to WIFI
    //  cloud BBDD if user does have conenction to WIFI
    override suspend fun getRecipesInFile(filterName: String): List<Recipe> {
        return if (filterName != "Italiana") {
            List(10) { index ->
                Recipe(
                    id = index + 1,
                    name = "Receta ${index + 1} de $filterName",
                    author = "Chef ${index + 1}",
                    ingredients = listOf(
                        Ingrediente.CERDO, Ingrediente.CORDERO
                    ),
                    tags =  emptyList(),
                    description = "Paso 1: Preparar los ingredientes.\nPaso 2: Cocinar a fuego lento durante 15 minutos.\nPaso 3: Servir bien caliente.",
                    published = true,
                    imageUrl = "https://picsum.photos/seed/${filterName}${index}/400/300"
                )
            }
        } else {
            emptyList()
        }
    }
}