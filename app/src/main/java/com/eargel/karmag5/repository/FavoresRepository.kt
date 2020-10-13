package com.eargel.karmag5.repository

import androidx.lifecycle.MutableLiveData
import com.eargel.karmag5.model.Favor
import com.eargel.karmag5.model.User
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object FavoresRepository {
    val database = Firebase.database.reference

    val favorEnProcesoLiveData = MutableLiveData<Favor>()
    val favoresDisponiblesLiveData = MutableLiveData<List<Favor>>()

    fun buscarFavorEnProceso(user: User) {
        database.child("favores").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val favor = dataSnapshot.getValue(Favor::class.java)
                if (favor != null) {
                    if (favor.estado == "Inicial" || favor.estado == "Asignado") {
                        if ((favor.user!!.uid == user.uid) || (favor.userAsignado != null && favor.userAsignado!!.uid == user.uid)) {
                            favorEnProcesoLiveData.value = favor
                        }
                    }
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val favorEnProceso = favorEnProcesoLiveData.value
                val favor = dataSnapshot.getValue(Favor::class.java)
                if (favorEnProceso != null && favor != null && favor.id == favorEnProceso.id) {
                    if (favor.estado == "Completo")
                        favorEnProcesoLiveData.value = null
                    else
                        favorEnProcesoLiveData.value = favor
                }
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun solicitarFavor(user: User, lugar: String, detalle: String, categoria: String) {
        val id = database.child("favores").push().key!!
        val favor = Favor(id, categoria, detalle, "Inicial", lugar, user)
        database.child("favores").child(id).setValue(favor)
    }
}