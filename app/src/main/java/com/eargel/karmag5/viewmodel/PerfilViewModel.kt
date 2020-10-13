package com.eargel.karmag5.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.eargel.karmag5.model.Favor
import com.eargel.karmag5.model.User
import com.eargel.karmag5.repository.FavoresRepository
import com.eargel.karmag5.repository.FirebaseAuthRepository

class PerfilViewModel : ViewModel() {
    fun authenticatedUserLiveData(): LiveData<User> =
        FirebaseAuthRepository.authenticatedUserLiveData

    fun favorEnProcesoLiveData(): LiveData<Favor> = FavoresRepository.favorEnProcesoLiveData

    fun buscarFavorEnProceso(user: User) {
        FavoresRepository.buscarFavorEnProceso(user)
    }

    fun signOut() {
        FirebaseAuthRepository.signOut()
    }
}