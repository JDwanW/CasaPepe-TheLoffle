package com.example.theloffle.features.recetas.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.theloffle.core.UI_System.theme.AppColors
import com.example.theloffle.features.perfil.domain.model.User
import com.example.theloffle.features.recetas.domain.model.Recipe
import org.jetbrains.compose.resources.painterResource
import theloffle.shared.generated.resources.Res
import theloffle.shared.generated.resources.profile_unfilled

@Composable
fun PublicationBlock(
    user: User,
    recipe: Recipe,
    onPublicationClick: (Int) -> Unit,
    onUserClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onPublicationClick(recipe.id) }
    ) {
        // 1. Cabecera del Usuario
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            AsyncImage(
                model = user.profilePhoto,
                placeholder = painterResource(Res.drawable.profile_unfilled),
                error = painterResource(Res.drawable.profile_unfilled),
                fallback = painterResource(Res.drawable.profile_unfilled),
                contentDescription = "Perfil",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .clickable { onUserClick(user.username) },
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "@${user.username}",
                color = AppColors.textPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }

        // 2. Imagen principal de la receta (soporta URL y file:// local)
        AsyncImage(
            model = recipe.imageUrl,
            contentDescription = recipe.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp),
            contentScale = ContentScale.Crop
        )

        // 3. Pie de la publicación (Título y Valoración)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = recipe.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = AppColors.textPrimary,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = if (recipe.published) "★ ${recipe.rating}" else "No publicado",
                modifier = Modifier
                    .background(
                        color = AppColors.buttonPrimary,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                color = AppColors.textPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        }
    }
}