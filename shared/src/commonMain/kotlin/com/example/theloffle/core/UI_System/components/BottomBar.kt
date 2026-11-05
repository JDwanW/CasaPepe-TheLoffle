package com.example.theloffle.core.UI_System.components

import com.example.theloffle.Route
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.theloffle.core.UI_System.theme.AppColors
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
data class BottomBarItem(
    val route: Route,
    val label: String,
    val iconSelected: DrawableResource,
    val iconUnselected: DrawableResource
)
@Composable
fun BottomBar(
    buttons: List<BottomBarItem>,
    isSelected: (Route) -> Boolean,
    onItemClick: (Route) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .height(60.dp)
            .background(AppColors.buttonPrimary),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            buttons.forEach { item ->
                CreateOptionButton(
                    selected = isSelected(item.route),
                    item = item,
                    onClick = { onItemClick(item.route) }
                )
            }
        }
    }
}

@Composable
private fun CreateOptionButton(
    selected: Boolean,
    item: BottomBarItem,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(70.dp)
            .height(45.dp),
        shape = RoundedCornerShape(50.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) AppColors.buttonSecondary
            else AppColors.buttonPrimary
        )
    ) {
        Image(
            painter = painterResource(
                if (selected) item.iconSelected else item.iconUnselected
            ),
            contentDescription = item.label,
            modifier = Modifier.size(45.dp)
        )
    }
}