package com.example.theloffle

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.theloffle.db.createDataBase

fun main() {
    // Creamos la bbdd y se la pasamos a App
    val database = createDataBase()
    application {
        Window(onCloseRequest = ::exitApplication, title = "TheLoffle") {
            App(database)
        }
    }
}