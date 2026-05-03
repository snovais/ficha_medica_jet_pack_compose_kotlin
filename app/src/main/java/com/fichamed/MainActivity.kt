package com.fichamed // Define o pacote (namespace) do projeto

// Importa classes essenciais do Android e Jetpack Compose
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent // Permite usar Compose para definir a UI
import androidx.activity.enableEdgeToEdge // Habilita layout em tela cheia
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen // Controle da splash screen
import com.fichamed.navigation.NavGraph // Responsável pela navegação entre telas
import com.fichamed.ui.FichaMedTheme // Tema do aplicativo (cores, tipografia, etc.)

// Classe principal da aplicação (ponto de entrada)
class MainActivity : ComponentActivity() {

    // Método chamado quando a Activity é criada
    override fun onCreate(savedInstanceState: Bundle?) {

        // Instala a splash screen (tela inicial ao abrir o app)
        installSplashScreen()

        // Chama o comportamento padrão da Activity (obrigatório)
        super.onCreate(savedInstanceState)

        // Permite que a interface ocupe toda a tela (inclusive atrás da status bar)
        enableEdgeToEdge()

        // Define a interface do app usando Jetpack Compose
        setContent {

            // Aplica o tema personalizado do aplicativo
            FichaMedTheme {

                // Define o fluxo de navegação entre telas do app
                NavGraph()
            }
        }
    }
}