package com.example.financeapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun DashboardScreen(navController: NavController) {
    // Fondo general color celeste
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD3EAFB)), // Fondo azul claro
        contentAlignment = Alignment.Center
    ) {
        // Tarjeta central
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F3FA)), // Fondo de la tarjeta
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.9f)
                .height(420.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Botón circular superior derecho (perfil)
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.TopEnd)
                        .background(Color(0xFFE0E0E0), CircleShape)
                        .clickable { navController.navigate("perfil") } // 🔹 Navega a la pantalla de perfil
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "P",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                // Contenido central
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "DASHBOARD",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF001DFF)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "$ 500.000",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Text(
                        text = "Saldo Disponible",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    // Botones
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { /* Acción Ingresos */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), // Verde
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                "INGRESOS",
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Button(
                            onClick = { /* Acción Gastos */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336)), // Rojo
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                "GASTOS",
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { /* Acción Movimientos */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF001DFF)), // Azul fuerte
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(
                            "MOVIMIENTOS",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
