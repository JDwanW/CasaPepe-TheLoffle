package com.example.theloffle.features.diario.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.theloffle.core.UI_System.theme.AppColors
import com.example.theloffle.features.diario.domain.model.FoodPages
import com.example.theloffle.features.perfil.data.repository.UserRepoImp
import com.example.theloffle.features.perfil.domain.model.User
import com.example.theloffle.features.recetas.domain.model.Recipe
import com.example.theloffle.features.recetas.presentation.components.PublicationBlock

@Composable
fun ScrollBlock(
    recipes: List<Recipe>,
    onPublicationClick: (Int) -> Unit,
    onUserClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.background),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        //right now hardcore just to try
        val user = User(
            id = 1,
            username = "Pepias1",
            password = "123asas12",
            numberOfMeal = listOf(FoodPages.BREAKFAST, FoodPages.LUNCH),
            profilePhoto = "",
            recipeList = emptyList()

        )
        //instead items = recipes, it should be a list of pair <User,Recipe>
        // 1 user 1 recipe
        items(
            items = recipes,
            key = { recipe -> recipe.id }
        ) { recipe ->
            PublicationBlock(
                user = user,
                recipe = recipe,
                onPublicationClick = onPublicationClick,
                onUserClick = onUserClick
            )
        }
    }
}