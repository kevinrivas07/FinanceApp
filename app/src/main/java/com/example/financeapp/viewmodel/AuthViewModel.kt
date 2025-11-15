package com.example.financeapp.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.financeapp.data.model.Usuario

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> = _isSuccess

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun register(
        nombre: String,
        apellido: String,
        fechaNacimiento: String,
        correo: String,
        contrasena: String
    ) {
        _isLoading.value = true
        _error.value = null
        _isSuccess.value = false

        auth.createUserWithEmailAndPassword(correo, contrasena)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val uid = user?.uid ?: return@addOnCompleteListener
                    val usuario = Usuario(nombre, apellido, fechaNacimiento, correo)

                    // Guardar datos en Realtime Database
                    database.child("usuarios").child(uid).setValue(usuario)
                        .addOnSuccessListener {
                            // Enviar correo de verificación
                            user.sendEmailVerification()
                                .addOnCompleteListener { verifyTask ->
                                    if (verifyTask.isSuccessful) {
                                        _isSuccess.value = true
                                    } else {
                                        _error.value = "Registro exitoso, pero no se pudo enviar el correo de verificación: ${verifyTask.exception?.message}"
                                    }
                                    _isLoading.value = false
                                }
                        }
                        .addOnFailureListener {
                            _error.value = "Error al guardar datos: ${it.message}"
                            _isLoading.value = false
                        }
                } else {
                    _error.value = "Error de autenticación: ${task.exception?.message}"
                    _isLoading.value = false
                }
            }
    }
    fun login(correo: String, contrasena: String, onVerified: (Boolean) -> Unit) {
        _isLoading.value = true
        _error.value = null
        _isSuccess.value = false

        auth.signInWithEmailAndPassword(correo, contrasena)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null && user.isEmailVerified) {
                        _isSuccess.value = true
                        onVerified(true)
                    } else {
                        _error.value = "Verifica tu correo antes de continuar."
                        onVerified(false)
                    }
                } else {
                    _error.value = "Error al iniciar sesión: ${task.exception?.message}"
                    onVerified(false)
                }
                _isLoading.value = false
            }
    }
}