package com.example.theloffle.features.inicio.data

import androidx.compose.runtime.structuralEqualityPolicy
import com.example.theloffle.core.database.workDispatcher
import com.example.theloffle.features.inicio.data.datasource.AuthLocalDataSource
import com.example.theloffle.features.inicio.domain.model.User
import com.example.theloffle.features.inicio.domain.repository.AuthRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow



private val users = mutableListOf<User>(
    User("carlos7@gmx.net", "test123", "Carlos", "Rivera", "Pala3"),
    User("matthis05@gmx.net", "test123", "Matthis", "Woltsche", "Matthix"),
    User("jorge190@gmail.com", "test123", "Jorge", "Garcia", "Jorgona"),
    User("rainerzu@icloud.com", "test123", "Rainer", "Zufall", "Raenstro"),
    User("fixiehartrein45@yahoo.com", "test123", "Fixie", "Hartmann", "Fiximann"),
    User("test", "test", "Theo", "Retisch", "Theorema")
)


class AuthRepoImp(
    private val localDataSource: AuthLocalDataSource? = null
) : AuthRepo {
    private val _isLoggedIn = MutableStateFlow(false)
    override val isLoggedIn = _isLoggedIn.asStateFlow()


    override suspend fun register(
        email: String,
        password: String,
        name: String,
        surname: String,
        username: String
    ): Boolean {

        //este delay solo es para que se vea el circulo un poco
        delay(2000)
        val normalizedEmail = email.trim().lowercase()

        if (localDataSource != null) {
            return withContext(workDispatcher) {
                // Cargamos si la bbdd vacio los ejemplos para poder probar (solo una vez como mucho)
                localDataSource.insertIfEmpty(users)
                if (localDataSource.emailExists(normalizedEmail) ||
                    localDataSource.usernameExists(username)
                ) {
                    false
                } else {
                    localDataSource.insert(User(normalizedEmail, password, name, surname, username))
                    true
                }
            }
        }

        // Aqui no deberia llegar nunca, dejo lo que estaba porque no afecta
        // Lo dejo en vez de borrarlo y asi podriamos decir que tenemos un "seguro por si falla"
        val exists = users.any { user ->
            user.email == normalizedEmail || user.username == username
        }
        if (!exists) {
            users.add(
                User(normalizedEmail, password, name, surname, username)
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

        // Comprueba que hay un usuario con ese email
        // Recordemos que el email es unico asi que solo devolveria un usuario o ninguno findByEmail
        val success = if (localDataSource != null) {
            withContext(workDispatcher) {
                // Cargamos si la bbdd vacio los ejemplos para poder probar
                localDataSource.insertIfEmpty(users)
                val user = localDataSource.findByEmail(normalizedEmail)
                user != null && user.email.trim().lowercase() == email.trim().lowercase() &&
                        user.password == password
            }
        } else {
            // Lo mismo lo dejo pero aqui pero aqui no llega nunca si va la bbdd
            users.any { user ->
                user.email.trim().lowercase() == normalizedEmail && user.password == password
            }
        }
        _isLoggedIn.value = success
        return success
    }

    
    override suspend fun logout (){
        _isLoggedIn.value = false
    }
}
