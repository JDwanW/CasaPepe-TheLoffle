package com.example.theloffle.core.UI_System.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import theloffle.shared.generated.resources.OleoScriptSwashCaps_Bold
import theloffle.shared.generated.resources.OleoScriptSwashCaps_Regular
import theloffle.shared.generated.resources.Res
import theloffle.shared.generated.resources.logo100

@Composable
fun Header(
    onHomeClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val fontRegular = FontFamily(Font(Res.font.OleoScriptSwashCaps_Regular))
    val fontBold = FontFamily(Font(Res.font.OleoScriptSwashCaps_Bold))

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(top = 50.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(60.dp)
                .then(
                    if (onHomeClick != null) {
                        Modifier
                            .clip(CircleShape)
                            .clickable { onHomeClick() }
                    } else Modifier
                ),
            painter = painterResource(Res.drawable.logo100),
            contentDescription = "Logo The Loffel"
        )

        Spacer(Modifier.width(12.dp))

        Text(
            text = "The Löffel",
            fontSize = 30.sp,
            fontFamily = fontBold,
            modifier = if (onHomeClick != null) {
                Modifier.clickable { onHomeClick() }
            } else Modifier
        )
    }
}