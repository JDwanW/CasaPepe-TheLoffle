package com.example.theloffle.db

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.io.File

fun createDataBase(): TheLoffleDatabase {
    // Creamos la ruta del archivo
    val file = File(System.getProperty("user.home"), ".theloffle/theloffle.db")
    val isNew = !file.exists()
    // Por si la carpeta no existe se crea
    file.parentFile.mkdirs()
    // Conexion, se crea el archivo si no existia
    val driver = JdbcSqliteDriver("jdbc:sqlite:${file.absolutePath}")
    if (isNew) TheLoffleDatabase.Schema.create(driver)
    return TheLoffleDatabase(driver)
}