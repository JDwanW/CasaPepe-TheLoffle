
package com.example.theloffle.di
import com.example.theloffle.db.TheLoffleDatabase
import com.example.theloffle.features.inicio.data.AuthRepoImp
import com.example.theloffle.features.inicio.data.datasource.AuthLocalDataSource
import com.example.theloffle.features.inicio.domain.repository.AuthRepo
//PROXY
import com.example.theloffle.features.recetas.data.datasource.RecipeLocalDataSource
//MEAL REGISTER
import com.example.theloffle.features.diario.data.repository.MealRegisterRepoImp
import com.example.theloffle.features.diario.domain.repository.MealRegisterRepo
//RECIPE
import com.example.theloffle.features.recetas.data.repository.RecipeRepoImp
import com.example.theloffle.features.recetas.domain.repository.RecipeRepo
//SEARCH
import com.example.theloffle.features.buscador.data.repository.SearchRepoImp
import com.example.theloffle.features.buscador.domain.repository.SearchRepo
//PROFILE
import com.example.theloffle.features.perfil.data.repository.UserRepoImp
import com.example.theloffle.features.perfil.domain.repository.UserRepo

interface AppContainer {
    val authRepoInstance : AuthRepo
    val mealRegisterRepoInstance: MealRegisterRepo
    val recipeRepoInstance: RecipeRepo
    val searchRepoInstance: SearchRepo
    val profileRepoInstance: UserRepo
}

class DefaultAppContainer (private val database: TheLoffleDatabase? = null): AppContainer{

    override val authRepoInstance: AuthRepo by lazy{
        AuthRepoImp(database?.let { AuthLocalDataSource(it) })
    }
    override val mealRegisterRepoInstance: MealRegisterRepo by lazy{
        MealRegisterRepoImp()
    }
    override val recipeRepoInstance: RecipeRepo by lazy {
        RecipeRepoImp(database?.let { RecipeLocalDataSource(it) })
    }
    override val searchRepoInstance: SearchRepo by lazy {
        SearchRepoImp(recipeRepoInstance)
    }
    override val profileRepoInstance: UserRepo by lazy{
        UserRepoImp()
    }
}