
package com.example.estadospracticas.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class PreguntaQuiz(
    val texto: String,
    val opciones: List<String>,
    val correcta: Int
)

enum class PantallaQuiz {
    INICIO, QUIZ, RESULTADO
}

@Preview(showBackground = true)
@Composable
fun QuizApp() {
    val preguntas = remember {
        listOf(
            PreguntaQuiz(
                "¿Qué palabra permite declarar una variable modificable en Kotlin?",
                listOf("val", "var", "fun", "class"),
                1
            ),
            PreguntaQuiz(
                "¿Qué estructura de datos utiliza LIFO?",
                listOf("Cola", "Pila", "Arreglo", "Lista"),
                1
            ),
            PreguntaQuiz(
                "¿Qué estructura permite evaluar una condición?",
                listOf("if", "for", "fun", "val"),
                0
            ),
            PreguntaQuiz(
                "¿Qué palabra se utiliza para declarar funciones en Kotlin?",
                listOf("function", "void", "fun", "method"),
                2
            ),
            PreguntaQuiz(
                "¿Qué función permite recordar un estado en Jetpack Compose?",
                listOf("remember", "println", "import", "override"),
                0
            )
        )
    }

    var pantalla by rememberSaveable { mutableStateOf(PantallaQuiz.INICIO) }
    var nombre by rememberSaveable { mutableStateOf("") }
    var indicePregunta by rememberSaveable { mutableIntStateOf(0) }
    var seleccionada by rememberSaveable { mutableStateOf<Int?>(null) }
    var respondida by rememberSaveable { mutableStateOf(false) }
    var puntaje by rememberSaveable { mutableIntStateOf(0) }

    fun reiniciar(borrarNombre: Boolean) {
        indicePregunta = 0
        seleccionada = null
        respondida = false
        puntaje = 0

        if (borrarNombre) {
            nombre = ""
            pantalla = PantallaQuiz.INICIO
        } else {
            pantalla = PantallaQuiz.QUIZ
        }
    }

    when (pantalla) {
        PantallaQuiz.INICIO -> {
            QuizInicioScreen(
                nombre = nombre,
                onNombreChange = { nombre = it },
                onComenzar = {
                    reiniciar(false)
                }
            )
        }

        PantallaQuiz.QUIZ -> {
            QuizPreguntaScreen(
                pregunta = preguntas[indicePregunta],
                numero = indicePregunta + 1,
                total = preguntas.size,
                seleccionada = seleccionada,
                respondida = respondida,
                onSeleccionar = {
                    seleccionada = it
                },
                onConfirmar = {
                    if (!respondida && seleccionada != null) {
                        if (seleccionada == preguntas[indicePregunta].correcta) {
                            puntaje++
                        }
                        respondida = true
                    }
                },
                onSiguiente = {
                    if (indicePregunta == preguntas.lastIndex) {
                        pantalla = PantallaQuiz.RESULTADO
                    } else {
                        indicePregunta++
                        seleccionada = null
                        respondida = false
                    }
                }
            )
        }

        PantallaQuiz.RESULTADO -> {
            QuizResultadoScreen(
                nombre = nombre,
                puntaje = puntaje,
                total = preguntas.size,
                onReintentar = {
                    reiniciar(false)
                },
                onCambiarJugador = {
                    reiniciar(true)
                }
            )
        }
    }
}

@Composable
fun QuizInicioScreen(
    nombre: String,
    onNombreChange: (String) -> Unit,
    onComenzar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Text("Quiz de programación", fontSize = 25.sp)

        Text("Responde 5 preguntas sobre programación")

        OutlinedTextField(
            value = nombre,
            onValueChange = onNombreChange,
            label = { Text("Nombre del alumno") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = onComenzar,
            enabled = nombre.trim().length >= 2,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Comenzar")
        }
    }
}

@Composable
fun QuizPreguntaScreen(
    pregunta: PreguntaQuiz,
    numero: Int,
    total: Int,
    seleccionada: Int?,
    respondida: Boolean,
    onSeleccionar: (Int) -> Unit,
    onConfirmar: () -> Unit,
    onSiguiente: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Text("Quiz de programación", fontSize = 24.sp)

        Text("Pregunta $numero de $total")

        LinearProgressIndicator(
            progress = { numero.toFloat() / total },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = pregunta.texto,
            fontSize = 19.sp
        )

        pregunta.opciones.forEachIndexed { index, opcion ->
            val colorOpcion = when {
                respondida && index == pregunta.correcta -> Color(0xFF228B22)
                respondida && index == seleccionada -> Color.Red
                else -> Color.Unspecified
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = seleccionada == index,
                    onClick = { onSeleccionar(index) },
                    enabled = !respondida
                )

                Text(
                    text = opcion,
                    color = colorOpcion
                )
            }
        }

        if (respondida) {
            if (seleccionada == pregunta.correcta) {
                Text(
                    text = "¡Respuesta correcta!",
                    color = Color(0xFF228B22)
                )
            } else {
                Text(
                    text = "Respuesta incorrecta",
                    color = Color.Red
                )

                Text(
                    text = "Correcta: ${pregunta.opciones[pregunta.correcta]}"
                )
            }

            Button(
                onClick = onSiguiente,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (numero == total) "Ver resultados"
                    else "Siguiente"
                )
            }
        } else {
            Button(
                onClick = onConfirmar,
                enabled = seleccionada != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirmar")
            }
        }
    }
}

@Composable
fun QuizResultadoScreen(
    nombre: String,
    puntaje: Int,
    total: Int,
    onReintentar: () -> Unit,
    onCambiarJugador: () -> Unit
) {
    val mensaje = when (puntaje) {
        in 0..2 -> "Sigue practicando"
        in 3..4 -> "Buen trabajo"
        else -> "Perfecto"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Resultados", fontSize = 25.sp)

        Text(
            text = "¡Muy bien, $nombre!",
            fontSize = 22.sp
        )

        Text(
            text = "$puntaje de $total",
            fontSize = 35.sp
        )

        Text(mensaje, fontSize = 18.sp)

        Button(
            onClick = onReintentar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reintentar")
        }

        OutlinedButton(
            onClick = onCambiarJugador,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cambiar jugador")
        }
    }
}
