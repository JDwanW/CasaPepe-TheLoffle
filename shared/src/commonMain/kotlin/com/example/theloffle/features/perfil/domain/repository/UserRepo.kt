package com.example.theloffle.features.perfil.domain.repository

import com.example.theloffle.features.perfil.domain.model.User

interface UserRepo {
    suspend fun getUserList(): List<User>
    suspend fun getUserByUsername(username: String): User?
}