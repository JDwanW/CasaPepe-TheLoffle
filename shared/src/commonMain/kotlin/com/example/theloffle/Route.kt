package com.example.theloffle

import kotlinx.serialization.Serializable

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
    @Serializable
    data object ProfileScreen: Route
}

