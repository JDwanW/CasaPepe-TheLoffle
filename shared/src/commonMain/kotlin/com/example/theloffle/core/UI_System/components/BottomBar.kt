package com.example.theloffle.core.UI_System.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import com.example.theloffle.AppPages
import theloffle.shared.generated.resources.Res
import theloffle.shared.generated.resources.main_filled
import theloffle.shared.generated.resources.main_unfilled
import theloffle.shared.generated.resources.profile_filled
import theloffle.shared.generated.resources.profile_unfilled
import theloffle.shared.generated.resources.recipe_filled
import theloffle.shared.generated.resources.recipe_unfilled
import theloffle.shared.generated.resources.search_filled
import theloffle.shared.generated.resources.search_unfilled
import com.example.theloffle.core.UI_System.theme.AppColors

// TODO Esto no es generico del todo porque le metemos info de la app
// hay que rehacer esto para las cosas de la app como que las paginas las pasemos como parametro
// desde fuera para que asi sea generico del todo.
@Composable
fun BottomBar(
    pageSeleccionada: AppPages,
    onPageClick: (AppPages) -> Unit,
    modifier: Modifier
){
    Box(
        modifier = modifier
            .fillMaxWidth(0.8f)
            .navigationBarsPadding() // Para el margen con el simbolo del sistema
            .height(60.dp)
            .background(AppColors.buttonPrimary, RoundedCornerShape(50.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CreateOptionButton(
                pageSeleccionada,
                AppPages.HOME,
                "Inicio",
                Res.drawable.main_filled,
                Res.drawable.main_unfilled,
                onPageClick
            )
            CreateOptionButton(
                pageSeleccionada,
                AppPages.RECIPE,
                "Recetas",
                Res.drawable.recipe_filled,
                Res.drawable.recipe_unfilled,
                onPageClick
            )
            CreateOptionButton(
                pageSeleccionada,
                AppPages.SEARCH,
                "Buscar",
                Res.drawable.search_filled,
                Res.drawable.search_unfilled,
                onPageClick
            )
            CreateOptionButton(
                pageSeleccionada,
                AppPages.PROFILE,
                "Perfil",
                Res.drawable.profile_filled,
                Res.drawable.profile_unfilled,
                onPageClick
            )
        }
    }
}

@Composable
fun CreateOptionButton(
    PageSelect : AppPages,
    typePage : AppPages,
    text : String,
    imageSelect : DrawableResource,
    imageNoSelect : DrawableResource,
    onPageClick : (AppPages) -> Unit
){
    Button(
        onClick = {
            onPageClick(typePage)
        },
        modifier = Modifier
            .width(70.dp)
            .height(45.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(50.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (typePage == PageSelect) AppColors.buttonSecondary
            else AppColors.buttonPrimary
        )
    ) {
        Image(
            painter = painterResource(
                if (typePage == PageSelect) imageSelect
                else imageNoSelect
            ),
            contentDescription = text,
            modifier = Modifier.size(45.dp)
        )
    }
}