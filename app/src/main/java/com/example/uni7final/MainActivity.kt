package com.example.uni7final

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.uni7final.ui.navigation.AppNavigation
import com.example.uni7final.ui.theme.Uni7finalTheme

// Clase principal donde arranca todo
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Uni7finalTheme {
                // aca llamo a la navegacion
                AppNavigation()
            }
        }
    }
}
