package com.example.theloffle.features.inicio.domain.repository

interface AuthRepo {
    suspend fun register (email:String, password:String, name:String, surname:String): Boolean

    suspend fun login (email:String, password:String):Boolean
}