package com.example.uni7final.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uni7final.data.Category
import com.example.uni7final.data.MealDetail
import com.example.uni7final.data.MealSummary
import com.example.uni7final.repository.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// Estados para saber si esta cargando o si hubo error
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

// Aca manejo los datos que se ven en las pantallas
class MealViewModel(private val repository: MealRepository) : ViewModel() {

    // Lista de categorias que viene de la base de datos
    val categories: StateFlow<UiState<List<Category>>> = repository.getLocalCategories()
        .map { list ->
            if (list.isEmpty()) UiState.Loading
            else UiState.Success(list)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )

    private val _meals = MutableStateFlow<UiState<List<MealSummary>>>(UiState.Loading)
    val meals: StateFlow<UiState<List<MealSummary>>> = _meals.asStateFlow()

    private val _mealDetail = MutableStateFlow<UiState<MealDetail>>(UiState.Loading)
    val mealDetail: StateFlow<UiState<MealDetail>> = _mealDetail.asStateFlow()

    init {
        // Cargar las categorias apenas empieza
        loadCategories()
    }

    // Traer categorias de internet
    fun loadCategories() {
        viewModelScope.launch {
            repository.refreshCategories()
        }
    }

    // Traer platos de una categoria
    fun loadMealsByCategory(category: String) {
        viewModelScope.launch {
            _meals.value = UiState.Loading
            repository.getMealsByCategory(category)
                .onSuccess { _meals.value = UiState.Success(it) }
                .onFailure { _meals.value = UiState.Error(it.message ?: "Algo salio mal") }
        }
    }

    // Ver el detalle de un plato
    fun loadMealDetail(mealId: String) {
        viewModelScope.launch {
            _mealDetail.value = UiState.Loading
            repository.getMealDetail(mealId)
                .onSuccess { _mealDetail.value = UiState.Success(it) }
                .onFailure { _mealDetail.value = UiState.Error(it.message ?: "Algo salio mal") }
        }
    }
}
