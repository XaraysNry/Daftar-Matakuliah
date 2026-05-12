package com.aditya.daftarmatakuliah

import android.os.Bundle
import androidx.compose.ui.layout.ContentScale
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.aditya.daftarmatakuliah.ui.theme.DaftarMatakuliahTheme

// MainActivity sebagai titik masuk aplikasi
class MainActivity : ComponentActivity() {
    private val viewModel: MatakuliahViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DaftarMatakuliahTheme {
                MatakuliahApp(viewModel = viewModel)
            }
        }
    }
}

// MatakuliahApp adalah komponen utama aplikasi yang menampilkan daftar matakuliah
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatakuliahApp(viewModel: MatakuliahViewModel) {
    // UI LAYER: Mengamati state dari Data Layer (ViewModel)
    val daftarMataKuliah by viewModel.daftarMatakuliah.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    // ELEMENT UI: Scaffold untuk kerangka dasar aplikasi
    Scaffold(
        topBar = {
            // ELEMENT UI: AppBar
            TopAppBar(
                title = { Text("Matakuliah Semester Genap") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        // LAYOUT: Column sebagai pembungkus utama
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // ELEMENT UI & LAYOUT: Box dengan Image di dalamnya
            // Box ini digunakan untuk membuat latar belakang banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp), // Sesuaikan tinggi banner
                contentAlignment = Alignment.Center
            ) {
                // Elemen Gambar (Latar Belakang)
                Image(
                    painter = painterResource(id = R.drawable.logo_utdi),
                    contentDescription = "Logo UTDI",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Elemen Pembatas Teks (Surface)
                // Surface ini digunakan untuk membuat sudut kotak sedikit melengkung dan sedikit transparan
                Surface(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                    shape = MaterialTheme.shapes.medium,
                    shadowElevation = 4.dp
                ) {
                    // Elemen Teks
                    Text(
                        text = "Semester Ganjil 2024",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary, // Warna teks agar kontras dengan pembatasnya
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // LAYOUT & INPUT: Row untuk menyusun TextField dan Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // ELEMENT UI INPUT: TextField untuk mengetik
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.updateSearchQuery(it) },
                    label = { Text("Cari Matakuliah...") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                // ELEMENT UI INPUT: Button
                Button(
                    onClick = {},
                    modifier = Modifier.height(56.dp)
                ) {
                    Text("Cari")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // SCROLLABLE LIST & GRID: Menampilkan data dalam bentuk Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // Membuat 2 kolom
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                // Menampilkan item hasil filter atau seluruh data
                val filteredList = daftarMataKuliah.filter {
                    it.namaMatakuliah.contains(searchQuery, ignoreCase = true)
                }

                items(filteredList) { matakuliah ->
                    MatakuliahCard(matakuliah = matakuliah)
                }
            }
        }
    }
}

@Composable
fun MatakuliahCard(matakuliah: Matakuliah) {
    // UI STATE: Digunakan untuk mengontrol popup rincian matakuliah
    var showDialog by remember { mutableStateOf(false) }

    // Card sebagai wadah item list
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = matakuliah.namaMatakuliah,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = matakuliah.namaDosen,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(4.dp))

            // Row untuk menyusun Label SKS dan Tombol Rincian secara sejajar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Label SKS
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = "${matakuliah.sks} SKS",
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 10.sp
                    )
                }

                // ELEMENT UI INPUT: Tombol untuk membuka rincian
                TextButton(onClick = { showDialog = true }) {
                    Text("Rincian", fontSize = 12.sp)
                }
            }
        }
    }

    // ELEMENT UI: AlertDialog akan muncul ketika di klik
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false }, // Menutup dialog jika user mengeklik di luar area kotak
            title = {
                Text(text = "Rincian Matakuliah", fontWeight = FontWeight.Bold)
            },
            text = {
                // Isi rincian matakuliah
                Column {
                    Text(text = "ID Matakuliah: ${matakuliah.id}")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Nama: ${matakuliah.namaMatakuliah}")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "SKS: ${matakuliah.sks}")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Dosen Pengampu: ${matakuliah.namaDosen}")
                }
            },
            confirmButton = {
                // Tombol untuk menutup dialog
                TextButton(onClick = { showDialog = false }) {
                    Text("Tutup")
                }
            }
        )
    }
}