
package com.example.estadospracticas.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun ResenaScreen() {
    var comida by remember { mutableIntStateOf(0) }
    var servicio by remember { mutableIntStateOf(0) }
    var ambiente by remember { mutableIntStateOf(0) }
    var comentario by remember { mutableStateOf("") }
    var etiquetas by remember { mutableStateOf(setOf<String>()) }
    var publicada by remember { mutableStateOf(false) }

    val general = if (comida > 0 && servicio > 0 && ambiente > 0) {
        (comida + servicio + ambiente) / 3.0
    } else {
        null
    }

    val puedePublicar = general != null && comentario.trim().length >= 20

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Reseña de restaurante", fontSize = 24.sp)

        Text("Califica tu experiencia")

        Text("Comida")
        RatingBar(
            rating = comida,
            onRatingChange = {
                comida = it
                publicada = false
            }
        )

        Text("Servicio")
        RatingBar(
            rating = servicio,
            onRatingChange = {
                servicio = it
                publicada = false
            }
        )

        Text("Ambiente")
        RatingBar(
            rating = ambiente,
            onRatingChange = {
                ambiente = it
                publicada = false
            }
        )

        Text(
            text = "Calificación general: " +
                    if (general != null) {
                        "${"%.1f".format(general)} / 5"
                    } else {
                        "Sin calificar"
                    },
            fontSize = 18.sp
        )

        Text("Comentario")

        ComentarioField(
            texto = comentario,
            onChange = {
                comentario = it
                publicada = false
            }
        )

        Text("Etiquetas")

        val opciones = listOf(
            "Familiar",
            "Romántico",
            "Económico",
            "Vegetariano",
            "Rápido"
        )

        opciones.chunked(2).forEach { fila ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                fila.forEach { etiqueta ->
                    FilterChip(
                        selected = etiqueta in etiquetas,
                        onClick = {
                            etiquetas = if (etiqueta in etiquetas) {
                                etiquetas - etiqueta
                            } else {
                                etiquetas + etiqueta
                            }
                            publicada = false
                        },
                        label = { Text(etiqueta) }
                    )
                }
            }
        }

        Button(
            onClick = { publicada = true },
            enabled = puedePublicar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Publicar reseña")
        }

        if (publicada && general != null) {
            ResumenResena(
                general = general,
                comentario = comentario,
                etiquetas = etiquetas
            )
        }
    }
}

@Composable
fun RatingBar(
    rating: Int,
    onRatingChange: (Int) -> Unit
) {
    val descripcion = when (rating) {
        1 -> "Malo"
        2 -> "Regular"
        3 -> "Bueno"
        4 -> "Muy bueno"
        5 -> "Excelente"
        else -> "Sin calificar"
    }

    Row {
        for (i in 1..5) {
            Text(
                text = if (i <= rating) "★" else "☆",
                fontSize = 30.sp,
                color = if (i <= rating) {
                    Color(0xFFE3A008)
                } else {
                    Color.Gray
                },
                modifier = Modifier
                    .clickable {
                        onRatingChange(i)
                    }
                    .padding(end = 5.dp)
            )
        }
    }

    Text(
        text = descripcion,
        fontSize = 13.sp,
        color = Color.Gray
    )
}

@Composable
fun ComentarioField(
    texto: String,
    onChange: (String) -> Unit,
    maximo: Int = 200
) {
    OutlinedTextField(
        value = texto,
        onValueChange = {
            if (it.length <= maximo) {
                onChange(it)
            }
        },
        label = { Text("Escribe tu comentario") },
        modifier = Modifier.fillMaxWidth(),
        minLines = 3,
        maxLines = 5
    )

    Text(
        text = "${texto.length}/$maximo",
        color = if (texto.length >= 180) {
            Color.Red
        } else {
            Color.Gray
        },
        fontSize = 12.sp
    )
}

@Composable
fun ResumenResena(
    general: Double,
    comentario: String,
    etiquetas: Set<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Reseña publicada", fontSize = 20.sp)

            Text("Calificación: ${"%.1f".format(general)} / 5")

            Text("Comentario: $comentario")

            Text(
                "Etiquetas: " +
                        if (etiquetas.isEmpty()) {
                            "Ninguna"
                        } else {
                            etiquetas.joinToString(", ")
                        }
            )
        }
    }
}
