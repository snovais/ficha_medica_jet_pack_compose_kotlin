package com.fichamed.model // Define o pacote responsável pelos modelos (dados da aplicação)

// Importa biblioteca Gson para conversão entre objeto e JSON
import com.google.gson.Gson

// Data class representa uma entidade de dados (Ficha Médica)
data class FichaMedica(

    // Dados pessoais
    val nome: String = "",
    val dataNascimento: String = "",
    val cpf: String = "",
    val rg: String = "",
    val sexo: String = "",
    val estadoCivil: String = "",

    // Contato
    val telefone: String = "",
    val email: String = "",

    // Endereço
    val endereco: String = "",
    val cidade: String = "",
    val estado: String = "",
    val cep: String = "",

    // Dados físicos
    val tipoSanguineo: String = "",
    val peso: String = "",
    val altura: String = "",

    // Informações médicas
    val alergias: String = "",
    val medicamentosUso: String = "",
    val doencasCronicas: String = "",
    val cirurgias: String = "",
    val historicoFamiliar: String = "",

    // Plano de saúde
    val planoSaude: String = "",
    val numeroPlanoDeSaude: String = "",

    // Emergência
    val contatoEmergencia: String = "",
    val telefoneEmergencia: String = "",

    // Observações gerais
    val observacoes: String = ""

) {

    // Converte o objeto FichaMedica para uma String no formato JSON
    fun toJson(): String = Gson().toJson(this)

    companion object {

        // Converte uma String JSON de volta para um objeto FichaMedica
        fun fromJson(json: String): FichaMedica =
            Gson().fromJson(json, FichaMedica::class.java)
    }
}