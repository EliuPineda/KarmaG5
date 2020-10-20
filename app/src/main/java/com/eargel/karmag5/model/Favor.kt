package com.eargel.karmag5.model

class Favor(
    var id: String = "",
    var categoria: String = "",
    var detalle: String = "",
    var estado: String = "",
    var lugar: String = "",
    var user: User? = null,
    var userAsignado: User? = null,
    var mensajes: HashMap<String, Mensaje> = HashMap(),
    var completado: Boolean = false,
    var confirmado: Boolean = false,
    var horaCompletado: Long = 0
) {}