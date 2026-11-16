package com.example.theloffle.features.inicio.data

import androidx.compose.runtime.structuralEqualityPolicy
import com.example.theloffle.core.UserSession
import com.example.theloffle.core.database.workDispatcher
import com.example.theloffle.features.inicio.data.datasource.AuthLocalDataSource
import com.example.theloffle.features.inicio.domain.model.User
import com.example.theloffle.features.inicio.domain.repository.AuthRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


private val users = mutableListOf<User>(
    User("carlos7@gmx.net", "test123", "Carlos", "Rivera", "Pala3"),
    User("matthis05@gmx.net", "test123", "Matthis", "Woltsche", "Matthix"),
    User("jorge190@gmail.com", "test123", "Jorge", "Garcia", "Jorgona"),
    User("rainerzu@icloud.com", "test123", "Rainer", "Zufall", "Raenstro"),
    User("fixiehartrein45@yahoo.com", "test123", "Fixie", "Hartmann", "Fiximann"),
    User("test", "test", "Theo", "Retisch", "Theorema")
)

class AuthRepoImp(
    private val localDataSource: AuthLocalDataSource? = null,
    private val session: UserSession
) : AuthRepo {

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

        if(localDataSource != null){
            return withContext(workDispatcher){
                // Cargamos si la bbdd vacio los ejemplos para poder probar (solo una vez como mucho)
                localDataSource.insertIfEmpty(users)
                if(localDataSource.emailExists(normalizedEmail) ||
                    localDataSource.usernameExists(username)){
                    false
                }
                else{
                    localDataSource.insert(User(normalizedEmail, password, name, surname, username))
                    session.open(username)
                    true
                }
            }
        }

        // Aqui no deberia llegar nunca, dejo lo que estaba porque no afecta
        // Lo dejo en vez de borrarlo y asi podriamos decir que tenemos un "seguro por si falla"
        val exists = users.any { user ->
            user.email == normalizedEmail || user.username == username}
        if (!exists) {
            users.add(
                User(normalizedEmail, password, name, surname, username)
            )
            session.open(username)
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
        if(localDataSource != null){
            return withContext(workDispatcher){
                // Cargamos si la bbdd vacio los ejemplos para poder probar
                localDataSource.insertIfEmpty(users)
                val user = localDataSource.findByEmail(normalizedEmail)
                if(user != null && user.email.trim().lowercase() == email.trim().lowercase() &&
                        user.password == password){
                    session.open(user.username)
                    true
                }
                else{
                    false
                }
            }
        }

        // Lo mismo lo dejo pero aqui pero aqui no llega nunca si va la bbdd (solo si hubiera web)
        val user = users.firstOrNull {it.email == normalizedEmail && it.password == password}
        if (user != null) {
            session.open(user.username)
            return true
        }
        return false
    }
}
