
package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class Interlineado(val valor: Float) {
    COMPACTO(1.0f),
    NORMAL(1.3f),
    AMPLIO(1.6f)
}

@Composable
fun LecturaScreen() {

    var tamano by rememberSaveable { mutableFloatStateOf(18f) }
    var interlineado by rememberSaveable {
        mutableStateOf(Interlineado.NORMAL)
    }
    var modoOscuro by rememberSaveable { mutableStateOf(false) }
    var usaSerif by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Ajustes de lectura", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(20.dp))

        Text("Tamaño de letra: ${tamano.toInt()}")

        Slider(
            value = tamano,
            onValueChange = { tamano = it },
            valueRange = 14f..32f
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Interlineado")

        Interlineado.values().forEach { opcion ->
            Row {
                RadioButton(
                    selected = interlineado == opcion,
                    onClick = { interlineado = opcion }
                )
                Text(opcion.name)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row {
            Text("Modo oscuro")
            Spacer(modifier = Modifier.width(10.dp))
            Switch(
                checked = modoOscuro,
                onCheckedChange = { modoOscuro = it }
            )
        }

        Row {
            FilterChip(
                selected = !usaSerif,
                onClick = { usaSerif = false },
                label = { Text("Sans") }
            )

            Spacer(modifier = Modifier.width(8.dp))

            FilterChip(
                selected = usaSerif,
                onClick = { usaSerif = true },
                label = { Text("Serif") }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Vista previa", fontSize = 20.sp)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (modoOscuro) Color.DarkGray else Color.LightGray
                )
                .padding(16.dp)
        ) {
            Text(
                text = "Este es un ejemplo de texto para probar los ajustes de lectura.",
                fontSize = tamano.sp,
                lineHeight = (tamano * interlineado.valor).sp,
                fontFamily = if (usaSerif) FontFamily.Serif else FontFamily.SansSerif,
                color = if (modoOscuro) Color.White else Color.Black
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                tamano = 18f
                interlineado = Interlineado.NORMAL
                modoOscuro = false
                usaSerif = false
            }
        ) {
            Text("Restablecer")
        }
    }
}
