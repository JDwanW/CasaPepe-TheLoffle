package com.example.theloffle.features.diario.data.repository

import com.example.theloffle.features.diario.data.datasource.LocalStorageManager
import com.example.theloffle.features.diario.domain.repository.MealRegisterRepo
import com.example.theloffle.features.recetas.data.datasource.MockRecipeDataSource
import com.example.theloffle.features.recetas.domain.repository.RecipeRepo
import com.example.theloffle.features.recetas.domain.model.Recipe
import kotlin.random.Random

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
private val ingredients = listOf(
    "Arroz",
    "Pollo",
    "Huevos",
    "Aceite de Oliva",
    "Cebolla",
    "Ajo",
    "Tomate",
    "Queso Mozzarella",
    "Harina de Trigo",
    "Leche",
    "Mantequilla",
    "Pimiento Rojo",
    "Pimiento Verde",
    "Patatas",
    "Sal",
    "Pimienta Negra"
)

class MealRegisterRepoImp(
    //private val dataSource: MockRecipeDataSource,
    //private val localStorageManager: LocalStorageManager
) : MealRegisterRepo {

    override suspend fun getIngredientsList(): List<String> {
        return ingredients
    }

    override suspend fun getFilterList(): List<String> {
        return filters
    }

    /*override suspend fun saveMealRecipe(
        recipe: Recipe,
        imageBytes: ByteArray?
    ): Result<Unit> {
        return runCatching {
            val finalImagePath = imageBytes?.let { bytes ->
                localStorageManager.saveImageToInternalStorage(bytes)
            } ?: recipe.imageUrl
            val finalId = if (recipe.id <= 0) Random.nextInt(1000, 999999) else recipe.id

            val recipeToSave = recipe.copy(
                id = finalId,
                imageUrl = finalImagePath
            )

            // 4. Persistir en a fuente de datos (BBDD o Mock)
            dataSource.addRecipe(recipeToSave)
        }
    }*/
}
