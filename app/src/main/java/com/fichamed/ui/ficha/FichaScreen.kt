package com.fichamed.ui.ficha

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fichamed.ui.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FichaScreen(
    onLogout: () -> Unit,
    vm: FichaViewModel = viewModel()
) {
    val state by vm.state.collectAsState()
    val tabs = listOf("Pessoal", "Médico", "Emergência")
    var tabSelecionada by remember { mutableIntStateOf(0) }
    val snackHost = remember { SnackbarHostState() }
    var showLogoutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(state.salvoSucesso) {
        if (state.salvoSucesso) {
            snackHost.showSnackbar("✓  Ficha salva com sucesso!")
            vm.resetSucesso()
        }
    }
    LaunchedEffect(state.erroObrigatorio) {
        if (state.erroObrigatorio) {
            snackHost.showSnackbar("Preencha os campos obrigatórios (*)")
            vm.resetErro()
        }
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Sair do sistema") },
            text  = { Text("Deseja realmente sair?") },
            confirmButton = {
                Button(
                    onClick = { vm.logout(); onLogout() },
                    colors  = ButtonDefaults.buttonColors(containerColor = Primary)
                ) { Text("Sair") }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) { Text("Cancelar") }
            }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackHost) },
        topBar = {
            Column(modifier = Modifier.background(CardBg)) {
                TopAppBar(
                    title = {
                        Column {
                            Text("Ficha Médica", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                            Text("Olá, ${state.usuarioNome}", color = TextSecondary, fontSize = 12.sp)
                        }
                    },
                    actions = {
                        IconButton(onClick = { showLogoutDialog = true }) {
                            Icon(Icons.Default.Logout, null, tint = TextSecondary)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = CardBg)
                )
                // TabRow sem indicador customizado para evitar problema de API
                TabRow(
                    selectedTabIndex = tabSelecionada,
                    containerColor   = CardBg,
                    contentColor     = Primary,
                ) {
                    tabs.forEachIndexed { i, t ->
                        Tab(
                            selected = tabSelecionada == i,
                            onClick  = { tabSelecionada = i },
                            text = {
                                Text(
                                    t,
                                    color = if (tabSelecionada == i) Primary else TextSecondary,
                                    fontWeight = if (tabSelecionada == i) FontWeight.SemiBold else FontWeight.Normal,
                                    fontSize = 13.sp
                                )
                            }
                        )
                    }
                }
                HorizontalDivider(color = Border)
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .background(CardBg)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .navigationBarsPadding()
            ) {
                Button(
                    onClick  = vm::salvar,
                    enabled  = !state.isSaving,
                    shape    = RoundedCornerShape(12.dp),
                    colors   = ButtonDefaults.buttonColors(containerColor = Primary),
                    modifier = Modifier.fillMaxWidth().height(52.dp)
                ) {
                    if (state.isSaving) {
                        CircularProgressIndicator(
                            color       = Color.White,
                            strokeWidth = 2.5.dp,
                            modifier    = Modifier.size(22.dp)
                        )
                    } else {
                        Icon(Icons.Default.Save, null, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Salvar Ficha Médica", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { padding ->
        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Primary)
            }
        } else {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .background(Surface)
            ) {
                when (tabSelecionada) {
                    0 -> TabPessoal(state.ficha)    { vm.update(it) }
                    1 -> TabMedico(state.ficha)     { vm.update(it) }
                    2 -> TabEmergencia(state.ficha) { vm.update(it) }
                }
            }
        }
    }
}
