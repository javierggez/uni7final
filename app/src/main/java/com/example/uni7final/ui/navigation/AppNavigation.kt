package com.example.uni7final.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uni7final.db.AppDatabase
import com.example.uni7final.network.RetrofitInstance
import com.example.uni7final.repository.MealRepository
import com.example.uni7final.ui.screens.CategoryListScreen
import com.example.uni7final.ui.screens.ContactScreen
import com.example.uni7final.ui.screens.MealDetailScreen
import com.example.uni7final.ui.screens.MealListScreen
import com.example.uni7final.ui.screens.SplashScreen
import com.example.uni7final.viewmodel.MealViewModel
import com.example.uni7final.viewmodel.MealViewModelFactory

// Aca pongo los nombres de las pantallas para no equivocarme
object Routes {
    const val SPLASH = "splash"
    const val CATEGORY_LIST = "categories"
    const val MEAL_LIST = "meals/{categoryName}"
    const val MEAL_DETAIL = "meal_detail/{mealId}"
    const val CONTACT = "contact"

    fun mealList(category: String) = "meals/$category"
    fun mealDetail(mealId: String) = "meal_detail/$mealId"
}

// Aca armo todo el mapa de navegacion
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // Traigo la base de datos y el repo para que funcione el viewmodel
    val db = AppDatabase.getInstance(context)
    val repository = MealRepository(RetrofitInstance.api, db.mealDao())
    val viewModel: MealViewModel = viewModel(factory = MealViewModelFactory(repository))

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        // La primera pantalla que sale
        composable(Routes.SPLASH) {
            SplashScreen(navController = navController)
        }

        // Pantalla de categorias
        composable(Routes.CATEGORY_LIST) {
            CategoryListScreen(navController = navController, viewModel = viewModel)
        }

        // Pantalla de comidas de una categoria
        composable(
            route = Routes.MEAL_LIST,
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("categoryName") ?: ""
            MealListScreen(
                navController = navController,
                viewModel = viewModel,
                categoryName = category
            )
        }

        // Pantalla del plato con sus detalles
        composable(
            route = Routes.MEAL_DETAIL,
            arguments = listOf(navArgument("mealId") { type = NavType.StringType })
        ) { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString("mealId") ?: ""
            MealDetailScreen(
                navController = navController,
                viewModel = viewModel,
                mealId = mealId
            )
        }

        // Pantalla de contacto
        composable(Routes.CONTACT) {
            ContactScreen(navController = navController)
        }
    }
}
