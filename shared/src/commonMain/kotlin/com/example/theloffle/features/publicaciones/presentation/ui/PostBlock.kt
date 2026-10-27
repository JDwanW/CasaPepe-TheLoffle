package com.example.theloffle.features.publicaciones.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theloffle.core.UI_System.theme.AppColors
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import theloffle.shared.generated.resources.Res
import theloffle.shared.generated.resources.test1
import kotlin.String

//Todo : create a box with
//Todo : 3 rows
//Todo : 1 row: profile picture + profile username + unfollow profile
//Todo : 2 row: image of the recipe
//Todo : 3 row: recipe rating from 0 ... 5 in double

@Composable
fun PostBlock(
    modifier: Modifier = Modifier,
    userName: String,
    userImage : DrawableResource,
    postImage :  DrawableResource,
    postRating : String             // maybe int
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // Post User
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Button(
                onClick = {

                },
                modifier = Modifier.size(50.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(50.dp),
                contentPadding = PaddingValues(0.dp),
            ) {
                Image(
                    painter = painterResource(userImage),
                    contentDescription = "Perfil",
                    modifier = Modifier.size(45.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "@$userName",
                color = AppColors.textPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
        // Post Image
        Image(
            painter = painterResource(postImage),
            contentDescription = "Receta",
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth()
                .height(400.dp),
            contentScale = ContentScale.Crop
        )
        // Post Rating
        Text(
            text = "Valoración: $postRating / 5",
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .background(color = AppColors.buttonPrimary),
            color = AppColors.textPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}
