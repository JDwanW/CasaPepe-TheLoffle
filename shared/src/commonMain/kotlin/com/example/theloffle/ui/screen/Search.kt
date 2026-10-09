package com.example.theloffle.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.theloffle.ui.components.Header
import com.example.theloffle.ui.components.SearchBlock

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    Column (
        modifier = Modifier
            .padding(top = 20.dp)
            .padding(horizontal = 26.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ){
        Header()
        SearchBlock()
    }
}