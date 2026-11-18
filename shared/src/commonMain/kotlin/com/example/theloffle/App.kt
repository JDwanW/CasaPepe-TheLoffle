package com.example.theloffle

import com.example.theloffle.di.DefaultAppContainer
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
import androidx.compose.foundation.layout.navigationBarsPadding
//DATA BASE
import com.example.theloffle.db.TheLoffleDatabase
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
import com.example.theloffle.di.AppContainer
//LOG-IN SCREEN
import com.example.theloffle.features.inicio.presentation.model.LogInViewModell
import com.example.theloffle.features.inicio.presentation.ui.LogInScreenRoot
//REGISTER SCREEN
import com.example.theloffle.features.inicio.presentation.model.RegisterViewModel
import com.example.theloffle.features.inicio.presentation.ui.RegisterScreenRoot
//HOME SCREEN
import com.example.theloffle.features.diario.ui.HomeScreenRoot
import com.example.theloffle.features.diario.presentation.HomeViewModel
//MEAL REGISTER
import com.example.theloffle.features.diario.presentation.MealRegisterViewModel
//RECIPE SCREEN
import com.example.theloffle.features.recetas.ui.RecipeScreenRoot
import com.example.theloffle.features.recetas.ui.RecipeViewModel
//CREATE RECIPE SCREEN
import com.example.theloffle.features.recetas.ui.CreateRecipeScreenRoot
//SEARCH SCREEN
import com.example.theloffle.features.buscador.ui.SearchScreenRoot
import com.example.theloffle.features.buscador.ui.SearchViewModel
import com.example.theloffle.features.configuración.presentation.model.SettingsViewModel
import com.example.theloffle.features.configuración.presentation.ui.PersonalDataScreen
import com.example.theloffle.features.configuración.presentation.ui.PersonalDataScreenRoot
import com.example.theloffle.features.configuración.presentation.ui.SettingsScreen
import com.example.theloffle.features.configuración.presentation.ui.SettingsScreenRoot
import com.example.theloffle.features.diario.domain.model.FoodPages
import com.example.theloffle.features.perfil.presentation.ProfileViewModel
import com.example.theloffle.features.perfil.ui.ProfileScreenRoot
//PUBLICATION SCREEN
import com.example.theloffle.features.recetas.ui.PublicationScreenRoot
import com.example.theloffle.features.recetas.presentation.PublicationViewModel

@Composable
@Preview
// Pasamos el database como parametro
fun App(
    database: TheLoffleDatabase? = null,
    toScreen: Route? = null,
    onLanguageClick: (String) -> Unit = {},
    appContainer: AppContainer? = null
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    // Pasamos la database al container
    val containerRepo = remember(appContainer, database) {
        appContainer ?: DefaultAppContainer(database)
    }
    val isLoggedIn by containerRepo.authRepoInstance
        .isLoggedIn
        .collectAsState()
    val currentUsername by containerRepo.authRepoInstance
        .currentUsername
        .collectAsState()
    // Val para comprobar si estamos en selecion de recetas para quitar el bottombar
    val isRecipeInSelectionMode = if (currentDestination?.hasRoute<Route.RecipeScreen>() == true) {
        backStackEntry?.toRoute<Route.RecipeScreen>()?.isSelectionMode == true
    } else {
        false
    }
    // Val para comprobar si estamos en perfil ajeno, para el bottombar
    val isViewingOtherProfile = if (currentDestination?.hasRoute<Route.ProfileScreen>() == true) {
        val routeUsername = backStackEntry?.toRoute<Route.ProfileScreen>()?.username
        val loggedUser = currentUsername
        routeUsername != null &&
                !routeUsername.removePrefix("@").equals(loggedUser?.removePrefix("@"), ignoreCase = true)
    } else {
        false
    }
    val showBottomBar = !(
            currentDestination?.hasRoute<Route.PublicationScreen>() == true ||
            currentDestination?.hasRoute<Route.CreateRecipeScreen>() == true ||
            currentDestination?.hasRoute<Route.LogInScreen>() == true ||
            currentDestination?.hasRoute<Route.RegisterScreen>() == true ||
            currentDestination?.hasRoute<Route.SettingsScreen>() == true ||
            isRecipeInSelectionMode
        )

    val bottomItems = listOf(
        BottomBarItem(Route.HomeScreen, "Inicio", Res.drawable.main_filled, Res.drawable.main_unfilled),
        BottomBarItem(Route.RecipeScreen(), "Recetas", Res.drawable.recipe_filled, Res.drawable.recipe_unfilled),
        BottomBarItem(Route.SearchScreen, "Buscar", Res.drawable.search_filled, Res.drawable.search_unfilled),
        BottomBarItem(Route.ProfileScreen(), "Perfil", Res.drawable.profile_filled, Res.drawable.profile_unfilled)
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
                        when {
                            isViewingOtherProfile && route is Route.SearchScreen -> true
                            isViewingOtherProfile && route is Route.ProfileScreen -> false
                            else -> currentDestination?.hasRoute(route::class) == true
                        }
                    },
                    onItemClick = { route ->
                        if (route is Route.ProfileScreen && isViewingOtherProfile) {
                            navController.navigate(Route.ProfileScreen()) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = false
                                }
                            }
                        } else {
                            navController.navigate(route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    modifier = Modifier.navigationBarsPadding()
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
                
                startDestination = if (isLoggedIn) Route.HomeScreen else Route.LogInScreen,

                modifier = Modifier.fillMaxSize()
            ) {

                composable<Route.LogInScreen> {

                    val viewModel: LogInViewModell = viewModel {
                        LogInViewModell(
                            repository = containerRepo.authRepoInstance
                        )
                    }

                    LogInScreenRoot(
                        viewModel = viewModel,

                        backClick = {
                            navController.popBackStack()
                        },

                        onLoginSuccess = {
                            navController.navigate(Route.HomeScreen) {
                                popUpTo<Route.LogInScreen> {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        },

                        onRegisterClick = {
                            navController.navigate(Route.RegisterScreen)
                        }
                    )
                }

                composable<Route.RegisterScreen> {
                    val viewModel: RegisterViewModel = viewModel {
                        RegisterViewModel(
                            repository = containerRepo.authRepoInstance
                        )
                    }
                    RegisterScreenRoot(
                        viewModel = viewModel,

                        backClick = {
                            navController.popBackStack()
                        },
                        onRegisterSuccess = {
                            navController.navigate(Route.HomeScreen) {
                                popUpTo<Route.RegisterScreen> {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    )
                }
                composable<Route.HomeScreen> { homeBackStackEntry ->
                    val viewModel: HomeViewModel = viewModel {
                        HomeViewModel(repository = containerRepo.recipeRepoInstance)
                    }

                    val savedStateHandle = homeBackStackEntry.savedStateHandle
                    val mealName = savedStateHandle.get<String>("registered_meal")
                    val recipeId = savedStateHandle.get<Int>("registered_recipe_id")

                    LaunchedEffect(mealName, recipeId) {
                        if (mealName != null && recipeId != null) {
                            viewModel.registerMealRecipe(FoodPages.valueOf(mealName), recipeId)
                            savedStateHandle.remove<String>("registered_meal")
                            savedStateHandle.remove<Int>("registered_recipe_id")
                        }
                    }
                    HomeScreenRoot(
                        viewModel = viewModel,
                        onNavigateToSelectRecipe = { foodPage ->
                            navController.navigate(Route.RecipeScreen(isSelectionMode = true, mealId = foodPage))
                        },
                        onNavigateToCreateRecipe = { foodPage ->
                            navController.navigate(Route.CreateRecipeScreen(mealId = foodPage))
                        },
                        onPublicationClick = { recipeId ->
                            navController.navigate(Route.PublicationScreen(recipeId = recipeId))
                        },
                        onUserClick = { username ->
                            navController.navigate(Route.ProfileScreen(username = username))
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                composable<Route.RecipeScreen> { backStackEntry ->
                    val routeData = backStackEntry.toRoute<Route.RecipeScreen>()
                    val viewModel: RecipeViewModel = viewModel {
                        RecipeViewModel(repository = containerRepo.recipeRepoInstance)
                    }
                    val mealRegisterViewModel: MealRegisterViewModel = viewModel{
                        MealRegisterViewModel(repository = containerRepo.mealRegisterRepoInstance)
                    }
                    LaunchedEffect(routeData.mealId) {
                        if (routeData.isSelectionMode) {
                            mealRegisterViewModel.initMealType(routeData.mealId)
                        }
                    }
                    var selectedIdToReturn by remember { mutableStateOf<Int?>(null) }
                    LaunchedEffect(mealRegisterViewModel.uiState.isSuccess) {
                        if (mealRegisterViewModel.uiState.isSuccess) {
                            mealRegisterViewModel.resetSuccessState()
                            selectedIdToReturn?.let { id ->
                                navController.previousBackStackEntry?.savedStateHandle?.apply {
                                    set("registered_meal", routeData.mealId.name) // Pasamos el .name (String)
                                    set("registered_recipe_id", id)
                                }
                            }
                            navController.popBackStack()
                        }
                    }
                    RecipeScreenRoot(
                        viewModel = viewModel,
                        isSelectionMode = routeData.isSelectionMode,
                        onRecipeClick = { recipeId ->
                            navController.navigate(Route.PublicationScreen(recipeId = recipeId))
                        },
                        onConfirmSelection = { selectedRecipeId ->
                            selectedIdToReturn = selectedRecipeId
                            val selectedRecipe = viewModel.uiState.value.recipes.find { it.id == selectedRecipeId }
                            if (selectedRecipe != null) {
                                mealRegisterViewModel.onSaveExistingRecipe(selectedRecipe)
                            }
                        },
                        onNavigateToCreateRecipe = {
                            navController.navigate(Route.CreateRecipeScreen(mealId = FoodPages.NONE))
                        },
                        onNavigateBack = {
                            navController.popBackStack()
                        },
                        onNavigateToHome = {
                            navController.navigate(Route.HomeScreen) {
                                popUpTo<Route.HomeScreen> { inclusive = true }
                            }
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                composable<Route.CreateRecipeScreen> { backStackEntry ->
                    val routeData = backStackEntry.toRoute<Route.CreateRecipeScreen>()
                    val foodPage = routeData.mealId

                    val viewModel: MealRegisterViewModel = viewModel {
                        MealRegisterViewModel(repository = containerRepo.mealRegisterRepoInstance)
                    }

                    LaunchedEffect(foodPage) {
                        viewModel.initMealType(foodPage)
                    }

                    LaunchedEffect(viewModel.uiState.isSuccess) {
                        if (viewModel.uiState.isSuccess) {
                            viewModel.resetSuccessState()
                            if (foodPage != FoodPages.NONE) {
                                navController.previousBackStackEntry?.savedStateHandle?.apply {
                                    set("registered_meal", foodPage.name)
                                    set("registered_recipe_id", viewModel.uiState.savedRecipeId ?: 0)
                                }
                            }
                            navController.popBackStack()
                        }
                    }
                    CreateRecipeScreenRoot(
                        foodPage = foodPage,
                        viewModel = viewModel,
                        onNavigateBack = {
                            navController.popBackStack()
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
                composable<Route.ProfileScreen> { backStackEntry ->
                    val routeData = backStackEntry.toRoute<Route.ProfileScreen>()
                    val targetUsername = routeData.username ?: currentUsername ?: ""

                    val viewModel: ProfileViewModel = viewModel(key = targetUsername) {
                        ProfileViewModel(
                            targetUsername = targetUsername,
                            currentUsername = currentUsername,
                            userRepository = containerRepo.profileRepoInstance,
                            recipeRepository = containerRepo.recipeRepoInstance
                        )
                    }

                    ProfileScreenRoot(
                        viewModel = viewModel,
                        onRecipeClick = { recipeId ->
                            navController.navigate(Route.PublicationScreen(recipeId = recipeId))
                        },
                        onSettingsClick = {
                            navController.navigate(Route.SettingsScreen) {
                                launchSingleTop = true
                            }
                        },
                        onNavigateToHome = {
                            navController.navigate(Route.HomeScreen) {
                                popUpTo<Route.HomeScreen> { inclusive = true }
                            }
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                composable<Route.SettingsScreen> {
                    val viewModel: SettingsViewModel = viewModel {
                        SettingsViewModel(
                            authRepo = containerRepo.authRepoInstance
                        )
                    }
                    SettingsScreenRoot(
                        viewModel = viewModel,
                        onNavigateToPersonalData = {
                            navController.navigate(Route.PersonalDataScreen)
                        },
                        onBackClick = {
                            navController.popBackStack()
                        },
                        onLanguageClick = onLanguageClick

                    )
                }

                composable<Route.PersonalDataScreen> {

                    val viewModel: SettingsViewModel = viewModel {
                        SettingsViewModel(
                            authRepo = containerRepo.authRepoInstance
                        )
                    }

                    PersonalDataScreenRoot(
                        viewModel = viewModel,
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}