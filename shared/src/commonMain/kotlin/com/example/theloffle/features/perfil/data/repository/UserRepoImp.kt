package com.example.theloffle.features.perfil.data.repository

import com.example.theloffle.features.perfil.data.datasource.MockUserDataSource
import com.example.theloffle.features.perfil.domain.repository.UserRepo
import com.example.theloffle.features.perfil.domain.model.User


class UserRepoImp : UserRepo {
    private val allUsers = MockUserDataSource.users

    override suspend fun getUserList(): List<User> {return allUsers}

}