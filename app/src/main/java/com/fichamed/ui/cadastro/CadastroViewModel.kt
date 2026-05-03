package com.fichamed.ui.cadastro

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fichamed.data.PrefsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CadastroUiState(
    val nome: String = "",
    val email: String = "",
    val senha: String = "",
    val confirma: String = "",
    val isLoading: Boolean = false,
    val nomeError: String = "",
    val emailError: String = "",
    val senhaError: String = "",
    val confirmaError: String = "",
    val sucesso: Boolean = false,
)

class CadastroViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = PrefsRepository(app)
    private val _state = MutableStateFlow(CadastroUiState())
    val state: StateFlow<CadastroUiState> = _state

    fun onNome(v: String)     { _state.value = _state.value.copy(nome = v, nomeError = "") }
    fun onEmail(v: String)    { _state.value = _state.value.copy(email = v, emailError = "") }
    fun onSenha(v: String)    { _state.value = _state.value.copy(senha = v, senhaError = "") }
    fun onConfirma(v: String) { _state.value = _state.value.copy(confirma = v, confirmaError = "") }

    fun cadastrar() {
        val s = _state.value
        var nErr = ""; var eErr = ""; var sErr = ""; var cErr = ""; var ok = true

        if (s.nome.trim().split(" ").size < 2) { nErr = "Informe nome e sobrenome"; ok = false }
        if (!s.email.contains("@"))             { eErr = "E-mail inválido"; ok = false }
        if (s.senha.length < 6)                 { sErr = "Mínimo 6 caracteres"; ok = false }
        if (s.confirma != s.senha)              { cErr = "As senhas não coincidem"; ok = false }

        if (!ok) {
            _state.value = s.copy(nomeError = nErr, emailError = eErr, senhaError = sErr, confirmaError = cErr)
            return
        }

        _state.value = s.copy(isLoading = true)
        viewModelScope.launch {
            repo.salvarUsuario(s.nome.trim(), s.email.trim(), s.senha)
            delay(600)
            _state.value = _state.value.copy(isLoading = false, sucesso = true)
        }
    }
}
