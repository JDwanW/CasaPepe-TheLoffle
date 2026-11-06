package com.example.theloffle.features.recetas.domain.model

// Creamos la clase recetas
data class Receta(
    val id: Int,
    val nombre: String,
    val autor: String,
    val ingredientes: List<Ingrediente>,
    val etiqueta: List<Etiqueta>,
    val pasos: String,
    val publicada: Boolean,
    val imagenUrl: String? = null
)