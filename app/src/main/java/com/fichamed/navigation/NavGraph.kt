package com.fichamed.navigation // Pacote responsável pela navegação entre telas

// Importações necessárias para navegação e animações
import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Importação das telas do app
import com.fichamed.ui.cadastro.CadastroScreen
import com.fichamed.ui.ficha.FichaScreen
import com.fichamed.ui.login.LoginScreen
import com.fichamed.ui.splash.SplashScreen

// Função composable que define toda a navegação do app

//O NavGraph é o mapa do app
//Cada tela é uma rota
//O navController troca as telas
//popUpTo limpa o histórico
@Composable
fun NavGraph(startDestination: String = Screen.Splash.route) {

    // Controlador de navegação (gerencia as rotas/telas)
    val navController = rememberNavController()

    // Container principal de navegação
    NavHost(
        navController = navController,

        // Define a tela inicial do app
        startDestination = startDestination,

        // Animação ao entrar em uma nova tela
        enterTransition  = { fadeIn() + slideInHorizontally { it / 4 } },

        // Animação ao sair de uma tela
        exitTransition   = { fadeOut() + slideOutHorizontally { -it / 4 } },

        // Animação ao voltar (back)
        popEnterTransition  = { fadeIn() + slideInHorizontally { -it / 4 } },
        popExitTransition   = { fadeOut() + slideOutHorizontally { it / 4 } },
    ) {

        // ========================
        // TELA DE SPLASH
        // ========================
        composable(Screen.Splash.route) {

            SplashScreen(

                // Se usuário já estiver logado → vai para ficha
                onLogado = {
                    navController.navigate(Screen.Ficha.route) {
                        popUpTo(0) // limpa histórico
                    }
                },

                // Se não estiver logado → vai para login
                onNaoLogado = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) // limpa histórico
                    }
                }
            )
        }

        // ========================
        // TELA DE LOGIN
        // ========================
        composable(Screen.Login.route) {

            LoginScreen(

                // Login realizado com sucesso
                onLoginSucesso = {
                    navController.navigate(Screen.Ficha.route) {
                        popUpTo(0) // impede voltar pro login
                    }
                },

                // Ir para tela de cadastro
                onCadastro = {
                    navController.navigate(Screen.Cadastro.route)
                }
            )
        }

        // ========================
        // TELA DE CADASTRO
        // ========================
        composable(Screen.Cadastro.route) {

            CadastroScreen(

                // Voltar para tela anterior
                onVoltar = {
                    navController.popBackStack()
                },

                // Após cadastro → volta também
                onSucesso = {
                    navController.popBackStack()
                }
            )
        }

        // ========================
        // TELA DE FICHA MÉDICA
        // ========================
        composable(Screen.Ficha.route) {

            FichaScreen(

                // Logout → volta para login
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) // limpa histórico
                    }
                }
            )
        }
    }
}
