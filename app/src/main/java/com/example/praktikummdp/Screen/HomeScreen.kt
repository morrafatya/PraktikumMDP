package com.example.praktikummdp.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.praktikummdp.Components.NoteCard
import com.example.praktikummdp.R
import com.example.praktikummdp.model.viewModel.NotesViewModel
import com.example.praktikummdp.navigation.Screen

@Composable
fun HomeScreen(navController: NavController, viewModel: NotesViewModel = viewModel()) {
   // var text by remember { mutableStateOf("") }

    //    var text by remember { mutableStateOf("")
    val notesState by viewModel.notes.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ){
        if (notesState.isEmpty()){
            CircularProgressIndicator(modifier = Modifier.align (Alignment.Center))
        } else{
            LazyColumn (
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ){
                items(notesState) {
                        note ->
                    NoteCard(note)
                }
            }
        }
    }
}


//    Box(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        // 🔹 Background Gambar
//        Image(
//            painter = painterResource(id = R.drawable.bg1), // Pastikan gambar bg1 ada di res/drawable
//            contentDescription = null,
//            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.Crop
//        )

//        // 🔹 Overlay Transparan
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.Black.copy(alpha = 0.5f))
//        )
//
//        // 🔹 Konten Utama di Tengah
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(24.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth(0.9f)
//                    .background(Color.White, shape = RoundedCornerShape(16.dp))
//                    .padding(24.dp),
//                verticalArrangement = Arrangement.spacedBy(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(
//                    "Halaman Utama",
//                    fontSize = 22.sp,
//                    fontWeight = FontWeight.Bold,
//                )
//                Text(
//                    "Masukkan Nama Kamu",
//                    color = Color.Gray,
//                    fontSize = 14.sp
//                )
//                TextField(
//                    value = text,
//                    onValueChange = { text = it },
//                    modifier = Modifier.fillMaxWidth(),
//                    placeholder = { Text("Contoh: Aku Gisna") }
//                )
//                Button(
//                    onClick = { navController.navigate(Screen.Result.passText(text)) },
//                    modifier = Modifier.fillMaxWidth(),
//                ) {
//                    Text("Submit", color = Color.White)
//                }
//            }
//        }
//    }
//}
