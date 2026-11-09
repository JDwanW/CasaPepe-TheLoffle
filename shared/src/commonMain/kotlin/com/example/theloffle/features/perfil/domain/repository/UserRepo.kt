package com.example.theloffle.features.perfil.domain.repository

import com.example.theloffle.features.perfil.domain.model.User

interface UserRepo {
    //just to try
    suspend fun getUserList(): List<User>
}