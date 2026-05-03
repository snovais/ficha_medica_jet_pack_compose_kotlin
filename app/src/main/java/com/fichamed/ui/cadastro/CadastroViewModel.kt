package com.fichamed.ui.cadastro

// Importações necessárias para o ciclo de vida do Android, Coroutines e Gerenciamento de Estado
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fichamed.data.PrefsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Data class que representa o "Estado" da tela.
// Concentra tudo o que a UI precisa mostrar (textos, erros e status de carregamento).
data class CadastroUiState(
    val nome: String = "",
    val email: String = "",
    val senha: String = "",
    val confirma: String = "",
    val isLoading: Boolean = false, // Indica se o processo de salvamento está ocorrendo
    val nomeError: String = "",    // Mensagem de erro específica para o nome
    val emailError: String = "",   // Mensagem de erro específica para o e-mail
    val senhaError: String = "",   // Mensagem de erro específica para a senha
    val confirmaError: String = "",// Mensagem de erro para a confirmação
    val sucesso: Boolean = false,  // Gatilho para avisar a UI que o cadastro deu certo
)

// O ViewModel sobrevive a mudanças de configuração (como girar a tela)
class CadastroViewModel(app: Application) : AndroidViewModel(app) {

    // Instância do repositório para persistência de dados (SharedPreferences/Datastore)
    private val repo = PrefsRepository(app)

    // _state é privado e mutável (escrita). O ViewModel altera este valor internamente.
    private val _state = MutableStateFlow(CadastroUiState())

    // state é público e imutável (leitura). A UI observa este Flow para se atualizar.
    val state: StateFlow<CadastroUiState> = _state

    // Funções chamadas pela UI a cada tecla digitada.
    // O .copy() atualiza apenas o campo necessário e limpa o erro ao começar a digitar.
    fun onNome(v: String)     { _state.value = _state.value.copy(nome = v, nomeError = "") }
    fun onEmail(v: String)    { _state.value = _state.value.copy(email = v, emailError = "") }
    fun onSenha(v: String)    { _state.value = _state.value.copy(senha = v, senhaError = "") }
    fun onConfirma(v: String) { _state.value = _state.value.copy(confirma = v, confirmaError = "") }

    // Função principal que processa o clique no botão "Cadastrar"
    fun cadastrar() {
        val s = _state.value // Captura o estado atual
        var nErr = ""; var eErr = ""; var sErr = ""; var cErr = ""; var ok = true

        // --- Validações de Regra de Negócio ---

        // Verifica se há pelo menos duas palavras (nome e sobrenome)
        if (s.nome.trim().split(" ").size < 2) { nErr = "Informe nome e sobrenome"; ok = false }

        // Validação simples de e-mail (presença do @)
        if (!s.email.contains("@"))             { eErr = "E-mail inválido"; ok = false }

        // Validação de comprimento de senha
        if (s.senha.length < 6)                 { sErr = "Mínimo 6 caracteres"; ok = false }

        // Verifica se as senhas digitadas são idênticas
        if (s.confirma != s.senha)              { cErr = "As senhas não coincidem"; ok = false }

        // Se alguma validação falhou, atualiza o estado com as mensagens de erro e interrompe a execução
        if (!ok) {
            _state.value = s.copy(nomeError = nErr, emailError = eErr, senhaError = sErr, confirmaError = cErr)
            return
        }

        // --- Início do Processo de Cadastro ---

        // Ativa o estado de carregamento (mostra o spinner no botão na UI)
        _state.value = s.copy(isLoading = true)

        // Lança uma Coroutine (processamento assíncrono) para não travar a interface
        viewModelScope.launch {
            // Simula ou executa a gravação dos dados no repositório
            repo.salvarUsuario(s.nome.trim(), s.email.trim(), s.senha)

            // Simula um tempo de rede/processamento para que o usuário veja o feedback
            delay(600)

            // Finaliza o carregamento e marca o sucesso como verdadeiro
            _state.value = _state.value.copy(isLoading = false, sucesso = true)
        }
    }
}