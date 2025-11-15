package com.example.financeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.financeapp.InicioScreen
import com.example.financeapp.LoginScreen
import com.example.financeapp.RegisterScreen
import com.example.financeapp.ui.theme.FinanceAppTheme
import com.google.firebase.FirebaseApp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            FinanceAppTheme {
                val navController = rememberNavController()
                Surface(color = MaterialTheme.colorScheme.background) {
                    NavHost(
                        navController = navController,
                        startDestination = "inicio"
                    ) {

                        composable("inicio") { InicioScreen(navController) }
                        composable("login") { LoginScreen(navController) }
                        composable("register") { RegisterScreen(navController) }
                        composable ( "dashboard") {DashboardScreen(navController)}
                        composable ("ingresos") {IngresoScreen(navController)}
                        composable ("gastos") {GastoScreen(navController)}
                        composable ("perfil") {PerfilScreen(navController)}
                        composable ("movimiento") {MovimientosScreen(navController)}
                    }
                }
            }
        }
    }
}
