package com.example.uni7final.network

import com.example.uni7final.data.CategoriesResponse
import com.example.uni7final.data.MealDetailResponse
import com.example.uni7final.data.MealsResponse
import retrofit2.http.GET
import retrofit2.http.Query

// Interfaz Retrofit para los endpoints de TheMealDB API
interface ApiService {

    // Obtiene todas las categorías de comidas
    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse

    // Obtiene comidas filtradas por categoría
    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): MealsResponse

    // Obtiene el detalle completo de una comida por su ID
    @GET("lookup.php")
    suspend fun getMealDetail(@Query("i") mealId: String): MealDetailResponse
}
