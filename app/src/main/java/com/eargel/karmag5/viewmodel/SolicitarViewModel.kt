package com.eargel.karmag5.viewmodel

import androidx.lifecycle.ViewModel
import com.eargel.karmag5.repository.FavoresRepository
import com.eargel.karmag5.repository.FirebaseAuthRepository

class SolicitarViewModel : ViewModel() {
    fun solicitarFavor(lugar: String, detalle: String, categoria: String) {
        val user = FirebaseAuthRepository.authenticatedUserLiveData.value
        if (user != null)
            FavoresRepository.solicitarFavor(user, lugar, detalle, categoria)
    }
}