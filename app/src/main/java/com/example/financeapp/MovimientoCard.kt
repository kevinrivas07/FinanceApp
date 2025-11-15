package com.example.financeapp


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financeapp.data.model.Movimiento

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
                text = "$ ${movimiento.monto}",
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