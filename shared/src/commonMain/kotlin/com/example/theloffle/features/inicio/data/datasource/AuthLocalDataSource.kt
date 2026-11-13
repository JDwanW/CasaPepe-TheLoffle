package com.example.theloffle.features.inicio.data.datasource

import com.example.theloffle.db.TheLoffleDatabase
import com.example.theloffle.db.Users
import com.example.theloffle.features.inicio.domain.model.User

// CUIDADO!! Users: Tabla de la bbdd, User: Clase de la feature

class AuthLocalDataSource(
    private  val database: TheLoffleDatabase
) {
    // Fila de la base de datos -> modelo de la feature
    private fun toUser(fila: Users): User = User(fila.email, fila.password, fila.name, fila.surname, fila.username)

    // Insertar un usario
    fun insert(user: User) {
        database.userQueries.insertUser(
            email = user.normalizedEmail,
            password = user.password,
            username = user.username,
            name = user.name,
            surname = user.surname,
            profile_photo = null
        )
    }

    // Buscar por nombre de usuario
    fun findByUsername(username: String):
            User? = database.userQueries.selectForUsername(username).executeAsOneOrNull()?.let { toUser(it) }

    // Ver si existe el username
    fun usernameExists(username: String):
            Boolean = database.userQueries.selectForUsername(username).executeAsOneOrNull() != null

    // Buscar por email
    fun findByEmail(email : String):
            User? = database.userQueries.selectForEmail(email).executeAsOneOrNull()?.let { toUser(it) }

    // Ver si existe el email
    fun emailExists(email: String):
            Boolean = database.userQueries.selectForEmail(email).executeAsOneOrNull() != null

    // Si la bbdd esta vacio insertamos usuarios de prueba
    fun insertIfEmpty(users: List<User>) {
        if (database.userQueries.countUsers().executeAsOne() == 0L) {
            users.forEach { insert(it) }
        }
    }
}