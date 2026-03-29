package com.example.uni7final.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// Aca pongo lo que se puede hacer con la base de datos
@Dao
interface MealDao {

    // Traer todas las categorias
    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    // Guardar las categorias que vienen de internet
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<CategoryEntity>)

    // Borrar todo para limpiar
    @Query("DELETE FROM categories")
    suspend fun clearCategories()
}
