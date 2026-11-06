import com.example.theloffle.features.recetas.data.repository.RecipeRepoImp
import com.example.theloffle.features.recetas.domain.repository.RecipeRepo
import com.example.theloffle.features.buscador.data.repository.SearchRepoImp
import com.example.theloffle.features.buscador.domain.repository.SearchRepo

interface AppContainer {
    val recipeRepoInstance: RecipeRepo
    val searchRepoInstance: SearchRepo
}

class DefaultAppContainer : AppContainer {

    override val recipeRepoInstance: RecipeRepo by lazy {
        RecipeRepoImp()
    }

    override val searchRepoInstance: SearchRepo by lazy {
        SearchRepoImp()
    }
}