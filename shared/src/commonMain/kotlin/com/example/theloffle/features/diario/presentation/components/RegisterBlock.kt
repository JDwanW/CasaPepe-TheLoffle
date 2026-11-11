package com.example.theloffle.features.diario.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.theloffle.core.UI_System.theme.AppColors
import com.example.theloffle.features.diario.domain.model.FoodPages
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import theloffle.shared.generated.resources.Res
import theloffle.shared.generated.resources.breakfast
import theloffle.shared.generated.resources.dinner
import theloffle.shared.generated.resources.lunch
import theloffle.shared.generated.resources.snack

@Composable
fun RegisterBlock(
    registeredMeals: Set<FoodPages>,
    onMealClick: (FoodPages) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Color.Transparent,
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
            val meals = listOf(
                Triple(FoodPages.BREAKFAST, "Desayuno", Res.drawable.breakfast),
                Triple(FoodPages.SNACK1, "Merienda 1", Res.drawable.snack),
                Triple(FoodPages.LUNCH, "Comida", Res.drawable.lunch),
                Triple(FoodPages.SNACK2, "Merienda 2", Res.drawable.snack),
                Triple(FoodPages.DINNER, "Cena", Res.drawable.dinner)
            )

            meals.forEach { (typeMeal, text, image) ->
                val isDone = registeredMeals.contains(typeMeal)

                CreateRegisterButton(
                    typeMeal = typeMeal,
                    text = text,
                    image = image,
                    isDone = isDone,
                    onMealSelected = onMealClick
                )
            }
        }
    }
}

@Composable
private fun CreateRegisterButton(
    typeMeal: FoodPages,
    text: String,
    image: DrawableResource,
    isDone: Boolean,
    onMealSelected: (FoodPages) -> Unit
) {
    Button(
        onClick = { onMealSelected(typeMeal) },
        modifier = Modifier.size(70.dp),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isDone) AppColors.foodDone else AppColors.buttonPrimary
        )
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = text,
            modifier = Modifier.size(70.dp)
        )
    }
}