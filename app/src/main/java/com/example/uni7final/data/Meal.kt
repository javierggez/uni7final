package com.example.uni7final.data

import com.google.gson.annotations.SerializedName

// Respuesta de la API para el listado de comidas por categoría
data class MealsResponse(
    @SerializedName("meals") val meals: List<MealSummary>?
)

// Modelo resumido de una comida (para el listado)
data class MealSummary(
    @SerializedName("strMeal") val name: String,
    @SerializedName("strMealThumb") val thumbnail: String,
    @SerializedName("idMeal") val id: String
)
