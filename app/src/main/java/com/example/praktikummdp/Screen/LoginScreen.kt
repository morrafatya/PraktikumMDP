package com.example.praktikummdp.Screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.praktikummdp.R
import com.example.praktikummdp.model.request.LoginRequest
import com.example.praktikummdp.navigation.Screen
import com.example.praktikummdp.service.api.ApiClient
import com.example.praktikummdp.utils.PreferenceManager
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController) {
    // State untuk menyimpan input username dan password
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // State untuk menandai apakah terdapat error pada input
    var usernameError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    // State untuk menampilkan indikator loading saat proses login berlangsung
    var isLoading by remember { mutableStateOf(false) }

    // Utilitas untuk manajemen UI
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 🔹 Background Gambar
        Image(
            painter = painterResource(id = R.drawable.bg1),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 🔹 Overlay Gelap Transparan
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )

        // 🔹 Konten Form Login di tengah
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.85f), // agar tidak terlalu mepet sisi layar
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Login",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        focusManager.clearFocus() // Tutup keyboard
                        usernameError = username.isBlank()
                        passwordError = password.isBlank()

                        // Jika input valid, kirim permintaan login
                        if (!usernameError && !passwordError) {
                            isLoading = true
                            coroutineScope.launch {
                                try {
                                    val response = ApiClient.instance.login(
                                        LoginRequest(username = username, password = password)
                                    )
                                    isLoading = false
                                    val body = response.body()

                                    if (response.isSuccessful && body?.code == 200) {
                                        val token =body.token
                                        PreferenceManager.setToken(context,token.toString())
                                        // Login sukses, navigasi ke halaman Home
                                        Toast.makeText(context, "Login berhasil!", Toast.LENGTH_SHORT).show()
                                        navController.navigate(Screen.Home.route) {
                                            popUpTo(Screen.Login.route) { inclusive = true }
                                        }
                                    } else {
                                        // Login gagal, tampilkan pesan dari server
                                        val errorMessage = body?.message ?: response.message()
                                        Toast.makeText(context, "Gagal: $errorMessage", Toast.LENGTH_LONG).show()
                                    }
                                } catch (e: Exception) {
                                    // Tangani kesalahan (contoh: masalah jaringan)
                                    isLoading = false
                                    Toast.makeText(context, "Terjadi kesalahan: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login")
                    }
            }
        }
    }
    if (isLoading) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {},
            title = { Text("Loading") },
            text = {
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Sedang login...")
                }
            }
            )
    }
}

