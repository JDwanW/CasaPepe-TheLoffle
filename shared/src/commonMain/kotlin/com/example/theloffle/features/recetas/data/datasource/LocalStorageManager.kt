import java.io.File
import java.util.UUID

class LocalStorageManager(private val context: Context) {

    fun saveImageToInternalStorage(imageBytes: ByteArray): String {
        // 1. Crear el directorio 'recipe_images' si no existe
        val directory = File(context.filesDir, "recipe_images")
        if (!directory.exists()) {
            directory.mkdirs()
        }

        // 2. Generar un nombre único para evitar sobrescribir otras fotos
        val fileName = "recipe_${UUID.randomUUID()}.jpg"
        val file = File(directory, fileName)

        // 3. Escribir los bytes en el archivo
        file.writeBytes(imageBytes)

        // 4. Devolver la ruta absoluta formateada como File URI para Coil
        return "file://${file.absolutePath}"
    }
}