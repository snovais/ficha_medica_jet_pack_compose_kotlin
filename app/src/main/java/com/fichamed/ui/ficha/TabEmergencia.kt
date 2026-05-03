package com.fichamed.ui.ficha

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.fichamed.model.FichaMedica
import com.fichamed.ui.*

@Composable
fun TabEmergencia(ficha: FichaMedica, onUpdate: (FichaMedica) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Contato de Emergência
        SectionCard("Contato de Emergência", Icons.Default.Emergency, ErrorColor, ErrorLight) {
            FichaTextField(
                value = ficha.contatoEmergencia,
                onValueChange = { onUpdate(ficha.copy(contatoEmergencia = it)) },
                label = "Nome do contato *",
                leadingIcon = Icons.Default.Person,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                )
            )
            Spacer(Modifier.height(10.dp))
            FichaTextField(
                value = ficha.telefoneEmergencia,
                onValueChange = { onUpdate(ficha.copy(telefoneEmergencia = it)) },
                label = "Telefone de emergência *",
                leadingIcon = Icons.Default.Phone,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                )
            )
        }

        // Observações
        SectionCard("Observações Gerais", Icons.Default.Notes, TextSecondary, Surface) {
            FichaTextField(
                value = ficha.observacoes,
                onValueChange = { onUpdate(ficha.copy(observacoes = it)) },
                label = "Informações adicionais",
                leadingIcon = Icons.Default.Edit,
                placeholder = "Anotações médicas, restrições, preferências de tratamento...",
                minLines = 5,
                maxLines = 8
            )
        }

        // Aviso
        InfoBox(
            text = "Em caso de emergência, o contato informado será acionado imediatamente pela equipe médica.",
            color = ErrorColor,
            bgColor = ErrorLight
        )

        Spacer(Modifier.height(4.dp))
    }
}
