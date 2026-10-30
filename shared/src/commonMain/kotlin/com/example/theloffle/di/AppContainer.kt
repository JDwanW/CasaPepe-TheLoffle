import com.example.theloffle.features.recetas.data.repository.UpdatedFilterImpl
import com.example.theloffle.features.recetas.domain.repository.UpdatedFilter

interface AppContainer {
    val updatedFilter: UpdatedFilter
    // aquí añades el resto de repositorios
}

class DefaultAppContainer : AppContainer {

    override val updatedFilter: UpdatedFilter by lazy {
        UpdatedFilterImpl()
    }
}