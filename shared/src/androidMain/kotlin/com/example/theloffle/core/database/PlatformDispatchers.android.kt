package com.example.theloffle.core.database

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual val workDispatcher: CoroutineDispatcher = Dispatchers.IO
