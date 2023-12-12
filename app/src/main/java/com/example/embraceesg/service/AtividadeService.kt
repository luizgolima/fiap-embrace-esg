package com.example.embraceesg.service

import com.example.embraceesg.model.Atividade
import retrofit2.Call
import retrofit2.http.*

interface AtividadeService {

    @GET("atividades")
    fun getAtividades(): Call<List<Atividade>>

    @GET("atividades/{atividade_id}")
    fun getAtividade(@Path("atividade_id") id: Int): Call<Atividade>

    @POST("atividades")
    fun createAtividade(@Body atividade: Atividade): Call<Atividade>

    @PUT("atividades/{atividade_id}")
    fun updateAtividade(@Path("atividade_id") id: Int, @Body atividade: Atividade): Call<Atividade>

    @DELETE("atividades/{atividade_id}")
    fun deleteAtividade(@Path("atividade_id") id: Int): Call<Void>

}