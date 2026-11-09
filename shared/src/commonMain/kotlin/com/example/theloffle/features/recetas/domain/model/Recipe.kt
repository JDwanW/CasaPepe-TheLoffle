package com.example.theloffle.features.recetas.domain.model

import kotlinx.serialization.Serializable

// Creamos la clase recetas
@Serializable
data class Recipe(
    val id: Int,
    val author: String,
    val name: String,
    val ingredients: List<String>,
    val tags: List<String>,
    val description: String? = null,
    val published: Boolean,
    val imageUrl: String? = null,
    val rating: Double? = if (published) 0.0 else null
)

