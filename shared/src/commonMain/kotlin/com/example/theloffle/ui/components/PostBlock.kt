package com.example.theloffle.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import theloffle.shared.generated.resources.Res
import com.example.theloffle.core.UI_System.theme.AppColors
import androidx.compose.ui.layout.ContentScale
// Para implementar TextButton
import androidx.compose.material3.TextButton
import androidx.compose.foundation.layout.*
import androidx.compose.ui.text.font.FontWeight
//
import theloffle.shared.generated.resources.test1



//Todo : create a box with
//Todo : 3 rows
//Todo : 1 row: profile picture + profile username + unfollow profile
//Todo : 2 row: image of the recipe
//Todo : 3 row: recipe rating from 0 ... 5 in double
@Composable
fun PostBlock(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(480.dp)
            .background(
                AppColors.buttonPrimary,
                RoundedCornerShape(10.dp)
            )
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(Res.drawable.test1),
            contentDescription = "Post",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        TextButton(
            onClick = { /* Acción al pulsar */ },
            modifier = Modifier.padding(start = 10.dp, top = 10.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
        ) {
            Image(
                painter = painterResource(Res.drawable.test1),
                contentDescription = "Perfil",
                modifier = Modifier.size(40.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text("@CasaPepeHito1"
                , color = AppColors.background
                ,fontWeight = FontWeight.Bold
                , fontSize = 16.sp)
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxWidth(0.4f)
                .padding(bottom = 10.dp, end = 20.dp)
                .height(40.dp)
                .background(AppColors.background, RoundedCornerShape(10.dp)),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Valoracion")
        }
    }
}