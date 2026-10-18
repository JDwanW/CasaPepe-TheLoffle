package com.example.theloffle.features.diario.presentation.ui

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
import com.example.theloffle.features.diario.domain.FoodPages
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import theloffle.shared.generated.resources.Res
import theloffle.shared.generated.resources.breakfast
import theloffle.shared.generated.resources.dinner
import theloffle.shared.generated.resources.lunch
import theloffle.shared.generated.resources.snack

@Composable
fun BloqueRegistro(
    modifier: Modifier = Modifier.Companion,
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
            //Todo : need a boolean to represent if meal is registered
            CreateRegisterButton(
                FoodPages.BREAKFAST,
                "Desayuno",
                Res.drawable.breakfast,
                onMealSelected
            );
            CreateRegisterButton(
                FoodPages.SNACK1,
                "Merienda 1",
                Res.drawable.snack,
                onMealSelected
            );
            CreateRegisterButton(FoodPages.LUNCH, "Comida", Res.drawable.lunch, onMealSelected);
            CreateRegisterButton(
                FoodPages.SNACK2,
                "Merienda 2",
                Res.drawable.snack,
                onMealSelected
            );
            CreateRegisterButton(FoodPages.DINNER, "Cena", Res.drawable.dinner, onMealSelected);
        }
    }
}

@Composable
fun CreateRegisterButton(
    typeMeal : FoodPages,
    text : String,
    image : DrawableResource,
    onMealSelected : (FoodPages) -> Unit
){
    Button(
        onClick = { onMealSelected(typeMeal) },
        modifier = Modifier.size(70.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(0.dp),
        //Todo : color depend on var bool
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.foodDone
        )
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = text,
            modifier = Modifier.size(70.dp)
        )
    }
}