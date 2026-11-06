
package com.example.estadospracticas.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class CategoriaGasto(val nombre: String) {
    COMIDA("Comida"),
    TRANSPORTE("Transporte"),
    RENTA("Renta"),
    OCIO("Ocio"),
    OTROS("Otros")
}

data class Gasto(
    val id: Int,
    val concepto: String,
    val monto: Double,
    val categoria: CategoriaGasto
)

@Preview(showBackground = true)
@Composable
fun PresupuestoScreen() {
    var ingresoTexto by remember { mutableStateOf("4500") }
    val gastos = remember { mutableStateListOf<Gasto>() }
    var siguienteId by remember { mutableIntStateOf(1) }

    val ingreso = ingresoTexto.toDoubleOrNull()
        ?.takeIf { it >= 0 && it.isFinite() } ?: 0.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Presupuesto mensual", fontSize = 24.sp)

        OutlinedTextField(
            value = ingresoTexto,
            onValueChange = { ingresoTexto = it },
            label = { Text("Ingreso mensual") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            singleLine = true
        )

        Text("Agregar gasto", fontSize = 19.sp)

        FormularioGasto(
            onAgregar = { concepto, monto, categoria ->
                gastos.add(
                    Gasto(siguienteId, concepto, monto, categoria)
                )
                siguienteId++
            }
        )

        HorizontalDivider()

        Text("Lista de gastos", fontSize = 19.sp)

        ListaGastos(
            gastos = gastos,
            onEliminar = { gasto ->
                gastos.remove(gasto)
            }
        )

        HorizontalDivider()

        ResumenPresupuesto(
            ingreso = ingreso,
            gastos = gastos
        )
    }
}

@Composable
fun FormularioGasto(
    onAgregar: (String, Double, CategoriaGasto) -> Unit
) {
    var concepto by remember { mutableStateOf("") }
    var montoTexto by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf(CategoriaGasto.COMIDA) }
    var menuAbierto by remember { mutableStateOf(false) }

    val monto = montoTexto.toDoubleOrNull()
    val datosValidos = concepto.isNotBlank() &&
            monto != null && monto > 0 && monto.isFinite()

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedTextField(
            value = concepto,
            onValueChange = { concepto = it },
            label = { Text("Concepto") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = montoTexto,
            onValueChange = { montoTexto = it },
            label = { Text("Monto") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            )
        )

        Text("Categoría")

        Box {
            OutlinedButton(
                onClick = { menuAbierto = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(categoria.nombre)
            }

            DropdownMenu(
                expanded = menuAbierto,
                onDismissRequest = { menuAbierto = false }
            ) {
                CategoriaGasto.entries.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion.nombre) },
                        onClick = {
                            categoria = opcion
                            menuAbierto = false
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                if (datosValidos && monto != null) {
                    onAgregar(concepto.trim(), monto, categoria)
                    concepto = ""
                    montoTexto = ""
                    categoria = CategoriaGasto.COMIDA
                }
            },
            enabled = datosValidos,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar gasto")
        }
    }
}

@Composable
fun ListaGastos(
    gastos: List<Gasto>,
    onEliminar: (Gasto) -> Unit
) {
    if (gastos.isEmpty()) {
        Text("No hay gastos registrados")
    }

    Column {
        gastos.forEach { gasto ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(gasto.concepto)
                    Text(
                        "${gasto.categoria.nombre}: \$${"%.2f".format(gasto.monto)}",
                        fontSize = 13.sp
                    )
                }

                TextButton(
                    onClick = { onEliminar(gasto) }
                ) {
                    Text("Eliminar")
                }
            }
        }
    }
}

@Composable
fun ResumenPresupuesto(
    ingreso: Double,
    gastos: List<Gasto>
) {
    val total = gastos.sumOf { it.monto }
    val saldo = ingreso - total

    val porcentaje = if (ingreso > 0) {
        total / ingreso * 100
    } else if (total > 0) {
        100.0
    } else {
        0.0
    }

    val grupos = gastos.groupBy { it.categoria }

    val categoriaMayor = grupos.maxByOrNull { grupo ->
        grupo.value.sumOf { it.monto }
    }?.key?.nombre ?: "Ninguna"

    val colorBarra = if (total > ingreso) {
        Color.Red
    } else if (porcentaje >= 80) {
        Color(0xFFE6A000)
    } else {
        Color.Green
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Resumen", fontSize = 20.sp)

        Text("Total gastado: \$${"%.2f".format(total)}")

        Text("Saldo disponible: \$${"%.2f".format(saldo)}")

        Text("Presupuesto usado: ${"%.1f".format(porcentaje)}%")

        LinearProgressIndicator(
            progress = {
                (porcentaje / 100).toFloat().coerceIn(0f, 1f)
            },
            modifier = Modifier.fillMaxWidth(),
            color = colorBarra
        )

        Text("Categoría con mayor gasto: $categoriaMayor")

        if (total > ingreso) {
            Text(
                text = "Presupuesto excedido por \$${"%.2f".format(total - ingreso)}",
                color = Color.Red
            )
        } else if (porcentaje >= 80) {
            Text(
                text = "Has utilizado el 80% o más de tu presupuesto",
                color = Color(0xFFE6A000)
            )
        }
    }
}
