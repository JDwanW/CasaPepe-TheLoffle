package com.example.theloffle.features.recetas.data.datasource

import com.example.theloffle.features.recetas.domain.model.Recipe

object MockRecipeDataSource {
    val recipes: List<Recipe> = listOf(
        Recipe(
            id = 1,
            name = "Paella Valenciana",
            ingredients = listOf("ARROZ", "POLLO", "CONEJO"),
            tags = listOf("Española", "Sartén", "Carnes"),
            description = "Cocinar a fuego lento...",
            published = true,
            imageUrl = "https://picsum.photos/seed/paella/400/300"
        ),
        Recipe(
            id = 2,
            name = "Pizza Margherita",
            ingredients = listOf("HARINA", "TOMATE", "MOZZARELLA"),
            tags = listOf("Italiana", "Horno", "Vegetariano"),
            description = "Hornear a 250°C...",
            published = true,
            imageUrl = "https://picsum.photos/seed/pizza/400/300"
        ),
        Recipe(
            id = 3,
            name = "Hamburguesa Completa",
            ingredients = listOf("CARNE", "PAN", "QUESO"),
            tags = listOf("Hamburguesa", "Sartén", "Carnes"),
            description = "Asar la carne...",
            published = true,
            imageUrl = "https://picsum.photos/seed/burger/400/300"
        ),
        Recipe(
            id = 4,
            name = "Hamburguesa Completa1",
            ingredients = listOf("CARNE", "PAN", "QUESO"),
            tags = listOf("Hamburguesa", "Sartén", "Carnes"),
            description = "Asar la carne...",
            published = true,
            imageUrl = "https://picsum.photos/seed/burger/400/300"
        ),
        Recipe(
            id = 5,
            name = "Hamburguesa Completa2",
            ingredients = listOf("CARNE", "PAN", "QUESO"),
            tags = listOf("Hamburguesa", "Sartén", "Carnes"),
            description = "Asar la carne...",
            published = true,
            imageUrl = null
        ),
        Recipe(
            id = 6,
            name = "Hamburguesa Completa2",
            ingredients = listOf("CARNE", "PAN", "QUESO"),
            tags = listOf("Pizza"),
            description = "Asar la carne...",
            published = true,
            imageUrl = null,
            rating = 4.1
        ),
        Recipe(
            id = 7,
            name = "Hamburguesa Completa2",
            ingredients = listOf("CARNE", "PAN", "QUESO"),
            tags = listOf("Pizza"),
            description = "Asar la carne...",
            published = true,
            imageUrl = null,
            rating = 2.0
        ),
        Recipe(
            id = 8,
            name = "Hamburguesa Completa2",
            ingredients = listOf("CARNE", "PAN", "QUESO"),
            tags = listOf("Pizza"),
            description = "Asar la carne...",
            published = true,
            imageUrl = null,
            rating = 4.4
        ),

    )
}