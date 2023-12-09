package com.example.embraceesg

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AtividadeRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://localhost:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var service: AtividadeService = retrofit.create(AtividadeService::class.java)

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        service = retrofit.create(AtividadeService::class.java)
    }
}
