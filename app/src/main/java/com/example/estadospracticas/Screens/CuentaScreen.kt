
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
import kotlin.math.ceil
import kotlin.math.roundToInt

@Preview(showBackground = true)
@Composable
fun CuentaScreen() {
    var montoTexto by remember { mutableStateOf("") }
    var propinaPct by remember { mutableIntStateOf(15) }
    var usaOtro by remember { mutableStateOf(false) }
    var personas by remember { mutableIntStateOf(2) }
    var redondear by remember { mutableStateOf(false) }

    val monto = montoTexto.toDoubleOrNull()
        ?.takeIf { it >= 0.0 && it.isFinite() } ?: 0.0

    val propina = monto * propinaPct / 100.0
    val total = monto + propina
    val division = total / personas

    val porPersona = if (redondear) {
        ceil(division * 100 - 0.000000001) / 100
    } else {
        division
    }

    val sobrante = if (redondear) {
        (porPersona * personas - total).coerceAtLeast(0.0)
    } else {
        null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Text(
            text = "Cuenta dividida",
            fontSize = 25.sp
        )

        Text("Calcula cuánto debe pagar cada persona")

        OutlinedTextField(
            value = montoTexto,
            onValueChange = { montoTexto = it },
            label = { Text("Monto de la cuenta") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            singleLine = true
        )

        if (montoTexto.toDoubleOrNull()
                ?.let { it >= 0.0 && it.isFinite() } != true
        ) {
            Text(
                text = "Monto no válido, se utilizará $0.00",
                fontSize = 12.sp
            )
        }

        SelectorPropina(
            porcentaje = propinaPct,
            usaOtro = usaOtro,
            onPorcentajeChange = { propinaPct = it },
            onOtroChange = { usaOtro = it }
        )

        StepperPersonas(
            personas = personas,
            onChange = { personas = it }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Redondear hacia arriba")

            Switch(
                checked = redondear,
                onCheckedChange = { redondear = it }
            )
        }

        HorizontalDivider()

        ResultadoCuenta(
            propina = propina,
            total = total,
            porPersona = porPersona,
            sobrante = sobrante
        )
    }
}

@Composable
fun SelectorPropina(
    porcentaje: Int,
    usaOtro: Boolean,
    onPorcentajeChange: (Int) -> Unit,
    onOtroChange: (Boolean) -> Unit
) {
    Column {
        Text("Propina", fontSize = 19.sp)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(10, 15, 20).forEach { opcion ->
                FilterChip(
                    selected = !usaOtro && porcentaje == opcion,
                    onClick = {
                        onOtroChange(false)
                        onPorcentajeChange(opcion)
                    },
                    label = { Text("$opcion%") }
                )
            }

            FilterChip(
                selected = usaOtro,
                onClick = {
                    onOtroChange(true)
                },
                label = { Text("Otro") }
            )
        }

        if (usaOtro) {
            Text("Propina personalizada: $porcentaje%")

            Slider(
                value = porcentaje.toFloat(),
                onValueChange = {
                    onPorcentajeChange(it.roundToInt())
                },
                valueRange = 0f..30f,
                steps = 29
            )
        }
    }
}

@Composable
fun StepperPersonas(
    personas: Int,
    onChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Número de personas")

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { onChange(personas - 1) },
                enabled = personas > 1
            ) {
                Text("-")
            }

            Text(
                text = "$personas",
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            OutlinedButton(
                onClick = { onChange(personas + 1) },
                enabled = personas < 20
            ) {
                Text("+")
            }
        }
    }
}

@Composable
fun ResultadoCuenta(
    propina: Double,
    total: Double,
    porPersona: Double,
    sobrante: Double?
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Resultados",
            fontSize = 22.sp
        )

        Text("Propina total: \$${"%.2f".format(propina)}")

        Text("Total con propina: \$${"%.2f".format(total)}")

        Text(
            text = "Por persona: \$${"%.2f".format(porPersona)}",
            fontSize = 22.sp
        )

        if (sobrante != null) {
            Text(
                text = "Sobrante por redondeo: \$${"%.2f".format(sobrante)}"
            )
        }
    }
}
