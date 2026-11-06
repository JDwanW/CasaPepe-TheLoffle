package com.example.theloffle.features.recetas.domain.model

// Creamos la clase recetas
data class Recipes(
    val id: Int,
    val name: String,
    val author: String,
    val ingredients: List<Ingrediente>,
    val tags: List<String>,
    val description: String? = null,
    val published: Boolean,
    val imageUrl: String? = null
)
