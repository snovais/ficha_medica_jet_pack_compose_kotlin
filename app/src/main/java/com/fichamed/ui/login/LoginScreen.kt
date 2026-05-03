package com.fichamed.ui.login

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fichamed.ui.*

@Composable
fun LoginScreen(
    onLoginSucesso: () -> Unit,
    onCadastro: () -> Unit,
    vm: LoginViewModel = viewModel()
) {
    val state by vm.state.collectAsState()
    var mostrarSenha by remember { mutableStateOf(false) }
    val snackHost = remember { SnackbarHostState() }

    LaunchedEffect(state.loginSucesso) {
        if (state.loginSucesso) onLoginSucesso()
    }
    LaunchedEffect(state.errMsg) {
        if (state.errMsg.isNotEmpty()) {
            snackHost.showSnackbar(state.errMsg)
            vm.clearMsg()
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackHost) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimaryLight)
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Primary, RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
                    .padding(vertical = 48.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(8.dp),
                        modifier = Modifier.size(80.dp)
                    ) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Default.MedicalServices,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(44.dp)
                            )
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    Text("FichaMed", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(4.dp))
                    Text("Sistema de Fichas Médicas", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                }
            }

            // Formulário
            Column(modifier = Modifier.padding(24.dp)) {
                Spacer(Modifier.height(8.dp))
                Text("Bem-vindo de volta", color = TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                Text("Faça login para acessar o sistema", color = TextSecondary, fontSize = 14.sp)
                Spacer(Modifier.height(24.dp))

                FichaTextField(
                    value = state.email,
                    onValueChange = vm::onEmailChange,
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
                    onValueChange = vm::onSenhaChange,
                    label = "Senha",
                    leadingIcon = Icons.Default.Lock,
                    isError = state.senhaError.isNotEmpty(),
                    errorMessage = state.senhaError,
                    visualTransformation = if (mostrarSenha) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { mostrarSenha = !mostrarSenha }) {
                            Icon(
                                if (mostrarSenha) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                null, tint = TextSecondary
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    )
                )
                Spacer(Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = state.lembrarMe,
                        onCheckedChange = vm::onLembrarChange,
                        colors = CheckboxDefaults.colors(checkedColor = Primary)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text("Lembrar meu e-mail", color = TextSecondary, fontSize = 14.sp)
                }
                Spacer(Modifier.height(20.dp))

                PrimaryButton(
                    text = "Entrar",
                    onClick = vm::login,
                    isLoading = state.isLoading
                )
                Spacer(Modifier.height(16.dp))

                TextButton(
                    onClick = onCadastro,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Não tem conta? ",
                        color = TextSecondary,
                        fontSize = 14.sp
                    )
                    Text(
                        "Cadastre-se",
                        color = Primary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.height(8.dp))

                // Dica de acesso
                InfoBox(
                    text = "Acesso padrão:\nadmin@clinica.com / 123456",
                    color = Primary,
                    bgColor = PrimaryLight
                )
            }
        }
    }
}
