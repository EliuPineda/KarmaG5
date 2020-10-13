package com.eargel.karmag5.repository

import androidx.lifecycle.MutableLiveData
import com.eargel.karmag5.model.Favor
import com.eargel.karmag5.model.User
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object FavoresRepository {
    val database = Firebase.database.reference

    val favorEnProcesoLiveData = MutableLiveData<Favor>()
    val favoresDisponiblesLiveData = MutableLiveData<List<Favor>>()

    fun solicitarFavor(user: User, lugar: String, detalle: String, categoria: String) {
        val id = database.child("favores").push().key!!
        val favor = Favor(id, categoria, detalle, "Inicial", lugar, user)
        database.child("favores").child(id).setValue(favor)
        favorEnProcesoLiveData.value = favor
    }
}