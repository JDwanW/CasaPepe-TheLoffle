package com.example.theloffle.features.recetas.data.datasource

import com.example.theloffle.features.recetas.domain.model.Recipe

object MockRecipeDataSource {
    val recipes: List<Recipe> = listOf(
        Recipe(
            id = 1,
            author = "PepitoVAS",
            name = "Paella Valenciana",
            ingredients = listOf("ARROZ", "POLLO", "CONEJO"),
            tags = listOf("Española", "Sartén", "Carnes"),
            description = "Cocinar a fuego lento...",
            published = true,
            imageUrl = "https://picsum.photos/seed/paella/400/300"
        ),
        Recipe(
            id = 2,
            author = "PepitoVAS",
            name = "Pizza Margherita",
            ingredients = listOf("HARINA", "TOMATE", "MOZZARELLA"),
            tags = listOf("Italiana", "Horno", "Vegetariano"),
            description = "Hornear a 250°C...",
            published = true,
            imageUrl = "https://picsum.photos/seed/pizza/400/300"
        ),
        Recipe(
            id = 3,
            author = "Haxassse",
            name = "SmashBurguer XLL",
            ingredients = listOf("CARNE", "PAN", "QUESO"),
            tags = listOf("Hamburguesa", "Sartén", "Carnes"),
            description = "1. Dorar el pan.\n" + "2. Hacer la carne.\n" + "3. Montar",
            published = true,
            imageUrl = "https://picsum.photos/seed/burger/400/300"
        ),
        Recipe(
            id = 4,
            author = "PepitoVAS",
            name = "NO TEXT",
            ingredients = listOf("CARNE", "PAN", "QUESO"),
            tags = listOf("Hamburguesa", "Sartén", "Carnes"),
            description = null,
            published = true,
            imageUrl = "https://picsum.photos/seed/burger/400/300"
        ),
        Recipe(
            id = 5,
            author = "PepitoVAS",
            name = "FULL TEXT",
            ingredients = listOf("CARNE", "PAN", "QUESO"),
            tags = listOf("Hamburguesa", "Sartén", "Carnes"),
            description = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
            published = false,
            imageUrl = null
        ),
        Recipe(
            id = 6,
            author = "PepitoVAS",
            name = "Hamburguesa Completa2",
            ingredients = listOf("CARNE", "PAN", "QUESO"),
            tags = listOf("Pizza"),
            description = "Asar la carne...",
            published = true,
            imageUrl = "https://picsum.photos/seed/pizza/400/300",
            rating = 4.1
        ),
        Recipe(
            id = 7,
            author = "PepitoVAS",
            name = "Hamburguesa Completa2",
            ingredients = listOf("CARNE", "PAN", "QUESO"),
            tags = listOf("Pizza"),
            description = "Asar la carne...",
            published = true,
            imageUrl = "https://picsum.photos/seed/pizza/400/300",
            rating = 2.0
        ),
        Recipe(
            id = 8,
            author = "PepitoVAS",
            name = "Hamburguesa Completa2",
            ingredients = listOf("CARNE", "PAN", "QUESO"),
            tags = listOf("Pizza"),
            description = "Asar la carne...",
            published = true,
            imageUrl = "https://picsum.photos/seed/burger/400/300",
            rating = 4.4
        ),
        Recipe(
            id = 9,
            author = "PepitoVAS",
            name = "Hamburguesa Completa1",
            ingredients = listOf("CARNE", "PAN", "QUESO"),
            tags = listOf("Hamburguesa", "Sartén", "Carnes"),
            description = "Asar la carne...",
            published = true,
            imageUrl = "https://picsum.photos/seed/burger/400/300"
        ),
        Recipe(
            id = 10,
            author = "PepitoVAS",
            name = "Hamburguesa Completa1",
            ingredients = listOf("CARNE", "PAN", "QUESO"),
            tags = listOf("Hamburguesa", "Sartén", "Carnes"),
            description = "Asar la carne...",
            published = true,
            imageUrl = "https://picsum.photos/seed/burger/400/300"
        ),
        Recipe(
            id = 11,
            author = "PepitoVAS",
            name = "Hamburguesa Completa1",
            ingredients = listOf("CARNE", "PAN", "QUESO"),
            tags = listOf("Hamburguesa", "Sartén", "Carnes"),
            description = "Asar la carne...",
            published = true,
            imageUrl = "https://picsum.photos/seed/burger/400/300"
        ),
        Recipe(
            id = 12,
            author = "PepitoVAS",
            name = "Hamburguesa Completa1",
            ingredients = listOf("CARNE", "PAN", "QUESO"),
            tags = listOf("Hamburguesa", "Sartén", "Carnes"),
            description = "Asar la carne...",
            published = true,
            imageUrl = "https://picsum.photos/seed/burger/400/300"
        ),
        Recipe(
            id = 13,
            author = "PepitoVAS",
            name = "Hamburguesa Completa1",
            ingredients = listOf("CARNE", "PAN", "QUESO"),
            tags = listOf("Hamburguesa", "Sartén", "Carnes"),
            description = "Asar la carne...",
            published = true,
            imageUrl = "https://picsum.photos/seed/burger/400/300"
        ),
        Recipe(
            id = 14,
            author = "PepitoVAS",
            name = "Hamburguesa Completa1",
            ingredients = listOf("CARNE", "PAN", "QUESO"),
            tags = listOf("Hamburguesa", "Sartén", "Carnes"),
            description = "Asar la carne...",
            published = true,
            imageUrl = "https://picsum.photos/seed/burger/400/300"
        ),
        Recipe(
            id = 15,
            author = "PepitoVAS",
            name = "Hamburguesa Completa1",
            ingredients = listOf("CARNE", "PAN", "QUESO"),
            tags = listOf("Hamburguesa", "Sartén", "Carnes"),
            description = "Asar la carne...",
            published = true,
            imageUrl = "https://picsum.photos/seed/burger/400/300"
        ),

    )
}