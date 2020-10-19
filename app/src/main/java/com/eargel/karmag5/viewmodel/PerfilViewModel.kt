package com.eargel.karmag5.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eargel.karmag5.model.Favor
import com.eargel.karmag5.model.User
import com.eargel.karmag5.repository.FavoresRepository
import com.eargel.karmag5.repository.FirebaseAuthRepository

class PerfilViewModel : ViewModel() {
    fun authenticatedUserLiveData(): LiveData<User> =
        FirebaseAuthRepository.authenticatedUserLiveData

    fun favoresHechosLiveData(): LiveData<List<Favor>> = FavoresRepository.favoresHechosLiveData

    fun favorEnProcesoLiveData(): LiveData<Favor> = FavoresRepository.favorEnProcesoLiveData

    fun buscarFavorEnProceso(user: User) {
        FavoresRepository.buscarFavores(user)
    }

    fun cancelarFavorEnProceso(user: User) {
        FavoresRepository.cancelarFavorParaUser(user)
    }

    fun confirmarFavorEnProceso(user: User) {
        FavoresRepository.confirmarFavorDesdeUser(user)
    }

    fun signOut() {
        FirebaseAuthRepository.signOut()
    }
}