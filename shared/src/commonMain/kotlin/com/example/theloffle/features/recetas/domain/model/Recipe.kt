package com.example.theloffle.features.recetas.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

// Creamos la clase recetas
@Serializable
data class Recipe(
    val id: Int,
    val name: String,
    val author: String,
    val ingredients: List<Ingrediente>,
    val tags: List<String>,
    val description: String? = null,
    val published: Boolean,
    val imageUrl: String? = null
)
/* Json example
val jsonRecipe = Json.encodeToString(Recipe(2, "Name",
                                        "Author", listOf(), listOf(),
    null, false,null))
*/