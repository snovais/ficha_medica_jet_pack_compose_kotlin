package com.fichamed.ui.ficha

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fichamed.data.PrefsRepository
import com.fichamed.model.FichaMedica
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class FichaUiState(
    val ficha: FichaMedica = FichaMedica(),
    val usuarioNome: String = "Usuário",
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val salvoSucesso: Boolean = false,
    val erroObrigatorio: Boolean = false,
)

class FichaViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = PrefsRepository(app)
    private val _state = MutableStateFlow(FichaUiState())
    val state: StateFlow<FichaUiState> = _state

    init {
        viewModelScope.launch {
            val nome = repo.usuarioNome.first()
            val json = repo.fichaJson.first()
            val ficha = if (json.isNotEmpty()) FichaMedica.fromJson(json) else FichaMedica()
            _state.value = _state.value.copy(usuarioNome = nome, ficha = ficha, isLoading = false)
        }
    }

    fun update(ficha: FichaMedica) { _state.value = _state.value.copy(ficha = ficha) }

    fun salvar() {
        val ficha = _state.value.ficha
        if (ficha.nome.isBlank() || ficha.contatoEmergencia.isBlank() || ficha.telefoneEmergencia.isBlank()) {
            _state.value = _state.value.copy(erroObrigatorio = true)
            return
        }
        viewModelScope.launch {
            _state.value = _state.value.copy(isSaving = true, erroObrigatorio = false)
            repo.salvarFicha(ficha)
            delay(700)
            _state.value = _state.value.copy(isSaving = false, salvoSucesso = true)
        }
    }

    fun logout()          = viewModelScope.launch { repo.logout() }
    fun resetSucesso()    { _state.value = _state.value.copy(salvoSucesso = false) }
    fun resetErro()       { _state.value = _state.value.copy(erroObrigatorio = false) }
}
