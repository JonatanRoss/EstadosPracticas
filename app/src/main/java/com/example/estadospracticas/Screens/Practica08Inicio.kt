package com.example.practicasandroidstudio.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.practicasandroidstudio.ui.theme.components.micard

@Composable
fun Practica08Inicio(modifier: Modifier = Modifier) {

    val listaNombres = remember {
        mutableStateListOf<String>()
    }

    var nombreEscrito by rememberSaveable {
        mutableStateOf("")
    }

    var posicion by rememberSaveable {
        mutableIntStateOf(-1)
    }

    Column(modifier = modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.White)
                .padding(10.dp)
        ) {

            TextField(
                value = nombreEscrito,
                onValueChange = { nombreEscrito = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {

                    if (nombreEscrito.isNotBlank()) {

                        if (posicion == -1) {
                            listaNombres.add(nombreEscrito)
                        } else {
                            listaNombres[posicion] = nombreEscrito
                            posicion = -1
                        }

                        nombreEscrito = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (posicion == -1) "Agregar" else "Guardar")
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.White)
        ) {

            itemsIndexed(listaNombres) { indice, nombre ->

                micard(
                    nombre = nombre,

                    eliminar = {
                        listaNombres.removeAt(indice)

                        if (posicion == indice) {
                            posicion = -1
                            nombreEscrito = ""
                        } else if (posicion > indice) {
                            posicion--
                        }
                    },

                    actualizar = {
                        nombreEscrito = nombre
                        posicion = indice
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InicioPreview() {
    PracticasAndroidStudioTheme {
        Practica08Inicio()
    }
}

@Composable
fun PracticasAndroidStudioTheme(content: @Composable () -> Unit) {
    TODO("Not yet implemented")
}