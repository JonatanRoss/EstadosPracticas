
package com.example.estadospracticas.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class LoginEstado {
    INACTIVO, ERROR, BLOQUEADO, EXITO
}

@Preview(showBackground = true)
@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var recordarme by remember { mutableStateOf(false) }
    var intentosFallidos by remember { mutableIntStateOf(0) }
    var estado by remember { mutableStateOf(LoginEstado.INACTIVO) }

    val mensajeError = when (estado) {
        LoginEstado.ERROR ->
            "Correo o contraseña incorrectos (intento $intentosFallidos de 3)"
        LoginEstado.BLOQUEADO ->
            "Demasiados intentos. Intenta más tarde."
        else -> null
    }

    if (estado == LoginEstado.EXITO) {
        HomeScreen(
            email = email,
            onLogout = {
                email = ""
                password = ""
                recordarme = false
                intentosFallidos = 0
                estado = LoginEstado.INACTIVO
            }
        )
    } else {
        LoginForm(
            email = email,
            onEmailChange = { email = it },
            password = password,
            onPasswordChange = { password = it },
            recordarme = recordarme,
            onRecordarmeChange = { recordarme = it },
            mensajeError = mensajeError,
            bloqueado = estado == LoginEstado.BLOQUEADO,
            onLogin = {
                if (email == "paciente@vida.mx" && password == "Vida2025") {
                    estado = LoginEstado.EXITO
                } else {
                    intentosFallidos++

                    if (intentosFallidos >= 3) {
                        estado = LoginEstado.BLOQUEADO
                    } else {
                        estado = LoginEstado.ERROR
                    }
                }
            }
        )
    }
}

@Composable
fun LoginForm(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    recordarme: Boolean,
    onRecordarmeChange: (Boolean) -> Unit,
    mensajeError: String?,
    bloqueado: Boolean,
    onLogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Text("Clínica Vida", fontSize = 25.sp)

        Text("Inicio de sesión", fontSize = 18.sp)

        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )

        PasswordField(
            value = password,
            onValueChange = onPasswordChange
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = recordarme,
                onCheckedChange = onRecordarmeChange
            )

            Text("Recordarme")
        }

        if (mensajeError != null) {
            Text(
                text = mensajeError,
                color = MaterialTheme.colorScheme.error
            )
        }

        Button(
            onClick = onLogin,
            enabled = !bloqueado,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Entrar")
        }
    }
}

@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit
) {
    var mostrarPassword by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Contraseña") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        visualTransformation = if (mostrarPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        trailingIcon = {
            TextButton(
                onClick = {
                    mostrarPassword = !mostrarPassword
                }
            ) {
                Text(
                    if (mostrarPassword) "Ocultar" else "Ver"
                )
            }
        }
    )
}

@Composable
fun HomeScreen(
    email: String,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Text(
            text = "Bienvenido a Clínica Vida",
            fontSize = 24.sp
        )

        Text("Sesión iniciada correctamente")

        Text("Correo: $email")

        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesión")
        }
    }
}
