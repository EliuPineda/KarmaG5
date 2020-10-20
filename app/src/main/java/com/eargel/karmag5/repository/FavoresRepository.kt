package com.eargel.karmag5.repository

import androidx.lifecycle.MutableLiveData
import com.eargel.karmag5.model.Favor
import com.eargel.karmag5.model.Mensaje
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
        favoresDisponiblesLiveData.value = mutableListOf()
        favoresHechosLiveData.value = mutableListOf()
        database.child("favores").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val favor = dataSnapshot.getValue(Favor::class.java)
                if (favor != null) {
                    if ((favor.user!!.uid == user.uid) || (favor.userAsignado != null && favor.userAsignado!!.uid == user.uid)) {
                        if (favor.estado == "Inicial" || favor.estado == "Asignado") {
                            favorEnProcesoLiveData.value = favor
                        } else if (favor.estado == "Completo") {
                            val favoresHechos = favoresHechosLiveData.value as MutableList
                            favoresHechos.add(favor)
                            favoresHechosLiveData.value =
                                favoresHechos.sortedByDescending { f -> f.horaCompletado }
                                    .toMutableList()
                        }
                    } else if (favor.user!!.uid != user.uid && favor.estado == "Inicial") {
                        val favoresDisponibles = favoresDisponiblesLiveData.value as MutableList
                        favoresDisponibles.add(favor)
                        favoresDisponiblesLiveData.value =
                            favoresDisponibles.sortedByDescending { f -> f.user?.karma }
                                .toMutableList()
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
                            val favoresHechos = favoresHechosLiveData.value as MutableList
                            favoresHechos.add(favor)
                            favoresHechosLiveData.value =
                                favoresHechos.sortedByDescending { f -> f.horaCompletado }
                                    .toMutableList()
                        } else if (favor.estado == "Inicial") {
                            favorEnProcesoLiveData.value = null
                        } else {
                            if (favor.completado && favor.confirmado) {
                                favor.estado = "Completo"
                                favor.horaCompletado = System.currentTimeMillis()

                                favor.userAsignado!!.apply {
                                    karma = karma + 1
                                    database.child("users").child(uid).setValue(this)
                                }
                                favor.user!!.apply {
                                    karma = karma - 2
                                    database.child("users").child(uid).setValue(this)
                                }

                                database.child("favores").child(favor.id).setValue(favor)
                                favorEnProcesoLiveData.value = null
                            } else {
                                favorEnProcesoLiveData.value = favor
                            }
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
                val favorId = dataSnapshot.key
                if (favorEnProcesoLiveData.value?.id == favorId)
                    favorEnProcesoLiveData.value = null

                val favoresDisponibles =
                    favoresDisponiblesLiveData.value!!.filter { favor -> favor.id != favorId }
                favoresDisponiblesLiveData.value =
                    favoresDisponibles.sortedByDescending { f -> f.user?.karma }.toMutableList()

                val favoresHechos =
                    favoresHechosLiveData.value!!.filter { favor -> favor.id != favorId }
                favoresHechosLiveData.value =
                    favoresHechos.sortedByDescending { f -> f.horaCompletado }.toMutableList()
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun solicitarFavor(user: User, lugar: String, detalle: String, categoria: String) {
        val id = database.child("favores").push().key!!
        database.child("favores").child(id)
            .setValue(Favor(id, categoria, detalle, "Inicial", lugar, user))
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
        }
    }

    fun confirmarFavorDesdeUser(user: User) {
        val favor = favorEnProcesoLiveData.value
        if (favor != null) {
            if (favor.userAsignado?.uid == user.uid)
                favor.completado = true
            else if (favor.user?.uid == user.uid)
                favor.confirmado = true
            database.child("favores").child(favor.id).setValue(favor)
        }
    }

    fun enviarMensajeAFavorEnProceso(texto: String, user: User) {
        val favor = favorEnProcesoLiveData.value
        if (favor != null) {
            val refMensajes = database.child("favores").child(favor.id).child("mensajes")
            val id = refMensajes.push().key!!
            refMensajes.child(id).setValue(Mensaje(id, user, texto, System.currentTimeMillis()))
        }
    }
}