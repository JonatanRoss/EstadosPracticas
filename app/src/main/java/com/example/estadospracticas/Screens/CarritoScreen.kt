
package com.example.estadospracticas.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Producto(
    val nombre: String,
    val precio: Double
)

@Preview(showBackground = true)
@Composable
fun CarritoScreen() {
    val productos = remember {
        listOf(
            Producto("Tortillas 1 kg", 24.0),
            Producto("Leche 1 L", 27.50),
            Producto("Huevo 12 pz", 52.0),
            Producto("Frijol 1 kg", 38.0)
        )
    }

    val cantidades = remember {
        mutableStateListOf(0, 0, 0, 0)
    }

    var mostrarConfirmacion by remember { mutableStateOf(false) }

    val articulos = cantidades.sum()

    val subtotal = productos.indices.sumOf {
        productos[it].precio * cantidades[it]
    }

    val descuento = if (subtotal >= 200) {
        subtotal * 0.10
    } else {
        0.0
    }

    val subtotalDescuento = subtotal - descuento

    val envio = if (articulos == 0) {
        0.0
    } else if (subtotalDescuento >= 150) {
        0.0
    } else {
        25.0
    }

    val total = subtotalDescuento + envio

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Carrito Don Pepe",
            fontSize = 25.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text("Selecciona los productos que quieras comprar")

        Spacer(modifier = Modifier.height(20.dp))

        productos.forEachIndexed { index, producto ->
            ProductoItem(
                producto = producto,
                cantidad = cantidades[index],
                onCantidadChange = {
                    cantidades[index] = it
                }
            )

            HorizontalDivider()
        }

        Spacer(modifier = Modifier.height(25.dp))

        ResumenCarrito(
            articulos = articulos,
            subtotal = subtotal,
            descuento = descuento,
            envio = envio,
            total = total
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = {
                    for (i in cantidades.indices) {
                        cantidades[i] = 0
                    }
                },
                enabled = articulos > 0,
                modifier = Modifier.weight(1f)
            ) {
                Text("Vaciar carrito")
            }

            Button(
                onClick = {
                    mostrarConfirmacion = true
                },
                enabled = articulos > 0,
                modifier = Modifier.weight(1f)
            ) {
                Text("Confirmar")
            }
        }
    }

    if (mostrarConfirmacion) {
        AlertDialog(
            onDismissRequest = {
                mostrarConfirmacion = false
            },
            title = {
                Text("Pedido confirmado")
            },
            text = {
                Text("Total de la compra: $${"%.2f".format(total)}")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarConfirmacion = false
                    }
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
}

@Composable
fun ProductoItem(
    producto: Producto,
    cantidad: Int,
    onCantidadChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(producto.nombre, fontSize = 17.sp)
            Text("$${"%.2f".format(producto.precio)}")
        }

        IconButton(
            onClick = {
                onCantidadChange(cantidad - 1)
            },
            enabled = cantidad > 0
        ) {
            Text("-", fontSize = 23.sp)
        }

        Text("$cantidad")

        IconButton(
            onClick = {
                onCantidadChange(cantidad + 1)
            },
            enabled = cantidad < 10
        ) {
            Text("+", fontSize = 23.sp)
        }
    }
}

@Composable
fun ResumenCarrito(
    articulos: Int,
    subtotal: Double,
    descuento: Double,
    envio: Double,
    total: Double
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Resumen del pedido",
            fontSize = 21.sp
        )

        Text("Artículos: $articulos")
        Text("Subtotal: $${"%.2f".format(subtotal)}")
        Text("Descuento: $${"%.2f".format(descuento)}")
        Text("Envío: $${"%.2f".format(envio)}")

        HorizontalDivider()

        Text(
            text = "Total: $${"%.2f".format(total)}",
            fontSize = 22.sp
        )
    }
}
