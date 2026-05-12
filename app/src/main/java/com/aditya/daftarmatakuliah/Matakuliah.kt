package com.aditya.daftarmatakuliah

// DATA CLASS: Matakuliah
// data class digunakan untuk menyimpan data/state.
data class Matakuliah(
    val id: Int,          // Gunakan Int, bukan String (menyesuaikan angka 1, 2, 3 di ViewModel)
    val namaMatakuliah: String,
    val sks: Int,         // Ganti deskripsi menjadi sks bertipe Int
    val namaDosen: String,
    val imageRes: Int     // Atribut gambar sudah benar!
)