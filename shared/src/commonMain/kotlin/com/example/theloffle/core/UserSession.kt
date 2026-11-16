package com.example.theloffle.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// En esta clase guardamos quien inicio sesion
class UserSession {
    // Teniendo una variable publica no modificable y una privada modificable
    // se consigue que todas las clases puedan ver sin modificar la variable publica
    // y que solo UserSession pueda modificar la parte privada
    private  val _current = MutableStateFlow<String?>(null)
    val current: StateFlow<String?> = _current.asStateFlow()

    fun open(username: String){
        _current.value = username
    }

    fun close(){
        _current.value = null
    }
}