package com.example.practicasandroidstudio.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Practica02Inicio(modifier: Modifier = Modifier) {

    var numero1 = remember { mutableStateOf("") }
    var numero2 = remember { mutableStateOf("") }
    var resultado = remember { mutableStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(text = "Suma de dos números")

        OutlinedTextField(
            value = numero1.value,
            onValueChange = { numero1.value = it },
            label = { Text("Número 1") }
        )

        OutlinedTextField(
            value = numero2.value,
            onValueChange = { numero2.value = it },
            label = { Text("Número 2") }
        )

        Text(text = "Resultado: ${resultado.value}")

        Button(
            onClick = {
                val n1 = numero1.value.toIntOrNull() ?: 0
                val n2 = numero2.value.toIntOrNull() ?: 0

                resultado.value = n1 + n2
            }
        ) {
            Text(text = "Sumar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InicioPreview() {
    PracticasAndroidStudioTheme {
        Practica02Inicio()
    }
}

@Composable
fun PracticasAndroidStudioTheme(content: @Composable () -> Unit) {
    TODO("Not yet implemented")
}