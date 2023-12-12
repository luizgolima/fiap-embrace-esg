package com.example.embraceesg.model

data class Atividade(
    val id: Int?,
    val titulo: String,
    val descricao: String,
    val categoria: String,
    val criadoEm: String?,
    val usuarioId: Int = 1,
)