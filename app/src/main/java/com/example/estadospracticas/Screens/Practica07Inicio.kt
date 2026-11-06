
package com.example.estadospracticas.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Practica07Inicio(modifier: Modifier = Modifier) {

    val calculadora = rememberSaveable(
        saver = listSaver<Calculadora, String>(
            save = { it.guardar() },
            restore = {
                Calculadora().apply {
                    restaurar(it)
                }
            }
        )
    ) {
        Calculadora()
    }

    var resultado by rememberSaveable {
        mutableStateOf(calculadora.texto)
    }

    val botones = listOf(
        listOf("AC", "<", "%", "÷"),
        listOf("7", "8", "9", "x"),
        listOf("4", "5", "6", "-"),
        listOf("1", "2", "3", "+"),
        listOf("±", "0", ".", "=")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(8.dp)
    ) {

        Text(
            text = "Calculadora",
            fontSize = 24.sp,
            color = Color.Black
        )

        Box(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
                .padding(8.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = resultado,
                fontSize = 35.sp,
                color = Color.Black
            )
        }

        Column(
            modifier = Modifier
                .weight(5f)
                .fillMaxWidth()
        ) {

            botones.forEach { fila ->

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {

                    fila.forEach { boton ->

                        Button(
                            onClick = {
                                calculadora.pulsar(boton)
                                resultado = calculadora.texto
                            },
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .padding(3.dp)
                        ) {
                            Text(
                                text = boton,
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Practica07Preview() {
    Practica07Inicio()
}
