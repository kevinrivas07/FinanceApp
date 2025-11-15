package com.example.financeapp

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.financeapp.data.model.Movimiento
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.example.financeapp.MovimientoCard

@Composable
fun MovimientosScreen(navController: NavController) {
    val context = LocalContext.current
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val ingresosRef = FirebaseDatabase.getInstance().getReference("ingresos")
    val gastosRef = FirebaseDatabase.getInstance().getReference("gastos")

    var movimientos by remember { mutableStateOf(listOf<Movimiento>()) }
    var showDialog by remember { mutableStateOf(false) }
    var movimientoSeleccionado by remember { mutableStateOf<Movimiento?>(null) }

    // 🔄 Cargar ingresos y gastos
    LaunchedEffect(uid) {
        uid?.let {
            ingresosRef.child(it).get().addOnSuccessListener { snapshot ->
                val ingresos = snapshot.children.mapNotNull { ingreso ->
                    val valor = ingreso.child("valor").getValue(String::class.java) ?: return@mapNotNull null
                    val fecha = ingreso.child("fecha").getValue(String::class.java) ?: ""
                    val obs = ingreso.child("observacion").getValue(String::class.java) ?: ""
                    val id = ingreso.key ?: return@mapNotNull null
                    Movimiento(fecha, "+ $valor", obs, Color(0xFF00A000), "ingreso", id)
                }

                gastosRef.child(it).get().addOnSuccessListener { snapshotG ->
                    val gastos = snapshotG.children.mapNotNull { gasto ->
                        val valor = gasto.child("valor").getValue(String::class.java) ?: return@mapNotNull null
                        val fecha = gasto.child("fecha").getValue(String::class.java) ?: ""
                        val obs = gasto.child("observacion").getValue(String::class.java) ?: ""
                        val id = gasto.key ?: return@mapNotNull null
                        Movimiento(fecha, "- $valor", obs, Color.Red, "gasto", id)
                    }

                    movimientos = (ingresos + gastos).sortedByDescending { it.fecha }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
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

        if (showDialog) {
            AlertaScreen(
                onConfirm = {
                    val mov = movimientoSeleccionado
                    if (uid != null && mov != null) {
                        val ref = FirebaseDatabase.getInstance().getReference(
                            if (mov.tipo == "ingreso") "ingresos/$uid/${mov.firebaseId}"
                            else "gastos/$uid/${mov.firebaseId}"
                        )
                        ref.removeValue()
                            .addOnSuccessListener {
                                movimientos = movimientos.filterNot { it.firebaseId == mov.firebaseId }
                                Toast.makeText(context, "Movimiento eliminado ✅", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Error al eliminar: ${it.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                    showDialog = false
                },
                onDismiss = { showDialog = false }
            )
        }

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
            Text("Volver", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}