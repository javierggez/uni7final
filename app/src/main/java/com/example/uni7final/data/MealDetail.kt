package com.example.uni7final.data

import com.google.gson.annotations.SerializedName

// Respuesta de la API para el detalle de una comida
data class MealDetailResponse(
    @SerializedName("meals") val meals: List<MealDetail>?
)

// Modelo completo de una comida con todos sus datos (para la pantalla de detalle)
data class MealDetail(
    @SerializedName("idMeal") val id: String,
    @SerializedName("strMeal") val name: String,
    @SerializedName("strCategory") val category: String,
    @SerializedName("strArea") val area: String,
    @SerializedName("strInstructions") val instructions: String,
    @SerializedName("strMealThumb") val thumbnail: String,
    @SerializedName("strYoutube") val youtube: String?,
    @SerializedName("strIngredient1") val ingredient1: String?,
    @SerializedName("strIngredient2") val ingredient2: String?,
    @SerializedName("strIngredient3") val ingredient3: String?,
    @SerializedName("strIngredient4") val ingredient4: String?,
    @SerializedName("strIngredient5") val ingredient5: String?,
    @SerializedName("strIngredient6") val ingredient6: String?,
    @SerializedName("strIngredient7") val ingredient7: String?,
    @SerializedName("strIngredient8") val ingredient8: String?,
    @SerializedName("strIngredient9") val ingredient9: String?,
    @SerializedName("strIngredient10") val ingredient10: String?,
    @SerializedName("strMeasure1") val measure1: String?,
    @SerializedName("strMeasure2") val measure2: String?,
    @SerializedName("strMeasure3") val measure3: String?,
    @SerializedName("strMeasure4") val measure4: String?,
    @SerializedName("strMeasure5") val measure5: String?,
    @SerializedName("strMeasure6") val measure6: String?,
    @SerializedName("strMeasure7") val measure7: String?,
    @SerializedName("strMeasure8") val measure8: String?,
    @SerializedName("strMeasure9") val measure9: String?,
    @SerializedName("strMeasure10") val measure10: String?
) {
    // Retorna la lista de ingredientes no nulos como strings formateados
    fun getIngredients(): List<String> {
        return listOfNotNull(
            formatIngredient(measure1, ingredient1),
            formatIngredient(measure2, ingredient2),
            formatIngredient(measure3, ingredient3),
            formatIngredient(measure4, ingredient4),
            formatIngredient(measure5, ingredient5),
            formatIngredient(measure6, ingredient6),
            formatIngredient(measure7, ingredient7),
            formatIngredient(measure8, ingredient8),
            formatIngredient(measure9, ingredient9),
            formatIngredient(measure10, ingredient10)
        )
    }

    private fun formatIngredient(measure: String?, ingredient: String?): String? {
        if (ingredient.isNullOrBlank()) return null
        val m = measure?.trim().orEmpty()
        return if (m.isEmpty()) ingredient.trim() else "$m ${ingredient.trim()}"
    }
}
