package com.example.theloffle.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
// Para implementar el button
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
//
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import page.FoodPages
// Para todos los recursos
import theloffle.shared.generated.resources.Res
import theloffle.shared.generated.resources.breakfast
import theloffle.shared.generated.resources.lunch
import theloffle.shared.generated.resources.dinner
import theloffle.shared.generated.resources.snack
import theme.AppColors

@Composable
fun BloqueRegistro(
    modifier: Modifier = Modifier,
    onMealSelected: (FoodPages) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Color(0x00FFFFFF),                  //Random Color, Transparent
                RoundedCornerShape(10.dp)
            )
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    onMealSelected(FoodPages.BREAKFAST)
                },
                modifier = Modifier.size(70.dp),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.foodDone
                )
            ) {
                Image(
                    painter = painterResource(
                        Res.drawable.breakfast
                    ),
                    contentDescription = "Desayuno",
                    modifier = Modifier.size(70.dp)
                )
            }

            Button(
                onClick = {
                    onMealSelected(FoodPages.SNACK1)
                },
                modifier = Modifier.size(70.dp),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.foodMissed
                )
            ) {
                Image(
                    painter = painterResource(
                        Res.drawable.snack
                    ),
                    contentDescription = "Merienda",
                    modifier = Modifier.size(70.dp)
                )
            }

            Button(
                onClick = {
                    onMealSelected(FoodPages.LUNCH)
                },
                modifier = Modifier.size(70.dp),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.foodMissed
                )
            ) {
                Image(
                    painter = painterResource(
                        Res.drawable.lunch
                    ),
                    contentDescription = "Comida",
                    modifier = Modifier.size(70.dp)
                )
            }

            Button(
                onClick = {
                    onMealSelected(FoodPages.SNACK2)
                },
                modifier = Modifier.size(70.dp),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.foodMissed
                )
            ) {
                Image(
                    painter = painterResource(
                        Res.drawable.snack
                    ),
                    contentDescription = "Merienda",
                    modifier = Modifier.size(70.dp)
                )
            }

            Button(
                onClick = {
                    onMealSelected(FoodPages.DINNER)
                },
                modifier = Modifier.size(70.dp),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.foodMissed
                )
            ) {
                Image(
                    painter = painterResource(
                        Res.drawable.dinner
                    ),
                    contentDescription = "Cena",
                    modifier = Modifier.size(70.dp)
                )
            }
        }
    }
}
