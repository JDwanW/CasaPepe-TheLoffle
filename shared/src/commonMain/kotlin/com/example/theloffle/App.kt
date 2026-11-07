package com.example.theloffle

import DefaultAppContainer
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.theloffle.core.UI_System.components.BottomBar
import com.example.theloffle.core.UI_System.theme.AppColors

import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.toRoute
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavDestination.Companion.hasRoute
import com.example.theloffle.core.UI_System.components.BottomBarItem
import theloffle.shared.generated.resources.Res
import theloffle.shared.generated.resources.main_filled
import theloffle.shared.generated.resources.main_unfilled
import theloffle.shared.generated.resources.profile_filled
import theloffle.shared.generated.resources.profile_unfilled
import theloffle.shared.generated.resources.recipe_filled
import theloffle.shared.generated.resources.recipe_unfilled
import theloffle.shared.generated.resources.search_filled
import theloffle.shared.generated.resources.search_unfilled
//HOME SCREEN
import com.example.theloffle.features.diario.ui.HomeScreen
//MEAL REGISTER SCREEN
import com.example.theloffle.features.diario.ui.MealRegisterScreen
//RECIPE SCREEN
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.theloffle.features.recetas.ui.RecipeScreenRoot
import com.example.theloffle.features.recetas.ui.RecipeViewModel
//SEARCH SCREEN
import com.example.theloffle.features.buscador.ui.SearchScreenRoot
import com.example.theloffle.features.buscador.ui.SearchViewModel
//PUBLICATION SCREEN
import com.example.theloffle.features.recetas.ui.PublicationScreenRoot

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val containerRepo = remember { DefaultAppContainer() }

    val bottomItems = listOf(
        BottomBarItem(Route.HomeScreen, "Inicio", Res.drawable.main_filled, Res.drawable.main_unfilled),
        BottomBarItem(Route.RecipeScreen, "Recetas", Res.drawable.recipe_filled, Res.drawable.recipe_unfilled),
        BottomBarItem(Route.SearchScreen, "Buscar", Res.drawable.search_filled, Res.drawable.search_unfilled),
        BottomBarItem(Route.ProfileScreen, "Perfil", Res.drawable.profile_filled, Res.drawable.profile_unfilled)
    )
    Scaffold(
        containerColor = AppColors.background,
        bottomBar = {
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
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            NavHost(
                navController = navController,
                startDestination = Route.HomeScreen,
                modifier = Modifier.fillMaxSize()
            ) {
                composable<Route.HomeScreen> { HomeScreen(modifier = Modifier.padding(innerPadding)) }
                composable<Route.MealRegisterScreen> {

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
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                composable<Route.PublicationScreen>{backStackEntry ->
                    val routeData = backStackEntry.toRoute<Route.PublicationScreen>()
                    val recipeId = routeData.recipeId

                    val viewModel: RecipeViewModel = viewModel {
                        RecipeViewModel(repository = containerRepo.recipeRepoInstance)
                    }

                    // Pedimos la receta al repositorio cuando entra a la pantalla
                    LaunchedEffect(recipeId) {
                        viewModel.loadRecipeById(recipeId)
                    }

                    val selectedRecipe by viewModel.selectedRecipe.collectAsState()

                    selectedRecipe?.let { recipe ->
                        PublicationScreenRoot(
                            viewModel = viewModel,
                            selectedRecipe = recipe,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
                composable<Route.ProfileScreen> {  }
            }
        }
    }
}
