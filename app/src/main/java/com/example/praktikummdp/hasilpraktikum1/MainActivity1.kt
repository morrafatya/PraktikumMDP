package com.example.praktikummdp.hasilpraktikum1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.praktikummdp.navigation.Screen
import com.example.praktikummdp.navigation.SetupNavGraph
import com.example.praktikummdp.ui.theme.PraktikumMDPTheme
import com.example.praktikummdp.ui.theme.MyApp



class MainActivity1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PraktikumMDPTheme {
//                val navController = rememberNavController()
//                SetupNavGraph(navController = navController)
                    val navController = rememberNavController()
                    val currentBackStack by navController.currentBackStackEntryAsState()
                    val currentRoute = currentBackStack?.destination?.route

                    if (currentRoute == Screen.Home.route || currentRoute == Screen.Profile.route) {
                        // Tampilkan MyApp dengan BottomBar
                        MyApp(navController = navController)
                    } else {
                        // Tampilkan SetupNavGraph langsung tanpa Scaffold
                        SetupNavGraph(navController = navController)

                    }
                }
            }
        }
    }





@Composable
fun WelcomeScreen() {
    val context = LocalContext.current
    var nama by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = "Selamat Datang di Mobile device programming",
            color = Color(0xFF512DA8),
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
        )

        OutlinedTextField(
            value = nama,
            label = { Text("Masukkan Nama Anda...") },
            onValueChange = { nama = it },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                Toast.makeText(context, "Halo, $nama", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text(text = "Tampilkan Toast")
        }
    }
}
