package com.example.practicasandroidstudio.ui.theme.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp


@Composable
fun Practica01Inicio(modifier: Modifier = Modifier) {

    var contador = remember { mutableStateOf(0) }

    Column(modifier = modifier) {

        Button(
            onClick = {
                contador.value++
            }
        ) {
            Text(text = "Sumar")
        }

        Text(
            text = "${contador.value}",
            fontSize = 50.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InicioPreview() {
    PracticasAndroidStudioTheme {
        Practica01Inicio()
    }
}

@Composable
fun PracticasAndroidStudioTheme(content: @Composable () -> Unit) {
    TODO("Not yet implemented")
}