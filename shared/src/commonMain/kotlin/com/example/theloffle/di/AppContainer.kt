import com.example.theloffle.features.diario.data.repository.MealRegisterRepoImp
import com.example.theloffle.features.diario.domain.repository.MealRegisterRepo
import com.example.theloffle.features.recetas.data.repository.RecipeRepoImp
import com.example.theloffle.features.recetas.domain.repository.RecipeRepo
import com.example.theloffle.features.buscador.data.repository.SearchRepoImp
import com.example.theloffle.features.buscador.domain.repository.SearchRepo


interface AppContainer {
    val mealRegisterRepoInstance: MealRegisterRepo
    val recipeRepoInstance: RecipeRepo
    val searchRepoInstance: SearchRepo
}

class DefaultAppContainer : AppContainer {
    override val mealRegisterRepoInstance: MealRegisterRepo by lazy{
        MealRegisterRepoImp()
    }
    override val recipeRepoInstance: RecipeRepo by lazy {
        RecipeRepoImp()
    }
    override val searchRepoInstance: SearchRepo by lazy {
        SearchRepoImp(recipeRepoInstance)
    }
}