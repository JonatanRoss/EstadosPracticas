
package com.example.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun EscribirScreen() {

    var texto by remember { mutableStateOf("") }
    val listaTextos = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        TextField(
            value = texto,
            onValueChange = { texto = it },
            label = { Text("Escribe algo") }
        )

        Button(
            onClick = {
                if (texto.isNotEmpty()) {
                    listaTextos.add(texto)
                    texto = ""
                }
            }
        ) {
            Text("Agregar")
        }

        Column {
            listaTextos.forEach { elemento ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text(
                        text = elemento,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}