package com.example.theloffle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.theloffle.db.createDataBase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // Create the DataBase
        val database = createDataBase(this)
        setContent {
            App()
        }
    }

    override fun onStop(){
        super.onStop()

    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}