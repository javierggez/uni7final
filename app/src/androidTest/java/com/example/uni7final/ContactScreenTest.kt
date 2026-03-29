package com.example.uni7final

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit4.runners.AndroidJUnit4
import com.example.uni7final.ui.screens.ContactScreen
import com.example.uni7final.ui.theme.Uni7finalTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test instrumental para la pantalla de Contacto.
 * Verifica la existencia y comportamiento del botón de email.
 */
@RunWith(AndroidJUnit4::class)
class ContactScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun contactScreen_botonEmailExiste() {
        // Renderiza la pantalla de contacto de forma aislada
        composeTestRule.setContent {
            Uni7finalTheme {
                ContactScreen(navController = rememberNavController())
            }
        }

        // Verifica que el botón de email existe en la UI
        composeTestRule
            .onNodeWithTag("btn_send_email")
            .assertExists()
    }

    @Test
    fun contactScreen_botonEmailDeshabilitadoConCamposVacios() {
        composeTestRule.setContent {
            Uni7finalTheme {
                ContactScreen(navController = rememberNavController())
            }
        }

        // El botón debe estar deshabilitado cuando los campos están vacíos
        composeTestRule
            .onNodeWithTag("btn_send_email")
            .assertIsNotEnabled()
    }

    @Test
    fun contactScreen_mostrarTextoContacto() {
        composeTestRule.setContent {
            Uni7finalTheme {
                ContactScreen(navController = rememberNavController())
            }
        }

        // Verifica que el título de la pantalla es visible
        composeTestRule
            .onNodeWithText("Contacto")
            .assertExists()
    }
}
