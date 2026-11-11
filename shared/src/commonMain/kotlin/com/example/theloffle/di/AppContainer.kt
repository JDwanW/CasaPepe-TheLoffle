import com.example.theloffle.features.diario.data.repository.MealRegisterRepoImp
import com.example.theloffle.features.diario.domain.repository.MealRegisterRepo
import com.example.theloffle.features.recetas.data.repository.RecipeRepoImp
import com.example.theloffle.features.recetas.domain.repository.RecipeRepo
import com.example.theloffle.features.buscador.data.repository.SearchRepoImp
import com.example.theloffle.features.buscador.domain.repository.SearchRepo
import com.example.theloffle.db.TheLoffleDatabase
import com.example.theloffle.features.inicio.data.AuthRepoImp
import com.example.theloffle.features.inicio.domain.repository.AuthRepo
import com.example.theloffle.features.recetas.data.datasource.RecipeLocalDataSource

interface AppContainer {
    val authRepoInstance : AuthRepo
    val mealRegisterRepoInstance: MealRegisterRepo
    val recipeRepoInstance: RecipeRepo
    val searchRepoInstance: SearchRepo
}

class DefaultAppContainer (private val database: TheLoffleDatabase? = null): AppContainer{

    override val authRepoInstance: AuthRepo by lazy{
        AuthRepoImp()
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
}