package com.example.theloffle.features.recetas.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.theloffle.features.recetas.data.repository.UpdatedFilterImpl
import com.example.theloffle.features.recetas.domain.repository.UpdatedFilter

import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card

data class File(val name: String, val filter: Set<String>)

@Composable
fun RecipeScreen(
    modifier: Modifier = Modifier,
    repository : UpdatedFilter
    ) {
    val selected = remember { mutableStateListOf<String>() }
    var filters by remember { mutableStateOf<List<String>>(emptyList()) }
    val scope = rememberCoroutineScope()

    // null = show the filters, not null = show the recipes of that file
    var openedFile by remember { mutableStateOf<File?>(null) }
    var recipes by remember { mutableStateOf<List<String>>(emptyList()) }

    LaunchedEffect(repository) { filters = repository.getFilterList() }

    val groups = remember(filters) {
        val files = filters.map { File(name = it, filter = setOf(it)) }
        listOf("Mis Recetas", "Mis Publicadas", "Mis Guardadas")
            .map { name -> name to files }
    }

    Column(
        modifier = modifier.fillMaxSize().padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Header()

        groups.forEach { (name, files) ->
            createFilesButtons(
                fileName = name,
                files = files,
                selected = selected,
                onFileClick = { file ->
                    openedFile = file
                    scope.launch {
                        repository.applyFilterOnFile(file.name)
                        recipes = repository.getRecipesInFile(file.name)
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Block: filters by default, recipes of the opened file otherwise
        Column(modifier = Modifier.weight(1f).fillMaxWidth()) {
            val file = openedFile
            if (file == null) {
                FlowRow(
                    modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    filters.forEach { filtro ->
                        val activo = filtro in selected
                        Button(
                            onClick = {
                                scope.launch {
                                    val nowActive = repository.toggleFilter(filtro)
                                    if (nowActive) selected.add(filtro) else selected.remove(filtro)
                                }
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
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(onClick = { openedFile = null }) { Text("← Filtros") }
                    Text(file.name, fontSize = 20.sp)
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.84f),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(top = 20.dp)
                ) {
                    items(recipes) { recipe ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Text(recipe, modifier = Modifier.padding(16.dp), fontSize = 18.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun createFilesButtons(
    fileName: String,
    files: List<File>,
    selected: List<String>,
    onFileClick: (File) -> Unit
) {
    var opened by remember { mutableStateOf(false) }

    val visible = if (selected.isEmpty()) files
    else files.filter { c -> c.filter.any { it in selected } }

    Row(
        modifier = Modifier.fillMaxWidth().height(88.dp),   // antes 56.dp
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(onClick = { opened = !opened }, modifier = Modifier.height(52.dp)) {
            Text(fileName, fontSize = 16.sp)
        }

        if (opened) {
            LazyRow(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(visible) { file ->
                    OutlinedButton(
                        onClick = { onFileClick(file) },
                        modifier = Modifier.height(72.dp),
                        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 0.dp)
                    ) {
                        Text(file.name, fontSize = 20.sp, maxLines = 1)
                    }
                }
            }
        }
    }
}