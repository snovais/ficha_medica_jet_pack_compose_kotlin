package com.fichamed.navigation

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fichamed.ui.cadastro.CadastroScreen
import com.fichamed.ui.ficha.FichaScreen
import com.fichamed.ui.login.LoginScreen
import com.fichamed.ui.splash.SplashScreen

@Composable
fun NavGraph(startDestination: String = Screen.Splash.route) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition  = { fadeIn() + slideInHorizontally { it / 4 } },
        exitTransition   = { fadeOut() + slideOutHorizontally { -it / 4 } },
        popEnterTransition  = { fadeIn() + slideInHorizontally { -it / 4 } },
        popExitTransition   = { fadeOut() + slideOutHorizontally { it / 4 } },
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onLogado    = { navController.navigate(Screen.Ficha.route) { popUpTo(0) } },
                onNaoLogado = { navController.navigate(Screen.Login.route) { popUpTo(0) } }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSucesso = { navController.navigate(Screen.Ficha.route) { popUpTo(0) } },
                onCadastro     = { navController.navigate(Screen.Cadastro.route) }
            )
        }

        composable(Screen.Cadastro.route) {
            CadastroScreen(
                onVoltar  = { navController.popBackStack() },
                onSucesso = { navController.popBackStack() }
            )
        }

        composable(Screen.Ficha.route) {
            FichaScreen(
                onLogout = { navController.navigate(Screen.Login.route) { popUpTo(0) } }
            )
        }
    }
}
