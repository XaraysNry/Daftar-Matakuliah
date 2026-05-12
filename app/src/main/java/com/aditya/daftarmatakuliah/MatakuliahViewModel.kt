package com.aditya.daftarmatakuliah

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MatakuliahViewModel : ViewModel() {

    // Menyimpan daftar asli matakuliah
    private val _daftarMatakuliah = MutableStateFlow<List<Matakuliah>>(emptyList())
    val daftarMatakuliah: StateFlow<List<Matakuliah>> = _daftarMatakuliah.asStateFlow()

    // STATE BARU: Untuk menyimpan teks input dari user untuk pencarian
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        loadDataMatakuliah()
    }

    private fun loadDataMatakuliah() {
        _daftarMatakuliah.value = listOf(
            Matakuliah(1, "Pemrograman Aplikasi", 3, "Bpk. Pius Anggoro"),
            Matakuliah(2, "Deep Learning", 3, "Ibu Dini Fakta Sari"),
            Matakuliah(3, "Desain UI/UX", 2, "Bpk. Pius Anggoro"),
            Matakuliah(4, "Analitika Data", 3, "Ibu. Pulut Suryati"),
            Matakuliah(5, "Kecerdasan Buatan", 3, "Ibu. Sari Iswanti"),
            Matakuliah(6, "Etika Profesi", 2, "Bpk. Fx. Henry Nugroho")
        )
    }

    // Fungsi untuk meng-update teks pencarian dari UI Layer
    fun updateSearchQuery(newQuery: String) {
        _searchQuery.value = newQuery
    }
}