package com.example.theloffle.features.diario.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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
            mealDone =  listOf(false,false),
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
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "¡Has visto todas las publicaciones recientes!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
        }
    }
}