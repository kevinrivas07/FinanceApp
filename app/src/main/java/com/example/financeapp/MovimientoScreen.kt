package com.example.financeapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MovimientosScreen(navController: NavController) {

    // Lista de movimientos simulada
    var movimientos by remember {
        mutableStateOf(
            listOf(
                Movimiento("31-oct-2025", "+ $500.000", "Quincena del 15", Color(0xFF00A000)),
                Movimiento("05-nov-2025", "- $200.000", "Recibo de la energía", Color.Red)
            )
        )
    }

    var showDialog by remember { mutableStateOf(false) }
    var movimientoSeleccionado by remember { mutableStateOf<Movimiento?>(null) }

    // Fondo general
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 🔹 Título
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, start = 20.dp, end = 20.dp)
        ) {
            Text(
                text = "MOVIMIENTOS",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF001DFF),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Todos mis movimientos",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 🔹 Tarjetas de movimientos
            movimientos.forEach { mov ->
                MovimientoCard(
                    movimiento = mov,
                    onEliminarClick = {
                        movimientoSeleccionado = mov
                        showDialog = true
                    }
                )
            }
        }

        // 🔹 Diálogo de confirmación
        if (showDialog) {
            AlertaScreen(
                onConfirm = {
                    movimientos = movimientos.filterNot { it == movimientoSeleccionado }
                    showDialog = false
                },
                onDismiss = { showDialog = false }
            )
        }

        // 🔹 Botón Volver
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF001DFF)),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp)
                .fillMaxWidth()
                .height(45.dp)
        ) {
            Text(
                "Volver",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

data class Movimiento(
    val fecha: String,
    val monto: String,
    val descripcion: String,
    val colorMonto: Color
)

@Composable
fun MovimientoCard(movimiento: Movimiento, onEliminarClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = movimiento.fecha,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "X",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onEliminarClick() }
                )
            }

            Text(
                text = movimiento.monto,
                color = movimiento.colorMonto,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Text(
                text = movimiento.descripcion,
                fontSize = 14.sp
            )
        }
    }
}
