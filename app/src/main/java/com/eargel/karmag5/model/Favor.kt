package com.eargel.karmag5.model

import com.google.firebase.Timestamp

class Favor(
    var id: String = "",
    var categoria: String = "",
    var detalle: String = "",
    var estado: String = "",
    var lugar: String = "",
    var user: User? = null,
    var userAsignado: User? = null,
    var mensajes: List<Mensaje> = listOf(),
    var completado: Boolean = false,
    var confirmado: Boolean = false,
    var horaCompletado: Timestamp? = null
) {}