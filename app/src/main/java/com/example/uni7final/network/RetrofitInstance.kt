package com.example.uni7final.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Singleton de Retrofit configurado para TheMealDB
object RetrofitInstance {

    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
