package com.eargel.karmag5.model

class Mensaje(
    var id: String = "",
    var user: User? = null,
    var texto: String = "",
    var hora: Long = 0
) {}