package com.example.embraceesg.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.embraceesg.model.Atividade
import com.example.embraceesg.repository.AtividadeRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AtividadeViewModel : ViewModel() {

    private val repository = AtividadeRepository()

    fun getAtividades(onResult: (List<Atividade>?) -> Unit) {
        viewModelScope.launch {
            repository.service.getAtividades().enqueue(object : Callback<List<Atividade>> {
                override fun onResponse(
                    call: Call<List<Atividade>>,
                    response: Response<List<Atividade>>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<List<Atividade>>, t: Throwable) {
                    onResult(null)
                }
            })
        }
    }

    fun getAtividade(id: Int, onResult: (Atividade?) -> Unit) {
        viewModelScope.launch {
            repository.service.getAtividade(id).enqueue(object : Callback<Atividade> {
                override fun onResponse(call: Call<Atividade>, response: Response<Atividade>) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<Atividade>, t: Throwable) {
                    onResult(null)
                }
            })
        }
    }

    fun createAtividade(atividade: Atividade, onResult: (Atividade?) -> Unit) {
        viewModelScope.launch {
            repository.service.createAtividade(atividade).enqueue(object : Callback<Atividade> {
                override fun onResponse(call: Call<Atividade>, response: Response<Atividade>) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<Atividade>, t: Throwable) {
                    onResult(null)
                }
            })
        }
    }

    fun updateAtividade(id: Int, atividade: Atividade, onResult: (Atividade?) -> Unit) {
        viewModelScope.launch {
            repository.service.updateAtividade(id, atividade).enqueue(object : Callback<Atividade> {
                override fun onResponse(call: Call<Atividade>, response: Response<Atividade>) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<Atividade>, t: Throwable) {
                    onResult(null)
                }
            })
        }
    }

    fun deleteAtividade(id: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            repository.service.deleteAtividade(id).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    onResult(response.isSuccessful)
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    onResult(false)
                }
            })
        }
    }
}