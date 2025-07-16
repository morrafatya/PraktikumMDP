package com.example.praktikummdp.Screen

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.praktikummdp.model.request.RegisterRequest
import com.example.praktikummdp.navigation.Screen
import com.example.praktikummdp.service.api.ApiClient
import kotlinx.coroutines.launch

@Composable
fun RegistrasiScreen(navController: NavController) {
    // State untuk nilai input dari form registrasi
    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // State untuk menandai apakah input mengalami kesalahan validasi
    var fullNameError by remember { mutableStateOf(false) }
    var usernameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }

    // State untuk menampilkan indikator loading selama proses registrasi
    var isLoading by remember { mutableStateOf(false) }

    // Utilitas fokus input dan konteks aplikasi
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        // 🔹 Background Gambar
        Image(
            painter = painterResource(id = R.drawable.bg1), // Pastikan bg1 ada di res/drawable
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 🔹 Overlay Transparan
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )

        // 🔹 Form Registrasi di Tengah
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Daftar Akun", style = MaterialTheme.typography.headlineMedium)

                TextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text("Nama Lengkap") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Konfirmasi Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                    Button(
                        onClick = {
                            focusManager.clearFocus()

                            // Validasi input sebelum submit
                            fullNameError = fullName.isBlank()
                            usernameError = username.isBlank()
                            emailError = email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()
                            passwordError = password.isBlank()
                            confirmPasswordError = confirmPassword != password

                            // Jika semua input valid, lakukan registrasi
                            if (!fullNameError && !usernameError && !emailError && !passwordError && !confirmPasswordError) {
                                isLoading = true
                                coroutineScope.launch {
                                    try {
                                        val response = ApiClient.instance.register(
                                            RegisterRequest(
                                                nm_lengkap = fullName,
                                                email = email,
                                                username = username,
                                                password = password
                                            )
                                        )
                                        isLoading = false
                                        val body = response.body()

                                        if (response.isSuccessful && body != null) {
                                            Toast.makeText(context, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
                                            // Navigasi ke halaman login setelah sukses
                                            navController.navigate(Screen.Login.route) {
                                                popUpTo(Screen.Register.route) { inclusive = true }
                                            }
                                        } else {
                                            val errorMsg = body?.message?: response.message()
                                            Toast.makeText(context, "Gagal: $errorMsg", Toast.LENGTH_LONG).show()
                                        }
                                    } catch (e: Exception) {
                                        isLoading = false
                                        Toast.makeText(context, "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Register")
                        }


                            Text(
                    text = "Sudah punya akun? Login di sini",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.Login.route)
                        },
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
    if (isLoading) {
        AlertDialog(
            onDismissRequest = {}, // Tidak dapat ditutup manual
            confirmButton = {},
            title = { Text("Mohon tunggu") },
            text = {
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Sedang mengirim data...")
                }
            }
            )
        }
}
