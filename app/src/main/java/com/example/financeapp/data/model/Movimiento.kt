package com.example.financeapp.data.model

import androidx.compose.ui.graphics.Color

data class Movimiento(
    val fecha: String = "",
    val monto: String = "",
    val descripcion: String = "",
    val colorMonto: Color = Color.Black,
    val tipo: String, // "ingreso" o "gasto"
    val firebaseId: String // ID del nodo en Firebase

)