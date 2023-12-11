package com.example.embraceesg.service

import com.example.embraceesg.model.Atividade
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

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