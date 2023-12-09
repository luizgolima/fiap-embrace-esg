package com.example.embraceesg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.embraceesg.ui.theme.EmbraceESGTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmbraceESGTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AtividadesScreen()
                }
            }
        }
    }
}

@Composable
fun AtividadesScreen() {
    val viewModel: AtividadeViewModel = viewModel()
    val atividades = remember { mutableStateOf(emptyList<Atividade>()) }
    val novoTitulo = remember { mutableStateOf("") }
    val novaDescricao = remember { mutableStateOf("") }
    val categoriaSelecionada = remember { mutableStateOf(Categoria.CULTIVO_HORTA_DOMESTICA) }
    val categoriaSelecionadaExpanded = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = viewModel) {
        viewModel.getAtividades { result ->
            atividades.value = result ?: emptyList()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Campos para adicionar nova atividade
        OutlinedTextField(value = novoTitulo.value,
            onValueChange = { novoTitulo.value = it },
            label = { Text("Título") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        OutlinedTextField(value = novaDescricao.value,
            onValueChange = { novaDescricao.value = it },
            label = { Text("Descrição") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedButton(
            onClick = {
                categoriaSelecionadaExpanded.value = !categoriaSelecionadaExpanded.value
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text(categoriaSelecionada.value.name)
        }

        // Campo de seleção para a categoria
        DropdownMenu(
            expanded = categoriaSelecionadaExpanded.value,
            onDismissRequest = { categoriaSelecionadaExpanded.value = false },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Categoria.entries.forEach { categoria ->
                DropdownMenuItem(text = {
                    Text(categoria.name)
                }, onClick = {
                    categoriaSelecionada.value = categoria
                    categoriaSelecionadaExpanded.value = false
                })
            }
        }

        // Botão para adicionar nova atividade
        Button(
            onClick = {
                val novaAtividade = Atividade(
                    id = null,
                    titulo = novoTitulo.value,
                    descricao = novaDescricao.value,
                    categoria = categoriaSelecionada.value.name,
                    usuarioId = 1
                )
                viewModel.createAtividade(novaAtividade) { result ->
                    if (result != null) {
                        atividades.value = atividades.value + result
                        novoTitulo.value = ""
                        novaDescricao.value = ""
                        categoriaSelecionada.value = Categoria.CULTIVO_HORTA_DOMESTICA
                    }
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Adicionar Atividade")
        }

        // Lista de atividades existentes
        AtividadesList(atividades = atividades.value, onEditClick = { atividade ->
            viewModel.updateAtividade(atividade.id ?: -1, atividade) { result ->
                if (result != null) {
                    atividades.value = atividades.value.map {
                        if (it.id == result.id) result else it
                    }
                }
            }
        }, onDeleteClick = { atividade ->
            viewModel.deleteAtividade(atividade.id ?: -1) {
                atividades.value = atividades.value.filter { it.id != atividade.id }
            }
        })
    }
}

@Composable
fun AtividadeItem(atividade: Atividade, onEditClick: () -> Unit, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = atividade.titulo, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = atividade.descricao, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = atividade.categoria, style = MaterialTheme.typography.bodySmall)

            // Botões para editar e excluir
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = onEditClick) {
                    Text("Editar")
                }

                Button(
                    onClick = onDeleteClick, modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text("Excluir")
                }
            }
        }
    }
}

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

@Preview(showBackground = true)
@Composable
fun AtividadesListPreview() {
    EmbraceESGTheme {}
}
