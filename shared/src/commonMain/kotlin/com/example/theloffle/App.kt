package com.example.theloffle

import DefaultAppContainer
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
//APP DATA
import com.example.theloffle.core.UI_System.theme.AppColors

//ROUTE
import com.example.theloffle.core.UI_System.components.BottomBar
import com.example.theloffle.core.UI_System.components.BottomBarItem

import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.toRoute
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination

//RESOURCE IMAGE
import theloffle.shared.generated.resources.Res
import theloffle.shared.generated.resources.main_filled
import theloffle.shared.generated.resources.main_unfilled
import theloffle.shared.generated.resources.profile_filled
import theloffle.shared.generated.resources.profile_unfilled
import theloffle.shared.generated.resources.recipe_filled
import theloffle.shared.generated.resources.recipe_unfilled
import theloffle.shared.generated.resources.search_filled
import theloffle.shared.generated.resources.search_unfilled
//VIEW MODEL
import androidx.lifecycle.viewmodel.compose.viewModel
//HOME SCREEN
import com.example.theloffle.features.diario.ui.HomeScreenRoot
import com.example.theloffle.features.diario.ui.HomeViewModel
//MEAL REGISTER SCREEN
import com.example.theloffle.features.diario.ui.MealRegisterScreenRoot
//RECIPE SCREEN
import com.example.theloffle.features.recetas.ui.RecipeScreenRoot
import com.example.theloffle.features.recetas.ui.RecipeViewModel
//SEARCH SCREEN
import com.example.theloffle.features.buscador.ui.SearchScreenRoot
import com.example.theloffle.features.buscador.ui.SearchViewModel
import com.example.theloffle.features.diario.presentation.MealRegisterViewModel
//PUBLICATION SCREEN
import com.example.theloffle.features.recetas.ui.PublicationScreenRoot
import com.example.theloffle.features.recetas.presentation.PublicationViewModel

@Composable
@Preview
fun App(
    toScreen: Route? = null
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val containerRepo = remember { DefaultAppContainer() }
    val showBottomBar = currentDestination?.hasRoute<Route.PublicationScreen>() == false

    val bottomItems = listOf(
        BottomBarItem(Route.HomeScreen, "Inicio", Res.drawable.main_filled, Res.drawable.main_unfilled),
        BottomBarItem(Route.RecipeScreen, "Recetas", Res.drawable.recipe_filled, Res.drawable.recipe_unfilled),
        BottomBarItem(Route.SearchScreen, "Buscar", Res.drawable.search_filled, Res.drawable.search_unfilled),
        BottomBarItem(Route.ProfileScreen, "Perfil", Res.drawable.profile_filled, Res.drawable.profile_unfilled)
    )

    LaunchedEffect(toScreen) {
        toScreen?.let {
            navController.navigate(it) {
                launchSingleTop = true
            }
        }
    }

    Scaffold(
        containerColor = AppColors.background,
        bottomBar = {
            if(showBottomBar){
                BottomBar(
                    buttons = bottomItems,
                    isSelected = { route ->
                        currentDestination?.hasRoute(route::class) == true
                    },
                    onItemClick = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {}
                    )
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            NavHost(
                navController = navController,
                // TODO: need to check if user is logged in or not, if logged in
                // TODO: start = Home if not start = LogIn
                startDestination = Route.HomeScreen,
                modifier = Modifier.fillMaxSize()
            ) {
                composable<Route.HomeScreen> {
                    val viewModel: HomeViewModel = viewModel {
                        HomeViewModel(repository = containerRepo.recipeRepoInstance)
                    }
                    HomeScreenRoot(
                        viewModel = viewModel,
                        onMealClick = { foodPage ->
                            navController.navigate(Route.MealRegisterScreen(foodPage))
                        },
                        onPublicationClick = { recipeId ->
                            navController.navigate(Route.PublicationScreen(recipeId = recipeId))
                        },
                        onUserClick = { username ->
                            // Navega al perfil del usuario
                            // navController.navigate(Route.ProfileScreen(username = username))
                        },
                        // TODO: si cierras app y entras dando al icono por alguna razon hace un bottom padding
                        modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
                    )
                }
                composable<Route.MealRegisterScreen> {backStackEntry ->
                    val routeData = backStackEntry.toRoute<Route.MealRegisterScreen>()
                    val foodPage = routeData.mealId // El tipo de comida del botón pulsado

                    val viewModel: MealRegisterViewModel = viewModel {
                        MealRegisterViewModel(repository = containerRepo.mealRegisterRepoInstance)
                    }

                    MealRegisterScreenRoot(
                        foodPage = foodPage,
                        viewModel = viewModel,
                        onNavigateBack = {
                            navController.popBackStack()
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                composable<Route.RecipeScreen> {
                    val viewModel: RecipeViewModel = viewModel {
                        RecipeViewModel(repository = containerRepo.recipeRepoInstance)
                    }
                    RecipeScreenRoot(
                        viewModel = viewModel,
                        onRecipeClick = { recipeId ->
                            navController.navigate(Route.PublicationScreen(recipeId = recipeId))
                        },
                        onNavigateToHome = {
                            navController.navigate(Route.HomeScreen){
                                //Limpia la pila para evitar acumular pantallas
                                popUpTo<Route.HomeScreen> {inclusive = true}
                            }
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                composable<Route.SearchScreen> { // Se instancia el ViewModel pasando las dependencias de tu contenedor
                    val viewModel: SearchViewModel = viewModel {
                        SearchViewModel(repository = containerRepo.searchRepoInstance)
                    }
                    SearchScreenRoot(
                        viewModel = viewModel,
                        onRecipeClick = { recipeId ->
                            navController.navigate(Route.PublicationScreen(recipeId = recipeId))
                        },
                        onNavigateToHome = {
                            navController.navigate(Route.HomeScreen){
                                popUpTo<Route.HomeScreen> {inclusive = true}
                            }
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                composable<Route.PublicationScreen>{backStackEntry ->
                    val routeData = backStackEntry.toRoute<Route.PublicationScreen>()
                    val recipeId = routeData.recipeId

                    val viewModel: PublicationViewModel = viewModel {
                        PublicationViewModel(recipeRepository = containerRepo.recipeRepoInstance)
                    }

                    PublicationScreenRoot(
                        recipeId = recipeId,
                        viewModel = viewModel,
                        onNavigateToHome = {
                            navController.navigate(Route.HomeScreen){
                                popUpTo<Route.HomeScreen> {inclusive = true}
                            }
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                composable<Route.ProfileScreen> {  }
            }
        }
    }
}
