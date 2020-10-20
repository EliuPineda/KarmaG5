package com.eargel.karmag5.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.eargel.karmag5.model.Favor
import com.eargel.karmag5.repository.FavoresRepository
import com.eargel.karmag5.repository.FirebaseAuthRepository

class ChatViewModel : ViewModel() {
    fun favorEnProcesoLiveData(): LiveData<Favor> =
        FavoresRepository.favorEnProcesoLiveData

    fun enviarMensaje(texto: String) {
        val user = FirebaseAuthRepository.authenticatedUserLiveData.value
        if (user != null)
            FavoresRepository.enviarMensajeAFavorEnProceso(texto, user)
    }
}