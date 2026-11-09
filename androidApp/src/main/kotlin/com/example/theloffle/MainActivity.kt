package com.example.theloffle

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.theloffle.notifications.Notification
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {

    private var toScreen by mutableStateOf<Route?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }

    override fun onStop(){
        super.onStop()
        Notification.scheduleNotification(
            this,
            "The Löffel",
            "Te estamos esperando",
            2,
            toScreen = Route.HomeScreen)
    }

    override fun onNewIntent(intent: Intent){
        super.onNewIntent(intent)
        toScreen =jsonToRoute(intent)
    }
    private fun jsonToRoute(intent : Intent): Route? =
        intent?.getStringExtra("EXTRA_TO_SCREEN")
            ?.let {runCatching { Json.decodeFromString<Route>(it) }.getOrNull()}
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}