package com.example.theloffle.db

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

fun createDataBase(context: Context): TheLoffleDatabase{
    // Driver has Schema, Context (where it saves it) and the name
    val driver = AndroidSqliteDriver(TheLoffleDatabase.Schema, context, "theloffle.db")
    return TheLoffleDatabase(driver)
}