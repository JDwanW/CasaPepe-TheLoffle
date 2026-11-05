package com.example.theloffle.features.publicaciones.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.theloffle.core.UI_System.theme.AppColors
import theloffle.shared.generated.resources.Res
import theloffle.shared.generated.resources.test1

@Composable
fun ScrollBlock(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .background(AppColors.background)
            .padding(bottom = 16.dp)
    ) {
        repeat(30) { index ->
            PostBlock(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                userName = "CasaPepeHito1",
                userImage = Res.drawable.test1,
                postImage = Res.drawable.test1,
                postRating = "4,5"
            )
        }
    }
}