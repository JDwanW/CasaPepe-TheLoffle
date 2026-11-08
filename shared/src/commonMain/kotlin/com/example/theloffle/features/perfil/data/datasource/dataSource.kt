package com.example.theloffle.features.perfil.data.datasource

import com.example.theloffle.features.diario.domain.model.FoodPages
import com.example.theloffle.features.perfil.domain.model.User

object MockUserDataSource {
    val users: List<User> = listOf(
        User(
            id = 0,
            username = "Ppe",
            password = "123asd123",
            numberOfMeal= listOf(FoodPages.BREAKFAST, FoodPages.LUNCH),
            profilePhoto = "",
            recipeList = emptyList(),
        )
    )
}
