// Define o pacote onde o arquivo reside para organização do projeto
package com.fichamed.ui.cadastro

// Importações de bibliotecas do Android Compose para UI, Layout, Ícones e Gerenciamento de Estado
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fichamed.ui.* // Importa componentes customizados como FichaTextField e PrimaryButton

// OptIn necessário pois a TopAppBar e o Scaffold do Material3 ainda possuem partes experimentais
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroScreen(
    onVoltar: () -> Unit,      // Callback para navegar de volta (quem chama a tela decide o destino)
    onSucesso: () -> Unit,     // Callback executado após o cadastro ser concluído com êxito
    vm: CadastroViewModel = viewModel() // Injeta o ViewModel para gerenciar a lógica de negócio e estado
) {
    // Coleta o estado do ViewModel como um Estado do Compose. Se o StateFlow no VM mudar, a UI recompõe.
    val state by vm.state.collectAsState()

    // Estados locais da UI: controlam se o texto da senha está visível ou com asteriscos
    var mostrarSenha by remember { mutableStateOf(false) }
    var mostrarConfirma by remember { mutableStateOf(false) }

    // Gerenciador do Snackbar (mensagens flutuantes na parte inferior)
    val snackHost = remember { SnackbarHostState() }

    // LaunchedEffect monitora a variável 'state.sucesso'. Quando mudar para 'true', executa o bloco.
    LaunchedEffect(state.sucesso) {
        if (state.sucesso) {
            snackHost.showSnackbar("Conta criada com sucesso!") // Exibe feedback visual
            kotlinx.coroutines.delay(800) // Pequena pausa para o usuário ler a mensagem
            onSucesso() // Navega para a próxima tela
        }
    }

    // Scaffold fornece a estrutura básica visual (TopBar, Snackbar, BottomBar)
    Scaffold(
        snackbarHost = { SnackbarHost(snackHost) }, // Define onde o snackbar aparecerá
        topBar = {
            TopAppBar(
                title = { Text("Criar Conta", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    // Botão de voltar que aciona o callback onVoltar
                    IconButton(onClick = onVoltar) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                // Customização de cores baseada em temas definidos no projeto (CardBg)
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CardBg)
            )
        }
    ) { padding -> // O 'padding' contém as medidas das barras (TopBar) para não sobrepor o conteúdo
        Column(
            modifier = Modifier
                .fillMaxSize() // Ocupa toda a tela disponível
                .background(Surface) // Aplica a cor de fundo definida no seu tema
                .verticalScroll(rememberScrollState()) // Permite rolar a tela se o teclado aparecer
                .padding(padding) // Respeita o espaço da TopAppBar
                .padding(24.dp) // Adiciona espaçamento interno nas bordas
        ) {
            // Cabeçalho da tela
            Text("Crie sua conta", color = TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp)) // Espaçamento vertical entre elementos
            Text("Preencha os dados para se cadastrar", color = TextSecondary, fontSize = 14.sp)
            Spacer(Modifier.height(24.dp))

            // Campo de Nome: envia cada mudança para o ViewModel (vm::onNome)
            FichaTextField(
                value = state.nome,
                onValueChange = vm::onNome,
                label = "Nome completo",
                leadingIcon = Icons.Default.Person,
                isError = state.nomeError.isNotEmpty(), // Fica vermelho se houver erro
                errorMessage = state.nomeError,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words, // Inicia palavras com maiúscula
                    imeAction = ImeAction.Next // Botão "Próximo" no teclado
                )
            )
            Spacer(Modifier.height(12.dp))

            // Campo de Email
            FichaTextField(
                value = state.email,
                onValueChange = vm::onEmail,
                label = "E-mail",
                leadingIcon = Icons.Default.Email,
                isError = state.emailError.isNotEmpty(),
                errorMessage = state.emailError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email, // Teclado otimizado para e-mail (com @)
                    imeAction = ImeAction.Next
                )
            )
            Spacer(Modifier.height(12.dp))

            // Campo de Senha
            FichaTextField(
                value = state.senha,
                onValueChange = vm::onSenha,
                label = "Senha",
                leadingIcon = Icons.Default.Lock,
                isError = state.senhaError.isNotEmpty(),
                errorMessage = state.senhaError,
                // Alterna entre texto limpo e caracteres ocultos (****)
                visualTransformation = if (mostrarSenha) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    // Ícone de "olhinho" para mostrar/esconder senha
                    IconButton(onClick = { mostrarSenha = !mostrarSenha }) {
                        Icon(if (mostrarSenha) Icons.Default.Visibility else Icons.Default.VisibilityOff, null, tint = TextSecondary)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next)
            )
            Spacer(Modifier.height(12.dp))

            // Campo de Confirmação (lógica idêntica ao de senha)
            FichaTextField(
                value = state.confirma,
                onValueChange = vm::onConfirma,
                label = "Confirmar senha",
                leadingIcon = Icons.Default.Lock,
                isError = state.confirmaError.isNotEmpty(),
                errorMessage = state.confirmaError,
                visualTransformation = if (mostrarConfirma) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { mostrarConfirma = !mostrarConfirma }) {
                        Icon(if (mostrarConfirma) Icons.Default.Visibility else Icons.Default.VisibilityOff, null, tint = TextSecondary)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done) // Botão "Concluído"
            )
            Spacer(Modifier.height(28.dp))

            // Botão de submissão
            PrimaryButton(
                text = "Criar Conta",
                onClick = vm::cadastrar, // Chama a função de cadastro no ViewModel
                isLoading = state.isLoading // Se estiver carregando, o botão mostra um spinner/troca estado
            )
        }
    }
}