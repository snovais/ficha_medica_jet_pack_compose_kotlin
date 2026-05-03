package com.fichamed.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fichamed.data.PrefsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val senha: String = "",
    val lembrarMe: Boolean = false,
    val isLoading: Boolean = false,
    val emailError: String = "",
    val senhaError: String = "",
    val loginSucesso: Boolean = false,
    val errMsg: String = "",
)

class LoginViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = PrefsRepository(app)

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state

    init {
        viewModelScope.launch {
            if (repo.lembrarMe.first()) {
                val email = repo.emailSalvo.first()
                _state.value = _state.value.copy(email = email, lembrarMe = true)
            }
        }
    }

    fun onEmailChange(v: String)    = _state.value.let { _state.value = it.copy(email = v, emailError = "") }
    fun onSenhaChange(v: String)    = _state.value.let { _state.value = it.copy(senha = v, senhaError = "") }
    fun onLembrarChange(v: Boolean) = _state.value.let { _state.value = it.copy(lembrarMe = v) }
    fun clearMsg()                  = _state.value.let { _state.value = it.copy(errMsg = "") }

    fun login() {
        val s = _state.value
        var ok = true
        var emailErr = ""; var senhaErr = ""

        if (!s.email.contains("@")) { emailErr = "E-mail inválido"; ok = false }
        if (s.senha.length < 6)      { senhaErr = "Mínimo 6 caracteres"; ok = false }
        if (!ok) { _state.value = s.copy(emailError = emailErr, senhaError = senhaErr); return }

        _state.value = s.copy(isLoading = true)
        viewModelScope.launch {
            val (emailCorreto, senhaCorreta) = repo.getCredenciais()
            if (s.email == emailCorreto && s.senha == senhaCorreta) {
                repo.login(s.lembrarMe, s.email)
                _state.value = _state.value.copy(isLoading = false, loginSucesso = true)
            } else {
                _state.value = _state.value.copy(isLoading = false, errMsg = "E-mail ou senha incorretos")
            }
        }
    }
}
