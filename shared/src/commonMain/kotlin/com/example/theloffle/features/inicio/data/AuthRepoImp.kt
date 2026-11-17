package com.example.theloffle.features.inicio.data

import androidx.compose.runtime.structuralEqualityPolicy
import com.example.theloffle.core.database.workDispatcher
import com.example.theloffle.features.inicio.data.datasource.AuthLocalDataSource
import com.example.theloffle.features.inicio.domain.model.User
import com.example.theloffle.features.inicio.domain.repository.AuthRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private val _currentUsername = MutableStateFlow<String?>(null)
    override val currentUsername: StateFlow<String?> = _currentUsername.asStateFlow()

    // para registrarse en el RegisterScreen
    override suspend fun register(
        email: String,
        password: String,
        name: String,
        surname: String,
        username: String
    ): Boolean {

        //este delay solo es para que se vea el circulo un poco
        delay(1500)
        val normalizedEmail = email.trim().lowercase()

        val success = if (localDataSource != null) {
             withContext(workDispatcher) {
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
        else{
            // Aqui no deberia llegar nunca, dejo lo que estaba porque no afecta
            // Lo dejo en vez de borrarlo y asi podriamos decir que tenemos un "seguro por si falla"
            val exists = users.any { user ->
                user.email == normalizedEmail || user.username == username
            }
            if (!exists) {
                users.add(
                    User(normalizedEmail, password, name, surname, username)
                )
                _currentUsername.value = username
                true
            }
            else {
                false
            }
        }

        if (success) {
            _isLoggedIn.value = true
            _currentUsername.value = username
        }

        return success

    }
    // para hacer el login en el LogIn-Screen
    override suspend fun login(
        email: String,
        password: String
    ): Boolean {

        //este delay solo es para que se vea el circulo un poco
        delay(1500)
        val normalizedEmail = email.trim().lowercase()

        // Comprueba que hay un usuario con ese email
        // Recordemos que el email es unico asi que solo devolveria un usuario o ninguno findByEmail
        val authenticatedUser = if (localDataSource != null) {
            withContext(workDispatcher) {
                localDataSource.insertIfEmpty(users)
                val user = localDataSource.findByEmail(normalizedEmail)
                if (user != null && user.normalizedEmail == normalizedEmail && user.password == password) {
                    user
                } else {
                    null
                }
            }
        } else {
            // Lo mismo lo dejo pero aqui pero aqui no llega nunca si va la bbdd
            users.find { user ->
                user.email.trim().lowercase() == normalizedEmail && user.password == password
            }
        }
        val success = authenticatedUser != null
        _isLoggedIn.value = success
        _currentUsername.value = authenticatedUser?.username
        return success
    }

    // para hacer el logout en settings
    override suspend fun logout (){
        _isLoggedIn.value = false
        _currentUsername.value = null
    }

    // para cargar los datos para el PersonalData Screen
    // tal vez luego cambiarlo al UserRepo? Tiene mas sentido imao
    // UserRepo trabaja con Mock Data Base
    override suspend fun getCurrentUser(): User? {
        val username = _currentUsername.value ?: return null

        return if (localDataSource != null) {
            withContext(workDispatcher) {
                localDataSource.findByUsername(username)
            }
        } else {
            users.find { it.username == username }
        }
    }
}
