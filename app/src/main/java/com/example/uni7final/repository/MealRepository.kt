package com.example.uni7final.repository

import com.example.uni7final.data.Category
import com.example.uni7final.data.MealDetail
import com.example.uni7final.data.MealSummary
import com.example.uni7final.db.CategoryEntity
import com.example.uni7final.db.MealDao
import com.example.uni7final.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Aca traigo los datos, ya sea de internet o de la base de datos
class MealRepository(
    private val apiService: ApiService,
    private val mealDao: MealDao
) {

    // Buscar las categorias guardadas en el cel
    fun getLocalCategories(): Flow<List<Category>> =
        mealDao.getAllCategories().map { entities ->
            entities.map { it.toCategory() }
        }

    // Traer categorias de internet y guardarlas para verlas despues
    suspend fun refreshCategories(): Result<List<Category>> {
        return try {
            val response = apiService.getCategories()
            val entities = response.categories.map { it.toCategoryEntity() }
            mealDao.clearCategories()
            mealDao.insertCategories(entities)
            Result.success(response.categories)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Traer platos de una categoria por internet
    suspend fun getMealsByCategory(category: String): Result<List<MealSummary>> {
        return try {
            val response = apiService.getMealsByCategory(category)
            Result.success(response.meals ?: emptyList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Traer toda la info de un plato por su ID
    suspend fun getMealDetail(mealId: String): Result<MealDetail> {
        return try {
            val response = apiService.getMealDetail(mealId)
            val meal = response.meals?.firstOrNull()
                ?: return Result.failure(Exception("No se encontro nada"))
            Result.success(meal)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

// Estos son para pasar de un tipo de dato a otro
private fun CategoryEntity.toCategory() = Category(
    id = id,
    name = name,
    thumbnail = thumbnail,
    description = description
)

private fun Category.toCategoryEntity() = CategoryEntity(
    id = id,
    name = name,
    thumbnail = thumbnail,
    description = description
)
