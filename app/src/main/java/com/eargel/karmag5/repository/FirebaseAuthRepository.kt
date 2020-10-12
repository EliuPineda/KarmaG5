package com.eargel.karmag5.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.eargel.karmag5.model.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ValueEventListener as ValueEventListener

class FirebaseAuthRepository {
    val auth = FirebaseAuth.getInstance()
    val database = Firebase.database.reference
    var userCreated = MutableLiveData<Boolean>()

    fun signIn(googleSignInAccount: GoogleSignInAccount?): MutableLiveData<User>? {
        val authenticatedUserMutableLiveData: MutableLiveData<User> = MutableLiveData<User>()
        if (googleSignInAccount != null) {
            val googleTokenId = googleSignInAccount.idToken
            val googleAuthCredential = GoogleAuthProvider.getCredential(googleTokenId, null)
            auth.signInWithCredential(googleAuthCredential)
                .addOnCompleteListener { authTask: Task<AuthResult> ->
                    if (authTask.isSuccessful) {
                        val authUser = auth.getCurrentUser()
                        if (authUser != null) {
                            database.child("users").child(authUser.uid)
                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                                        var user: User? = null
                                        if (dataSnapshot.exists())
                                            user = dataSnapshot.getValue(User::class.java)
                                        if (user == null)
                                            user = createUser(authUser)
                                        authenticatedUserMutableLiveData.value = user
                                    }

                                    override fun onCancelled(databaseError: DatabaseError) {
                                        // handle error
                                    }
                                })
                        }
                    }
                }
        }
        return authenticatedUserMutableLiveData
    }

    fun createUser(firebaseUser: FirebaseUser): User {
        val user = User(
            firebaseUser.uid,
            firebaseUser.displayName!!,
            2
        )
        database.child("users").child(user.uid).setValue(user)
        return user
    }

}