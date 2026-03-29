package com.example.uni7final.data

import com.google.gson.annotations.SerializedName

// Respuesta de la API para el listado de categorías
data class CategoriesResponse(
    @SerializedName("categories") val categories: List<Category>
)

// Modelo de datos para una categoría de comida
data class Category(
    @SerializedName("idCategory") val id: String,
    @SerializedName("strCategory") val name: String,
    @SerializedName("strCategoryThumb") val thumbnail: String,
    @SerializedName("strCategoryDescription") val description: String
)
