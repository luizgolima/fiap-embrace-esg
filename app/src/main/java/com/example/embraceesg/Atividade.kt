package com.example.embraceesg

data class Atividade(
    val id: Int?,
    val titulo: String,
    val descricao: String,
    val categoria: String,
    val usuarioId: Int = 1,
)