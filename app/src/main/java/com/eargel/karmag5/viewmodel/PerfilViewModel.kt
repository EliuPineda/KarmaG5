package com.eargel.karmag5.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.eargel.karmag5.model.User
import com.eargel.karmag5.repository.FirebaseAuthRepository

class PerfilViewModel : ViewModel() {
    fun authenticatedUserLiveData(): LiveData<User> =
        FirebaseAuthRepository.authenticatedUserLiveData

    fun signOut() {
        FirebaseAuthRepository.signOut()
    }
}