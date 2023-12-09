package com.example.embraceesg

import retrofit2.Call
import retrofit2.http.*

interface AtividadeService {

    @GET("atividades")
    fun getAtividades(): Call<List<Atividade>>

    @GET("atividades/{id}")
    fun getAtividade(@Path("id") id: Int): Call<Atividade>

    @POST("atividades")
    fun createAtividade(@Body atividade: Atividade): Call<Atividade>

    @PUT("atividades/{id}")
    fun updateAtividade(@Path("id") id: Int, @Body atividade: Atividade): Call<Atividade>

    @DELETE("atividades/{id}")
    fun deleteAtividade(@Path("id") id: Int): Call<Void>

}