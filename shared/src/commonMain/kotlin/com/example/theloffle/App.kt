package com.example.theloffle

import Route
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.theloffle.core.UI_System.components.BottomBar
import com.example.theloffle.core.UI_System.theme.AppColors

import com.example.theloffle.features.buscador.presentation.ui.SearchScreen
import com.example.theloffle.ui.screen.HomeScreen
import com.example.theloffle.features.inicio.presentation.ui.PrincipalScreen

import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
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

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val bottomItems = listOf(
        BottomBarItem(Route.HomeScreen, "Inicio", Res.drawable.main_filled, Res.drawable.main_unfilled),
        BottomBarItem(Route.RecipeScreen, "Recetas", Res.drawable.recipe_filled, Res.drawable.recipe_unfilled),
        BottomBarItem(Route.StadisticScreen, "Estadisticas", Res.drawable.recipe_filled, Res.drawable.recipe_unfilled),
        BottomBarItem(Route.SearchScreen, "Buscar", Res.drawable.search_filled, Res.drawable.search_unfilled),
        BottomBarItem(Route.ProfileScreen, "Perfil", Res.drawable.profile_filled, Res.drawable.profile_unfilled)
    )
    Scaffold(
        containerColor = AppColors.background,
    ) {
        innerPadding ->
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
                composable<Route.RecipeScreen> { Text("Recetas") }
                composable<Route.SearchScreen> { SearchScreen() }
                composable<Route.ProfileScreen> { Text("Perfil") }
            }

            BottomBar(
                buttons = bottomItems,
                isSelected = { route -> currentDestination?.hasRoute(route::class) == true },
                onItemClick = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun Text(x0: String) {
    TODO("Not yet implemented")
}
