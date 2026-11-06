package com.example.practicasandroidstudio.ui.theme.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.practicasandroidstudio.ui.theme.components.micard

@Composable
fun Practica04Inicio(modifier: Modifier = Modifier) {

    val nombres = listOf(
        "Said",
        "Alejandro",
        "Said",
        "Alejandro",
        "Said",
        "Alejandro",
        "Said",
        "Alejandro"
    )

    LazyColumn(
        modifier = modifier
    ) {
        items(nombres) { persona ->

            micard(
                nombre = persona,
                modifier = Modifier
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun InicioPreview() {
    PracticasAndroidStudioTheme {
        Practica04Inicio()
    }
}

@Composable
fun PracticasAndroidStudioTheme(content: @Composable () -> Unit) {
    TODO("Not yet implemented")
}