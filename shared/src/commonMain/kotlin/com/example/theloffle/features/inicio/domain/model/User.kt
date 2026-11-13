package com.example.theloffle.features.inicio.domain.model

data class User (
    val email : String,
    val password : String,
    val name : String,
    val surname : String,

) {
    val normalizedEmail = email.trim().lowercase()
}