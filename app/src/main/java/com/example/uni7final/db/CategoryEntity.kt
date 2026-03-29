package com.example.uni7final.db

import androidx.room.Entity
import androidx.room.PrimaryKey

// Aca guardo los datos de las categorias para que se vean sin internet
@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey val id: String,
    val name: String,
    val thumbnail: String,
    val description: String
)
