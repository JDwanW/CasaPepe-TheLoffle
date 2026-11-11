package com.example.theloffle.features.inicio.data

import com.example.theloffle.features.inicio.domain.model.User
import com.example.theloffle.features.inicio.domain.repository.AuthRepo


private val users = mutableListOf<User>(
    User("carlos7@gmx.net", "test123", "Carlos", "Rivera"),
    User("matthis05@gmx.net", "test123", "Matthis", "Woltsche"),
    User("jorge190@gmail.com", "test123", "Jorge", "Garcia"),
    User("rainerzu@icloud.com", "test123", "Rainer", "Zufall"),
    User("fixiehartrein45@yahoo.com", "test123", "Fixie", "Hartmann")
)

class AuthRepoImp : AuthRepo {


    override suspend fun register(
        email: String,
        password: String,
        name: String,
        surname: String
    ): Boolean {

        val normalizedEmail = email.trim().lowercase()

        val exists = users.any{ user ->
            user.email == normalizedEmail
        }
        if (!exists){
            users.add(
                User(normalizedEmail, password, name, surname))
            return true
        }
        return false
    }
}