package com.example.practicasandroidstudio.ui.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun micard(
    modifier: Modifier = Modifier,
    nombre: String,
    eliminar: () -> Unit,
    actualizar: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .border(2.dp, Color.Blue, RoundedCornerShape(12.dp))
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(10.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = nombre,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "Actualizar",
                modifier = Modifier.clickable {
                    actualizar()
                }.padding(5.dp)
            )

            Text(
                text = "Eliminar",
                modifier = Modifier.clickable {
                    eliminar()
                }.padding(5.dp)
            )
        }
    }
}