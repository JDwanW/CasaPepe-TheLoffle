package com.example.theloffle.features.inicio.domain.repository

import com.example.theloffle.features.inicio.domain.model.User
import kotlinx.coroutines.flow.StateFlow

interface AuthRepo {
    val isLoggedIn: StateFlow<Boolean>
    val currentUsername: StateFlow<String?>
    suspend fun getCurrentUser(): User?
    suspend fun register (email:String, password:String, name:String, surname:String, username:String): Boolean
    suspend fun login (email:String, password:String):Boolean
    suspend fun logout (): Unit
}