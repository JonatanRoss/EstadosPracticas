
package com.example.estadospracticas.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun InscripcionScreen() {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var taller by remember { mutableStateOf("Pintura") }
    var aviso by remember { mutableStateOf(false) }
    var intentoEnviar by remember { mutableStateOf(false) }
    var inscrito by remember { mutableStateOf(false) }

    val nombreValido = nombre.trim().length >= 3
    val arroba = correo.indexOf("@")
    val punto = correo.indexOf(".", arroba + 1)
    val correoValido = arroba > 0 && punto > arroba + 1 && punto < correo.length - 1
    val edadValida = edad.toIntOrNull()?.let { it in 12..99 } ?: false

    if (inscrito) {
        ConfirmacionScreen(
            nombre = nombre,
            correo = correo,
            edad = edad,
            taller = taller,
            onNueva = {
                nombre = ""
                correo = ""
                edad = ""
                taller = "Pintura"
                aviso = false
                intentoEnviar = false
                inscrito = false
            }
        )
    } else {
        InscripcionForm(
            nombre = nombre,
            onNombreChange = { nombre = it },
            correo = correo,
            onCorreoChange = { correo = it },
            edad = edad,
            onEdadChange = { edad = it.filter { c -> c.isDigit() } },
            taller = taller,
            onTallerChange = { taller = it },
            aviso = aviso,
            onAvisoChange = { aviso = it },
            intentoEnviar = intentoEnviar,
            nombreValido = nombreValido,
            correoValido = correoValido,
            edadValida = edadValida,
            onInscribir = {
                intentoEnviar = true
                if (nombreValido && correoValido && edadValida && aviso) {
                    inscrito = true
                }
            }
        )
    }
}

@Composable
fun InscripcionForm(
    nombre: String,
    onNombreChange: (String) -> Unit,
    correo: String,
    onCorreoChange: (String) -> Unit,
    edad: String,
    onEdadChange: (String) -> Unit,
    taller: String,
    onTallerChange: (String) -> Unit,
    aviso: Boolean,
    onAvisoChange: (Boolean) -> Unit,
    intentoEnviar: Boolean,
    nombreValido: Boolean,
    correoValido: Boolean,
    edadValida: Boolean,
    onInscribir: () -> Unit
) {
    var menuAbierto by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Casa del Lago", fontSize = 24.sp)
        Text("Inscripción a talleres", fontSize = 17.sp)

        OutlinedTextField(
            value = nombre,
            onValueChange = onNombreChange,
            label = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth(),
            isError = intentoEnviar && !nombreValido,
            supportingText = {
                if (intentoEnviar && !nombreValido) {
                    Text("Escribe al menos 3 caracteres")
                }
            }
        )

        OutlinedTextField(
            value = correo,
            onValueChange = onCorreoChange,
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            isError = intentoEnviar && !correoValido,
            supportingText = {
                if (intentoEnviar && !correoValido) {
                    Text("Correo no válido")
                }
            }
        )

        OutlinedTextField(
            value = edad,
            onValueChange = onEdadChange,
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            isError = intentoEnviar && !edadValida,
            supportingText = {
                if (intentoEnviar && !edadValida) {
                    Text("La edad debe estar entre 12 y 99")
                }
            }
        )

        Text("Selecciona un taller")

        Box {
            OutlinedButton(
                onClick = { menuAbierto = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(taller)
                Spacer(Modifier.weight(1f))
                Text("▼")
            }

            DropdownMenu(
                expanded = menuAbierto,
                onDismissRequest = { menuAbierto = false }
            ) {
                listOf("Pintura", "Fotografía", "Guitarra").forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            onTallerChange(it)
                            menuAbierto = false
                        }
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = aviso,
                onCheckedChange = onAvisoChange
            )
            Text("Acepto el aviso de privacidad")
        }

        if (intentoEnviar && !aviso) {
            Text(
                "Debes aceptar el aviso",
                color = MaterialTheme.colorScheme.error
            )
        }

        Button(
            onClick = onInscribir,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Inscribirme")
        }
    }
}

@Composable
fun ConfirmacionScreen(
    nombre: String,
    correo: String,
    edad: String,
    taller: String,
    onNueva: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Text("Inscripción exitosa", fontSize = 24.sp)

        Text("Nombre: $nombre")
        Text("Correo: $correo")
        Text("Edad: $edad")
        Text("Taller: $taller")

        Button(
            onClick = onNueva,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Nueva inscripción")
        }
    }
}
