package com.example.practicasandroidstudio.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Practica03Inicio(modifier: Modifier = Modifier) {

    var numero = remember { mutableStateOf("") }
    var cantidad = remember { mutableStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(text = "Practica 03")

        OutlinedTextField(
            value = numero.value,
            onValueChange = { numero.value = it },
            label = { Text("Ingresa un número") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        Text(text = "Resultado:")

        repeat(cantidad.value) {
            ImprimirCaracter("*")
        }

        Button(
            onClick = {
                cantidad.value = numero.value.toIntOrNull() ?: 0
            }
        ) {
            Text(text = "Imprimir")
        }
    }
}

@Composable
fun ImprimirCaracter(caracter: String) {
    Text(text = caracter)
}

@Preview(showBackground = true)
@Composable
fun InicioPreview() {
    PracticasAndroidStudioTheme {
        Practica03Inicio()
    }
}

@Composable
fun PracticasAndroidStudioTheme(content: @Composable () -> Unit) {
    TODO("Not yet implemented")
}