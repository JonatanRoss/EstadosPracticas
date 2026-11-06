package com.example.practicasandroidstudio.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun Practica06Inicio(modifier: Modifier = Modifier) {

    var nombreColor by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    val colores = remember {
        mutableStateListOf<Color>()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(text = "Lista de colores")

        OutlinedTextField(
            value = nombreColor,
            onValueChange = { nombreColor = it },
            label = { Text("Ingresa un color") },
            modifier = Modifier.fillMaxWidth()
        )

        if (mensaje.isNotEmpty()) {
            Text(
                text = mensaje,
                color = Color.Red
            )
        }

        Button(
            onClick = {
                val colorSeleccionado = obtenerColor(nombreColor)

                if (colorSeleccionado != null) {
                    colores.add(colorSeleccionado)
                    nombreColor = ""
                    mensaje = ""
                } else {
                    mensaje = "Color no válido"
                }
            }
        ) {
            Text(text = "Agregar")
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(colores) { color ->
                MostrarColor(color)
            }
        }
    }
}

@Composable
fun MostrarColor(color: Color) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(color)
    )
}

fun obtenerColor(nombre: String): Color? {

    return when (nombre.trim().lowercase()) {
        "rojo" -> Color.Red
        "azul" -> Color.Blue
        "verde" -> Color.Green
        "amarillo" -> Color.Yellow
        "negro" -> Color.Black
        "blanco" -> Color.White
        "gris" -> Color.Gray
        "cyan" -> Color.Cyan
        "magenta" -> Color.Magenta
        else -> null
    }
}

@Preview(showBackground = true)
@Composable
fun InicioPreview() {
    PracticasAndroidStudioTheme {
        Practica06Inicio()
    }
}

@Composable
fun PracticasAndroidStudioTheme(content: @Composable () -> Unit) {
    TODO("Not yet implemented")
}