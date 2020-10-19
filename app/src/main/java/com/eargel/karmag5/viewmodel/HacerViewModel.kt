package com.eargel.karmag5.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.eargel.karmag5.model.Favor
import com.eargel.karmag5.repository.FavoresRepository
import com.eargel.karmag5.repository.FirebaseAuthRepository

class HacerViewModel : ViewModel() {
    fun favoresDisponiblesLiveData(): LiveData<List<Favor>> =
        FavoresRepository.favoresDisponiblesLiveData

    fun asignarFavor(favor: Favor) {
        val user = FirebaseAuthRepository.authenticatedUserLiveData.value
        if (user != null)
            FavoresRepository.asignarFavor(favor, user)
    }
}
