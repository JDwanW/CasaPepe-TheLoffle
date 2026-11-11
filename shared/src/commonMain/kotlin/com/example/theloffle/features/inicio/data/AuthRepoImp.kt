package com.example.theloffle.features.inicio.data

import com.example.theloffle.features.inicio.domain.repository.AuthRepo

class AuthRepoImp : AuthRepo {
    override suspend fun register(
        email: String,
        password: String,
        name: String,
        surname: String
    ): Boolean {
        return true
    }
}