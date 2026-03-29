package com.example.uni7final

import com.example.uni7final.data.CategoriesResponse
import com.example.uni7final.data.Category
import com.example.uni7final.db.MealDao
import com.example.uni7final.network.ApiService
import com.example.uni7final.repository.MealRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

/**
 * Test unitario para MealRepository.
 * Verifica la lógica de negocio sin depender de la red ni de la BD real.
 */
class MealRepositoryTest {

    private lateinit var apiService: ApiService
    private lateinit var mealDao: MealDao
    private lateinit var repository: MealRepository

    @Before
    fun setUp() {
        apiService = mock()
        mealDao = mock()
        repository = MealRepository(apiService, mealDao)
    }

    @Test
    fun `refreshCategories retorna success cuando la API responde correctamente`() = runTest {
        // Given: la API devuelve una lista válida de categorías
        val categories = listOf(
            Category("1", "Beef", "https://img.com/beef.png", "Carne de res")
        )
        val response = CategoriesResponse(categories)
        whenever(apiService.getCategories()).thenReturn(response)

        // When: refrescamos las categorías
        val result = repository.refreshCategories()

        // Then: el resultado debe ser exitoso y guardar en Room
        assertTrue("El resultado debe ser exitoso", result.isSuccess)
        assertEquals(1, result.getOrNull()?.size)
        assertEquals("Beef", result.getOrNull()?.first()?.name)
        verify(mealDao).clearCategories()
        verify(mealDao).insertCategories(any())
    }

    @Test
    fun `refreshCategories retorna failure cuando la API lanza excepcion`() = runTest {
        // Given: la API lanza una excepción de red
        whenever(apiService.getCategories()).thenAnswer {
            throw RuntimeException("Sin conexión a internet")
        }

        // When: intentamos refrescar
        val result = repository.refreshCategories()

        // Then: el resultado debe ser fallido
        assertTrue("El resultado debe ser un error", result.isFailure)
        assertEquals("Sin conexión a internet", result.exceptionOrNull()?.message)
    }

    @Test
    fun `getMealsByCategory retorna lista vacía cuando la API no encuentra comidas`() = runTest {
        // Given: la API retorna meals = null
        val response = com.example.uni7final.data.MealsResponse(meals = null)
        whenever(apiService.getMealsByCategory("Desconocida")).thenReturn(response)

        // When
        val result = repository.getMealsByCategory("Desconocida")

        // Then: debe retornar lista vacía sin error
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isEmpty() == true)
    }
}
