package com.fichamed.ui.cadastro

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
import com.fichamed.ui.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroScreen(
    onVoltar: () -> Unit,
    onSucesso: () -> Unit,
    vm: CadastroViewModel = viewModel()
) {
    val state by vm.state.collectAsState()
    var mostrarSenha by remember { mutableStateOf(false) }
    var mostrarConfirma by remember { mutableStateOf(false) }
    val snackHost = remember { SnackbarHostState() }

    LaunchedEffect(state.sucesso) {
        if (state.sucesso) {
            snackHost.showSnackbar("Conta criada com sucesso!")
            kotlinx.coroutines.delay(800)
            onSucesso()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackHost) },
        topBar = {
            TopAppBar(
                title = { Text("Criar Conta", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onVoltar) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CardBg)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Surface)
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(24.dp)
        ) {
            Text("Crie sua conta", color = TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text("Preencha os dados para se cadastrar", color = TextSecondary, fontSize = 14.sp)
            Spacer(Modifier.height(24.dp))

            FichaTextField(
                value = state.nome,
                onValueChange = vm::onNome,
                label = "Nome completo",
                leadingIcon = Icons.Default.Person,
                isError = state.nomeError.isNotEmpty(),
                errorMessage = state.nomeError,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                )
            )
            Spacer(Modifier.height(12.dp))

            FichaTextField(
                value = state.email,
                onValueChange = vm::onEmail,
                label = "E-mail",
                leadingIcon = Icons.Default.Email,
                isError = state.emailError.isNotEmpty(),
                errorMessage = state.emailError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )
            Spacer(Modifier.height(12.dp))

            FichaTextField(
                value = state.senha,
                onValueChange = vm::onSenha,
                label = "Senha",
                leadingIcon = Icons.Default.Lock,
                isError = state.senhaError.isNotEmpty(),
                errorMessage = state.senhaError,
                visualTransformation = if (mostrarSenha) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { mostrarSenha = !mostrarSenha }) {
                        Icon(if (mostrarSenha) Icons.Default.Visibility else Icons.Default.VisibilityOff, null, tint = TextSecondary)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next)
            )
            Spacer(Modifier.height(12.dp))

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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done)
            )
            Spacer(Modifier.height(28.dp))

            PrimaryButton(
                text = "Criar Conta",
                onClick = vm::cadastrar,
                isLoading = state.isLoading
            )
        }
    }
}
