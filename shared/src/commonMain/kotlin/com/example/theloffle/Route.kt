package com.example.theloffle

import kotlinx.serialization.Serializable
import com.example.theloffle.features.recetas.domain.model.Recipe
@Serializable
sealed interface Route {
    @Serializable
    data object PrincipalScreen: Route
    @Serializable
    data object HomeScreen: Route
    @Serializable
    data object SearchScreen: Route
    @Serializable
    data object RecipeScreen: Route
    data object PublicationScreen(val recipe: Recipe): Route
    @Serializable
    data object ProfileScreen: Route
}

