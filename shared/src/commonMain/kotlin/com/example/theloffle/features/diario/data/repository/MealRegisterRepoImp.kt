package com.example.theloffle.features.diario.data.repository

import com.example.theloffle.core.UserSession
import com.example.theloffle.core.database.workDispatcher
import com.example.theloffle.core.todayAsText
import com.example.theloffle.features.diario.data.datasource.MealLogLocalDataSource
import com.example.theloffle.features.diario.domain.model.FoodPages
import com.example.theloffle.features.diario.domain.repository.MealRegisterRepo
import com.example.theloffle.features.recetas.domain.model.Recipe
import com.example.theloffle.features.recetas.domain.repository.RecipeRepo
import kotlinx.coroutines.withContext

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
    private val recipeRepo: RecipeRepo,
    private val session: UserSession,
    private val mealLog: MealLogLocalDataSource? = null
) : MealRegisterRepo {
    // POr si llegaramos a hacerlo en web
    // En web no hay SQLDelight asi que se guardaria en memoria
    // Lo pongo por si acaso ya que se podria llegar a expandir
    // Tambien la uso por si falla la bbdd
    private val webMeals = mutableMapOf<FoodPages, Int>()

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
    ): Result<Int> {
        val id = recipeRepo.saveRecipe(recipe)
        if(id <= 0){
            return Result.failure(Exception("No se ha podido guardar la receta"))
        }
        return Result.success(id)
    }

    // Reistramos la comida si cumple las condiciones
    override suspend fun registerMeal(
        recipeId: Int,
        slot: FoodPages
    ): Result<Unit> {
        // Si el registro es NONE o el usuario no esta iniciado no se puede guardar
        if (slot == FoodPages.NONE)
            return Result.failure(Exception("Franja no valida"))
        val username = session.current.value ?:
            return Result.failure(Exception("No hay ninguna sesión iniciada"))

        if (mealLog != null) {
            withContext(workDispatcher) {
                mealLog.register(username, todayAsText(), slot.name, recipeId)
            }
        }
        else {
            // Como no hay bbdd en web se guardaria en el map
            webMeals[slot] = recipeId
        }
        return Result.success(Unit)
    }

    // Comidas registradas hoy
    override suspend fun todayMeals(): Map<FoodPages, Int> {
        if (mealLog == null)
            return webMeals.toMap()
        // User nulo devuelve mapa vacio
        val username = session.current.value ?:
            return emptyMap()
        val rows = withContext(workDispatcher) {
            mealLog.registeredMeals(username, todayAsText())
        }
        // Devuelve las comidas registradas
        return rows.mapKeys { FoodPages.valueOf(it.key) }
    }
}