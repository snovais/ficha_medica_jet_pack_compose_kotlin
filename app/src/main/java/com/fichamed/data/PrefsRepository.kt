package com.fichamed.data // Define o pacote responsável por acesso a dados

// Importações necessárias para DataStore e manipulação de dados
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.fichamed.model.FichaMedica
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// Cria uma extensão do Context para acessar o DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore("fichamed")

// Classe responsável por gerenciar persistência de dados (Repository Pattern)
class PrefsRepository(private val ctx: Context) {

    // Define chaves para armazenar dados no DataStore
    companion object {
        val KEY_NOME       = stringPreferencesKey("usuario_nome")
        val KEY_EMAIL      = stringPreferencesKey("usuario_email")
        val KEY_SENHA      = stringPreferencesKey("usuario_senha")
        val KEY_LOGADO     = booleanPreferencesKey("logado")
        val KEY_LEMBRAR    = booleanPreferencesKey("lembrar_me")
        val KEY_EMAIL_SAL  = stringPreferencesKey("email_salvo")
        val KEY_FICHA      = stringPreferencesKey("ficha_json")
    }

    // Fluxos reativos para observar dados salvos
    val isLogado: Flow<Boolean> = ctx.dataStore.data.map {
        it[KEY_LOGADO] ?: false // Retorna false se não existir valor
    }

    val usuarioNome: Flow<String> = ctx.dataStore.data.map {
        it[KEY_NOME] ?: "Usuário" // Valor padrão
    }

    val emailSalvo: Flow<String> = ctx.dataStore.data.map {
        it[KEY_EMAIL_SAL] ?: ""
    }

    val lembrarMe: Flow<Boolean> = ctx.dataStore.data.map {
        it[KEY_LEMBRAR] ?: false
    }

    val fichaJson: Flow<String> = ctx.dataStore.data.map {
        it[KEY_FICHA] ?: ""
    }

    // Salva dados do usuário
    suspend fun salvarUsuario(nome: String, email: String, senha: String) {
        ctx.dataStore.edit {
            it[KEY_NOME]  = nome
            it[KEY_EMAIL] = email
            it[KEY_SENHA] = senha
        }
    }

    // Realiza login e salva estado
    suspend fun login(lembrar: Boolean, email: String) {
        ctx.dataStore.edit {
            it[KEY_LOGADO] = true

            // Se marcar "lembrar-me", salva email
            if (lembrar) {
                it[KEY_LEMBRAR] = true
                it[KEY_EMAIL_SAL] = email
            } else {
                it.remove(KEY_LEMBRAR)
                it.remove(KEY_EMAIL_SAL)
            }
        }
    }

    // Realiza logout
    suspend fun logout() {
        ctx.dataStore.edit {
            it[KEY_LOGADO] = false
        }
    }

    // Salva ficha médica convertida em JSON
    suspend fun salvarFicha(ficha: FichaMedica) {
        ctx.dataStore.edit {
            it[KEY_FICHA] = ficha.toJson()
        }
    }

    // Recupera credenciais armazenadas
    suspend fun getCredenciais(): Pair<String, String> {
        val p = ctx.dataStore.data.first()

        return Pair(
            p[KEY_EMAIL] ?: "admin@clinica.com", // valor padrão
            p[KEY_SENHA] ?: "123456"
        )
    }
}