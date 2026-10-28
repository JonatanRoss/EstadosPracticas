package com.example.estadospracticas.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.intellij.lang.annotations.JdkConstants

@Preview (showBackground = true)
@Composable
fun AforoScreen() {
    var personasActuales by remember { mutableIntStateOf(0) }
    var personasVisitasDia by remember { mutableIntStateOf(0) }
    val porcentaje = personasActuales / 50f
    val fondoAforo = if(porcentaje < 0.7f) {
        Color(0xFFE5F4E7)
    } else if(porcentaje < 1f) {
        Color(0xFFFFF3D8)
    } else {
        Color(0xFFFFE4E4)
    }
    val colorBarra = if (porcentaje < 0.7f) {
        Color(0xFF247A36)
    } else if (porcentaje < 1f) {
        Color(0xFFE9A018)
    } else {
        Color(0xFFC52D2D)
    }
    val aforoDisponible = if (porcentaje < 1f) {
        "Aforo disponible"
    } else {
        "Aforo Completo"
    }
    Column(modifier = Modifier.fillMaxWidth().padding(20.dp, 30.dp)) {
        Column(modifier = Modifier.padding(bottom = 20.dp)) {
            Text(text = "Biblioteca Central", fontSize = 24.sp)
        }

        Text(text = "Sala de estudio capacidad 50", color = Color.Gray, fontSize = 15.sp)

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .background(fondoAforo,
                    RoundedCornerShape(20.dp)
                )
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
                Text(text = "$personasActuales / 50", fontSize = 40.sp)
                Text(text = "Personas en la sala", Modifier.padding(bottom = 20.dp))
            LinearProgressIndicator(
                progress =  { porcentaje },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(10.dp)), color = colorBarra, trackColor = Color.LightGray
            )

                Text(text = "${(porcentaje * 100).toInt()} % de ocupación",Modifier.padding(top = 20.dp), fontSize = 15.sp)
                Text(text = aforoDisponible, Modifier.padding(top = 12.dp))
            }

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = {
                    personasActuales--
                },
                enabled = personasActuales > 0,
                modifier = Modifier.weight(1f).height(48.dp),
                shape = RoundedCornerShape(25.dp),
                border = BorderStroke(1.dp, Color(0xFF7656B5)),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF7656B5)
                )
            ) {
                Text("− Sale")
            }

            Button(
                onClick = {
                    personasActuales++
                    personasVisitasDia++
                },
                enabled = personasActuales < 50,
                modifier = Modifier.weight(1f).height(48.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6B50A8),
                    contentColor = Color.White
                )
            ) {
                Text("+ Entra")
            }
        }
            Row(modifier = Modifier.fillMaxWidth().background(Color(0xFFF2ECF6), RoundedCornerShape(15.dp)).padding(horizontal = 15.dp, vertical = 20.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Column() {
                    Text(text = "visitas del día")
                    Text(text = "Solo suma las entradas")
                }

                Text(text = "$personasVisitasDia", fontSize = 33.sp, fontWeight = FontWeight.Black)
            }

            Row(modifier = Modifier.fillMaxWidth().padding(top = 30.dp, bottom = 100.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Verde < 70%")
                Text(text = "Amarillo 70-99%")
                Text(text = "Rojo 100%")
            }

            Column() {
                OutlinedButton(onClick = {
                    personasActuales = 0
                    personasVisitasDia = 0
                }, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, Color.Red)
                    ) {
                   Text( text = "Cerrar día",
                       color = Color.Red)
                }
            }
        }
    }
