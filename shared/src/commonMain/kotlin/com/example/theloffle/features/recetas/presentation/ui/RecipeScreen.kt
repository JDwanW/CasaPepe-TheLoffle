package com.example.theloffle.features.recetas.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theloffle.core.UI_System.components.Header

data class Carpeta(val nombre: String, val etiquetas: Set<String>)

private val filtros = listOf(
    "Española",
    "Italiana",
    "Asiática",
    "China",
    "Hamburguesa",
    "Pizza",
    "Bebidas",
    "Cocktails",
    "Postre",
    "Mexicano",
    "Carnes",
    "Pescados",
    "Vegetariano",
    "Vegano",
    "Sin Alergias",
    "Sin Gluten",
    "Horno",
    "AirFryer",
    "Parrilla",
    "Sartén"
)
private val grupos = listOf("Mis Recetas", "Mis Publicadas", "Mis Guardadas")
    .mapIndexed { g, nombre ->
        nombre to filtros.map { filtro -> Carpeta(nombre = filtro, etiquetas = setOf(filtro)) }
    }

@Composable
fun RecipeScreen(modifier: Modifier = Modifier) {
    val seleccionados = remember { mutableStateListOf<String>() }

    Column(
        modifier = modifier.fillMaxSize().padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Header()
        // Row for each type of saves
        grupos.forEach { (nombre, carpetas) -> FilaCarpetas(nombre, carpetas, seleccionados) }

        // Space between row 3 and 4
        Spacer(modifier = Modifier.height(24.dp))

        // Row for all the filters
        Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
            FlowRow(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                filtros.forEach { filtro ->
                    val activo = filtro in seleccionados
                    Button(
                        onClick = {
                            if (activo) seleccionados.remove(filtro) else seleccionados.add(filtro)
                        },
                        modifier = Modifier.height(44.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                        colors = if (activo) ButtonDefaults.buttonColors()
                        else ButtonDefaults.filledTonalButtonColors()
                    ) {
                        Text(filtro, fontSize = 16.sp, maxLines = 1)
                    }
                }
            }
        }
    }
}

@Composable
private fun FilaCarpetas(
    titulo: String,
    carpetas: List<Carpeta>,
    seleccionados: List<String>
) {
    var abierto by remember { mutableStateOf(false) }

    val visibles = if (seleccionados.isEmpty()) carpetas
    else carpetas.filter { c -> c.etiquetas.any { it in seleccionados } }

    Row(
        modifier = Modifier.fillMaxWidth().height(88.dp),   // antes 56.dp
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = { abierto = !abierto },
            modifier = Modifier.height(52.dp)
        ) {
            Text(titulo, fontSize = 16.sp)
        }

        if (abierto) {
            LazyRow(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(visibles) { carpeta ->
                    OutlinedButton(
                        onClick = { /* abrir carpeta */ },
                        modifier = Modifier.height(72.dp),
                        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 0.dp)
                    ) {
                        Text(carpeta.nombre, fontSize = 20.sp, maxLines = 1)
                    }
                }
            }
        }
    }
}