package com.example.theloffle.features.inicio.data

import androidx.compose.runtime.structuralEqualityPolicy
import com.example.theloffle.features.inicio.domain.model.User
import com.example.theloffle.features.inicio.domain.repository.AuthRepo
import kotlinx.coroutines.delay


private val users = mutableListOf<User>(
    User("carlos7@gmx.net", "test123", "Carlos", "Rivera"),
    User("matthis05@gmx.net", "test123", "Matthis", "Woltsche"),
    User("jorge190@gmail.com", "test123", "Jorge", "Garcia"),
    User("rainerzu@icloud.com", "test123", "Rainer", "Zufall"),
    User("fixiehartrein45@yahoo.com", "test123", "Fixie", "Hartmann"),
    User("test", "test", "Theo", "Retisch")
)

class AuthRepoImp : AuthRepo {


    override suspend fun register(
        email: String,
        password: String,
        name: String,
        surname: String
    ): Boolean {

        //este delay solo es para que se vea el circulo un poco
        delay(2000)

        val normalizedEmail = email.trim().lowercase()

        val exists = users.any { user ->
            user.email == normalizedEmail
        }
        if (!exists) {
            users.add(
                User(normalizedEmail, password, name, surname)
            )
            return true
        }
        return false
    }

    override suspend fun login(
        email: String,
        password: String
    ): Boolean {

        //este delay solo es para que se vea el circulo un poco
        delay(2000)

        val normalizedEmail = email.trim().lowercase()

        val exists = users.any {user ->
            user.email == normalizedEmail && user.password == password
        }
        if (exists){
            return true
        }
        return false
    }
}
