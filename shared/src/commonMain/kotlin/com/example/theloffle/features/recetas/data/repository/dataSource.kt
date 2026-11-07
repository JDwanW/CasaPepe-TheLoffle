package com.example.theloffle.features.recetas.data.repository

import com.example.theloffle.features.recetas.domain.model.Recipe

object MockRecipeDataSource {
    val recipes: List<Recipe> = listOf(
        Recipe(
            id = 1,
            name = "Paella Valenciana",
            author = "Chef Juan",
            ingredients = listOf("ARROZ", "POLLO", "CONEJO"),
            tags = listOf("Española", "Sartén", "Carnes"),
            description = "Cocinar a fuego lento...",
            published = true,
            imageUrl = "https://picsum.photos/seed/paella/400/300"
        ),
        Recipe(
            id = 2,
            name = "Pizza Margherita",
            author = "Chef Mario",
            ingredients = listOf("HARINA", "TOMATE", "MOZZARELLA"),
            tags = listOf("Italiana", "Horno", "Vegetariano"),
            description = "Hornear a 250°C...",
            published = true,
            imageUrl = "https://picsum.photos/seed/pizza/400/300"
        ),
        Recipe(
            id = 3,
            name = "Hamburguesa Completa",
            author = "Chef Alex",
            ingredients = listOf("CARNE", "PAN", "QUESO"),
            tags = listOf("Hamburguesa", "Sartén", "Carnes"),
            description = "Asar la carne...",
            published = true,
            imageUrl = "https://picsum.photos/seed/burger/400/300"
        ),
        Recipe(
            id = 4,
            name = "Hamburguesa Completa1",
            author = "Chef Alex",
            ingredients = listOf("CARNE", "PAN", "QUESO"),
            tags = listOf("Hamburguesa", "Sartén", "Carnes"),
            description = "Asar la carne...",
            published = true,
            imageUrl = "https://picsum.photos/seed/burger/400/300"
        ),
        Recipe(
            id = 5,
            name = "Hamburguesa Completa2",
            author = "Chef Alex",
            ingredients = listOf("CARNE", "PAN", "QUESO"),
            tags = listOf("Hamburguesa", "Sartén", "Carnes"),
            description = "Asar la carne...",
            published = true,
            imageUrl = null
        )

    )
}