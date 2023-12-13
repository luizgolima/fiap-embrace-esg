package com.example.embraceesg.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.embraceesg.model.Atividade
import com.example.embraceesg.model.enums.Categoria
import com.example.embraceesg.viewModel.AtividadeViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AtividadesScreen() {
    val viewModel: AtividadeViewModel = viewModel()
    val atividades = remember { mutableStateOf(emptyList<Atividade>()) }
    val novoTitulo = remember { mutableStateOf("") }
    val novaDescricao = remember { mutableStateOf("") }
    val categoriaSelecionada = remember { mutableStateOf(Categoria.CULTIVO_HORTA_DOMESTICA) }
    val categoriaSelecionadaExpanded = remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = viewModel) {
        viewModel.getAtividades { result ->
            atividades.value = result ?: emptyList()
        }
    }

    Surface(modifier = Modifier
        .fillMaxSize()
        .clickable { keyboardController?.hide() }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TopAppBar(
                title = {
                    Text(
                        "Embrace ESG",
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                modifier = Modifier.fillMaxWidth(),
            )

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

            Button(
                onClick = {
                    val novaAtividade = Atividade(
                        id = null,
                        titulo = novoTitulo.value,
                        descricao = novaDescricao.value,
                        categoria = categoriaSelecionada.value.name,
                        criadoEm = null,
                        usuarioId = 1
                    )
                    viewModel.createAtividade(novaAtividade) { result ->
                        if (result != null) {
                            novoTitulo.value = ""
                            novaDescricao.value = ""
                            categoriaSelecionada.value = Categoria.CULTIVO_HORTA_DOMESTICA

                            viewModel.getAtividades { result ->
                                atividades.value = result ?: emptyList()
                            }
                        }
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text("Adicionar Atividade")
            }

            AtividadesList(atividades = atividades.value, onEditClick = { atividade ->
                val editaAtividade = Atividade(
                    id = atividade.id,
                    titulo = novoTitulo.value,
                    descricao = novaDescricao.value,
                    categoria = categoriaSelecionada.value.name,
                    criadoEm = atividade.criadoEm,
                    usuarioId = 1
                )
                viewModel.updateAtividade(atividade.id ?: -1, editaAtividade) { result ->
                    if (result != null) {
                        novoTitulo.value = ""
                        novaDescricao.value = ""
                        categoriaSelecionada.value = Categoria.CULTIVO_HORTA_DOMESTICA

                        viewModel.getAtividades { result ->
                            atividades.value = result ?: emptyList()
                        }
                    }
                }
            }, onDeleteClick = { atividade ->
                viewModel.deleteAtividade(atividade.id ?: -1) {
                    viewModel.getAtividades { result ->
                        atividades.value = result ?: emptyList()
                    }
                }
            })
        }
    }
}