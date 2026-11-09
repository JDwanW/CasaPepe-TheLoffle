package com.example.theloffle.features.perfil.domain.model

import com.example.theloffle.features.diario.domain.model.FoodPages
import com.example.theloffle.features.recetas.domain.model.Recipe
import kotlinx.serialization.Serializable

// Creamos la clase recetas
@Serializable
data class User(
    val id: Int,
    val username: String,
    val password: String,
    val numberOfMeal: List<FoodPages>,
    val mealDone: List<Boolean>,
    val profilePhoto: String? = null,
    val recipeList: List<Recipe>
)
