package com.example.theloffle.features.diario.data.repository

import com.example.theloffle.features.diario.domain.model.FoodPages
import com.example.theloffle.features.diario.domain.repository.MealRegisterRepo
import com.example.theloffle.features.recetas.domain.model.Recipe
import com.example.theloffle.features.recetas.domain.repository.RecipeRepo

// Right now the list is hardcode but we can make it dynamic from bbdd
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
    private val recipeRepo: RecipeRepo
) : MealRegisterRepo {

    override suspend fun getIngredientsList(): List<String> {
        return ingredients
    }

    override suspend fun getFilterList(): List<String> {
        return filters
    }

    override suspend fun saveMealRecipe(
        recipe: Recipe,
        imageBytes: ByteArray?,
        targetMeal: FoodPages
    ): Result<Unit> {
        val id = recipeRepo.saveRecipe(recipe)
        if(id <= 0){
            return Result.failure(Exception("No se ha podido guardar la receta"))
        }
        return Result.success(Unit)
    }
}