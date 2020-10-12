package com.eargel.karmag5.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.eargel.karmag5.model.User
import com.eargel.karmag5.repository.FirebaseAuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthCredential


class LoginViewModel : ViewModel() {
    fun authenticatedUserLiveData(): LiveData<User> =
        FirebaseAuthRepository.authenticatedUserLiveData

    fun signInWithGoogle(googleSignInAccount: GoogleSignInAccount?) {
        FirebaseAuthRepository.signIn(googleSignInAccount)
    }
}