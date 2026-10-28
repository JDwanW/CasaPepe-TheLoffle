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
    @Serializable
    data class MealRegisterScreen(val meadId: Int): Route
    @Serializable
    data object StadisticScreen: Route
}

