
package com.example.estadospracticas.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Tarea(
    val id: Int,
    val titulo: String,
    val prioridad: String,
    val hecha: Boolean = false
)

@Preview(showBackground = true)
@Composable
fun TareasScreen() {
    val tareas = remember { mutableStateListOf<Tarea>() }

    var texto by remember { mutableStateOf("") }
    var prioridad by remember { mutableStateOf("Media") }
    var filtro by remember { mutableStateOf("Todas") }
    var mostrarDialogo by remember { mutableStateOf(false) }
    var id by remember { mutableIntStateOf(1) }

    val pendientes = tareas.count { !it.hecha }

    val tareasVisibles = when (filtro) {
        "Pendientes" -> tareas.filter { !it.hecha }
        "Hechas" -> tareas.filter { it.hecha }
        else -> tareas.toList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Text("Gestor de tareas", fontSize = 24.sp)

        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it },
            label = { Text("Nueva tarea") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Prioridad", modifier = Modifier.padding(top = 10.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Alta", "Media", "Baja").forEach { opcion ->
                FilterChip(
                    selected = prioridad == opcion,
                    onClick = { prioridad = opcion },
                    label = { Text(opcion) }
                )
            }
        }

        Button(
            onClick = {
                tareas.add(Tarea(id, texto.trim(), prioridad))
                id++
                texto = ""
            },
            enabled = texto.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar tarea")
        }

        Text(
            "Filtrar",
            modifier = Modifier.padding(top = 15.dp)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Todas", "Pendientes", "Hechas").forEach { opcion ->
                FilterChip(
                    selected = filtro == opcion,
                    onClick = { filtro = opcion },
                    label = { Text(opcion) }
                )
            }
        }

        Text(
            "$pendientes pendientes de ${tareas.size}",
            modifier = Modifier.padding(vertical = 10.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(tareasVisibles, key = { it.id }) { tarea ->
                TareaItem(
                    tarea = tarea,
                    onCheckedChange = { marcada ->
                        val index = tareas.indexOfFirst { it.id == tarea.id }
                        if (index >= 0) {
                            tareas[index] = tareas[index].copy(hecha = marcada)
                        }
                    },
                    onDelete = {
                        tareas.remove(tarea)
                    }
                )
            }
        }

        OutlinedButton(
            onClick = { mostrarDialogo = true },
            enabled = tareas.any { it.hecha },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Borrar completadas")
        }
    }

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Eliminar tareas") },
            text = { Text("¿Deseas borrar las tareas completadas?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        tareas.removeAll { it.hecha }
                        mostrarDialogo = false
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogo = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun TareaItem(
    tarea: Tarea,
    onCheckedChange: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    val colorPrioridad = when (tarea.prioridad) {
        "Alta" -> Color.Red
        "Media" -> Color(0xFFE6A000)
        else -> Color.Green
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = tarea.hecha,
            onCheckedChange = onCheckedChange
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = tarea.titulo,
                textDecoration = if (tarea.hecha)
                    TextDecoration.LineThrough
                else
                    TextDecoration.None
            )

            Text(
                text = tarea.prioridad,
                fontSize = 12.sp,
                color = colorPrioridad
            )
        }

        IconButton(onClick = onDelete) {
            Text("X", color = Color.Red)
        }
    }
}
