package com.example.theloffle.features.recetas.domain.model

// Creamos la clase recetas
data class Receta(
    val id: Int,
    val nombre: String,
    val autor: String,
    val ingredientes: List<Ingrediente>,
    val utensilios: List<Utensilio>,
    val etiqueta: List<Etiqueta>,
    val pasos: String,
    val publicada: Boolean
)