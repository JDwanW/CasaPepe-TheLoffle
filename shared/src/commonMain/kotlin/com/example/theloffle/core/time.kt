package com.example.theloffle.core

import kotlin.time.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

// Utilizamos la libreria datetime para guardar la fecha en texto
fun todayAsText(): String =
    Clock.System.todayIn(TimeZone.currentSystemDefault()).toString()