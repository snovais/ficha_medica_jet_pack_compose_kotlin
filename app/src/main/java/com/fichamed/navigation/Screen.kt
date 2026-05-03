package com.fichamed.navigation

sealed class Screen(val route: String) {
    object Splash   : Screen("splash")
    object Login    : Screen("login")
    object Cadastro : Screen("cadastro")
    object Ficha    : Screen("ficha")
}
