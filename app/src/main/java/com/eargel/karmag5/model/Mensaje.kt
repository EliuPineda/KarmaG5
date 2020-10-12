package com.eargel.karmag5.model

import java.time.LocalDateTime

class Mensaje(
    var id: Int,
    var user: User,
    var texto: String,
    var hora: LocalDateTime
) {}