package com.eargel.karmag5.model

class Favor(
    var id: Int,
    var categoria: String,
    var detalles: String,
    var estado: String,
    var lugar: String,
    var usuario: User,
    var usuarioAsignado: User,
    var mensajes: List<Mensaje>
) {}