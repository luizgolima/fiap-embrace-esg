package com.example.embraceesg.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.embraceesg.model.Atividade

@Composable
fun AtividadesList(
    atividades: List<Atividade>,
    onEditClick: (Atividade) -> Unit,
    onDeleteClick: (Atividade) -> Unit
) {
    LazyColumn {
        items(atividades) { atividade ->
            AtividadeItem(atividade = atividade,
                onEditClick = { onEditClick(atividade) },
                onDeleteClick = { onDeleteClick(atividade) })
        }
    }
}