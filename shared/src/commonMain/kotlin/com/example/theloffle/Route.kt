package com.example.theloffle

import com.example.theloffle.features.diario.domain.model.FoodPages
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {

    @Serializable
    data object SettingsScreen : Route
    @Serializable
    data object PersonalDataScreen : Route
    @Serializable
    data object LogInScreen : Route
    @Serializable
    data object RegisterScreen : Route

    @Serializable
    data object HomeScreen: Route
    @Serializable
    data class CreateRecipeScreen (val mealId: FoodPages): Route
    @Serializable
    data object SearchScreen: Route
    @Serializable
    data class RecipeScreen (val isSelectionMode: Boolean = false, val mealId: FoodPages = FoodPages.NONE): Route
    @Serializable
    data class PublicationScreen (val recipeId: Int): Route
    @Serializable
    data class ProfileScreen (val username: String? = null): Route
}

