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
    val favoresHechosLiveData = MutableLiveData<List<Favor>>()

    fun buscarFavores(user: User) {
        database.child("favores").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val favor = dataSnapshot.getValue(Favor::class.java)
                if (favor != null) {
                    if ((favor.user!!.uid == user.uid) || (favor.userAsignado != null && favor.userAsignado!!.uid == user.uid)) {
                        if (favor.estado == "Inicial" || favor.estado == "Asignado") {
                            favorEnProcesoLiveData.value = favor
                        } else if (favor.estado == "Completo") {
                            addFavorToFavoresHechos(favor)
                        }
                    } else if (favor.user!!.uid != user.uid && favor.estado == "Inicial") {
                        addFavorToFavoresDisponibles(favor)
                    }
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val favorEnProceso = favorEnProcesoLiveData.value
                val favor = dataSnapshot.getValue(Favor::class.java)
                if (favor != null) {
                    if (favorEnProceso != null && favor.id == favorEnProceso.id) {
                        if (favor.estado == "Completo") {
                            favorEnProcesoLiveData.value = null
                            addFavorToFavoresHechos(favor)
                        } else {
                            favorEnProcesoLiveData.value = favor
                        }
                    }

                    val favoresDisponibles: MutableList<Favor>? =
                        favoresDisponiblesLiveData.value as MutableList<Favor>?
                    if (favoresDisponibles != null) {
                        val indiceFavorEnLista =
                            favoresDisponibles.indexOfFirst { f -> f.id == favor.id }
                        if (indiceFavorEnLista < -1) {
                            if (favor.estado == "Inicial") {
                                favoresDisponibles.set(indiceFavorEnLista, favor)
                            } else {
                                favoresDisponibles.removeAt(indiceFavorEnLista)
                            }
                        }
                    }
                    favoresDisponiblesLiveData.value = favoresDisponibles
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

    fun addFavorToFavoresHechos(favor: Favor) {
        var favoresHechos: MutableList<Favor>? = favoresHechosLiveData.value as MutableList<Favor>?
        if (favoresHechos == null)
            favoresHechos = mutableListOf(favor)
        else
            favoresHechos.add(favor)
        favoresHechosLiveData.value =
            favoresHechos.sortedBy { f -> f.horaCompletado }.toMutableList()
    }

    fun addFavorToFavoresDisponibles(favor: Favor) {
        var favoresDisponibles = favoresDisponiblesLiveData.value as MutableList<Favor>?
        if (favoresDisponibles == null)
            favoresDisponibles = mutableListOf(favor)
        else
            favoresDisponibles.add(favor)
        favoresDisponiblesLiveData.value =
            favoresDisponibles.sortedByDescending { f -> f.user?.karma }.toMutableList()
    }

    fun solicitarFavor(user: User, lugar: String, detalle: String, categoria: String) {
        val id = database.child("favores").push().key!!
        val favor = Favor(id, categoria, detalle, "Inicial", lugar, user)
        database.child("favores").child(id).setValue(favor)
    }

    fun asignarFavor(favor: Favor, user: User) {
        favor.userAsignado = user
        favor.estado = "Asignado"
        database.child("favores").child(favor.id).setValue(favor)
    }

    fun cancelarFavorParaUser(user: User) {
        val favor = favorEnProcesoLiveData.value
        if (favor != null) {
            if (favor.userAsignado?.uid == user.uid) {
                favor.userAsignado = null
                favor.estado = "Inicial"
                database.child("favores").child(favor.id).setValue(favor)
            } else if (favor.user?.uid == user.uid) {
                database.child("favores").child(favor.id).setValue(null)
            }
            favorEnProcesoLiveData.value = null
        }
    }
}