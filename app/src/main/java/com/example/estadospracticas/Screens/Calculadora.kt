
package com.example.estadospracticas.screens

class Calculadora {

    var texto = "0"

    private var numeroAnterior = 0.0
    private var operador = ""
    private var nuevoNumero = true

    fun pulsar(boton: String) {

        when (boton) {

            "AC" -> {
                texto = "0"
                numeroAnterior = 0.0
                operador = ""
                nuevoNumero = true
            }

            "<" -> {
                texto = if (texto.length > 1) {
                    texto.dropLast(1)
                } else {
                    "0"
                }
            }

            "±" -> {
                texto = if (texto.startsWith("-")) {
                    texto.drop(1)
                } else if (texto != "0") {
                    "-$texto"
                } else {
                    texto
                }
            }

            "%" -> {
                texto = (texto.toDoubleOrNull()?.div(100) ?: 0.0).toString()
            }

            "+", "-", "x", "÷" -> {
                numeroAnterior = texto.toDoubleOrNull() ?: 0.0
                operador = boton
                nuevoNumero = true
            }

            "=" -> {
                val numeroActual = texto.toDoubleOrNull() ?: 0.0

                val resultado = when (operador) {
                    "+" -> numeroAnterior + numeroActual
                    "-" -> numeroAnterior - numeroActual
                    "x" -> numeroAnterior * numeroActual
                    "÷" -> {
                        if (numeroActual != 0.0) {
                            numeroAnterior / numeroActual
                        } else {
                            null
                        }
                    }
                    else -> numeroActual
                }

                texto = resultado?.let {
                    if ((it % 1.0) == 0.0) it.toLong().toString()
                    else it.toString()
                } ?: "Error"

                operador = ""
                nuevoNumero = true
            }

            "." -> {
                if (nuevoNumero) {
                    texto = "0."
                    nuevoNumero = false
                } else if (!texto.contains(".")) {
                    texto += "."
                }
            }

            else -> {
                if (nuevoNumero || (texto == "0") || (texto == "Error")) {
                    texto = boton
                    nuevoNumero = false
                } else {
                    texto += boton
                }
            }
        }
    }

    fun guardar(): List<String> {
        return listOf(
            texto,
            numeroAnterior.toString(),
            operador,
            nuevoNumero.toString(),
        )
    }

    fun restaurar(datos: List<String>) {
        texto = datos.getOrElse(0) { "0" }
        numeroAnterior = datos.getOrNull(1)?.toDoubleOrNull() ?: 0.0
        operador = datos.getOrElse(2) { "" }
        nuevoNumero = datos.getOrNull(3)?.toBoolean() ?: true
    }
}
