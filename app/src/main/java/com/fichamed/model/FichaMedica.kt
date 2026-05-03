package com.fichamed.model

import com.google.gson.Gson

data class FichaMedica(
    val nome: String = "",
    val dataNascimento: String = "",
    val cpf: String = "",
    val rg: String = "",
    val sexo: String = "",
    val estadoCivil: String = "",
    val telefone: String = "",
    val email: String = "",
    val endereco: String = "",
    val cidade: String = "",
    val estado: String = "",
    val cep: String = "",
    val tipoSanguineo: String = "",
    val peso: String = "",
    val altura: String = "",
    val alergias: String = "",
    val medicamentosUso: String = "",
    val doencasCronicas: String = "",
    val cirurgias: String = "",
    val historicoFamiliar: String = "",
    val planoSaude: String = "",
    val numeroPlanoDeSaude: String = "",
    val contatoEmergencia: String = "",
    val telefoneEmergencia: String = "",
    val observacoes: String = ""
) {
    fun toJson(): String = Gson().toJson(this)
    companion object {
        fun fromJson(json: String): FichaMedica =
            Gson().fromJson(json, FichaMedica::class.java)
    }
}
