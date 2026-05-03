package com.fichamed.navigation // Pacote responsável pela navegação

// Classe selada que representa todas as telas do aplicativo
//Função:

//Centralizar os nomes das telas
//Evitar erros com strings
//Organizar a navegação
sealed class Screen(val route: String) {

    // Tela inicial (Splash)
    object Splash : Screen("splash")

    // Tela de login
    object Login : Screen("login")

    // Tela de cadastro
    object Cadastro : Screen("cadastro")

    // Tela principal (ficha médica)
    object Ficha : Screen("ficha")
}