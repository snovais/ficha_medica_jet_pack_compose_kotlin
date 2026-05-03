package com.fichamed.ui.ficha

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.fichamed.model.FichaMedica
import com.fichamed.ui.*

val TIPOS_SANGUINEOS = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")

@Composable
fun TabMedico(ficha: FichaMedica, onUpdate: (FichaMedica) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Dados Físicos
        SectionCard("Dados Físicos", Icons.Default.MonitorWeight, Primary, PrimaryLight) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FichaTextField(
                    value = ficha.peso, onValueChange = { onUpdate(ficha.copy(peso = it)) },
                    label = "Peso (kg)", placeholder = "70",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next),
                    modifier = Modifier.weight(1f)
                )
                FichaTextField(
                    value = ficha.altura, onValueChange = { onUpdate(ficha.copy(altura = it)) },
                    label = "Altura (cm)", placeholder = "175",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                    modifier = Modifier.weight(1f)
                )
                FichaDropdown(
                    value = ficha.tipoSanguineo, onValueChange = { onUpdate(ficha.copy(tipoSanguineo = it)) },
                    label = "Tipo Sang.", options = TIPOS_SANGUINEOS,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Condições de Saúde
        SectionCard("Condições de Saúde", Icons.Default.HealthAndSafety, ErrorColor, ErrorLight) {
            FichaTextField(
                value = ficha.alergias, onValueChange = { onUpdate(ficha.copy(alergias = it)) },
                label = "Alergias conhecidas", leadingIcon = Icons.Default.Warning,
                placeholder = "Medicamentos, alimentos...",
                maxLines = 3, minLines = 2
            )
            Spacer(Modifier.height(10.dp))
            FichaTextField(
                value = ficha.medicamentosUso, onValueChange = { onUpdate(ficha.copy(medicamentosUso = it)) },
                label = "Medicamentos em uso", leadingIcon = Icons.Default.Medication,
                placeholder = "Nome, dosagem e frequência",
                maxLines = 3, minLines = 2
            )
            Spacer(Modifier.height(10.dp))
            FichaTextField(
                value = ficha.doencasCronicas, onValueChange = { onUpdate(ficha.copy(doencasCronicas = it)) },
                label = "Doenças crônicas", leadingIcon = Icons.Default.Sick,
                placeholder = "Diabetes, hipertensão...",
                maxLines = 3, minLines = 2
            )
            Spacer(Modifier.height(10.dp))
            FichaTextField(
                value = ficha.cirurgias, onValueChange = { onUpdate(ficha.copy(cirurgias = it)) },
                label = "Cirurgias anteriores", leadingIcon = Icons.Default.LocalHospital,
                placeholder = "Descreva as cirurgias realizadas",
                maxLines = 3, minLines = 2
            )
            Spacer(Modifier.height(10.dp))
            FichaTextField(
                value = ficha.historicoFamiliar, onValueChange = { onUpdate(ficha.copy(historicoFamiliar = it)) },
                label = "Histórico familiar", leadingIcon = Icons.Default.FamilyRestroom,
                placeholder = "Doenças hereditárias relevantes",
                maxLines = 3, minLines = 2
            )
        }

        // Plano de Saúde
        SectionCard("Plano de Saúde", Icons.Default.CardMembership, Accent, AccentLight) {
            FichaTextField(
                value = ficha.planoSaude, onValueChange = { onUpdate(ficha.copy(planoSaude = it)) },
                label = "Plano de saúde", leadingIcon = Icons.Default.HealthAndSafety,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            Spacer(Modifier.height(10.dp))
            FichaTextField(
                value = ficha.numeroPlanoDeSaude, onValueChange = { onUpdate(ficha.copy(numeroPlanoDeSaude = it)) },
                label = "Número da carteirinha", leadingIcon = Icons.Default.CreditCard,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
        }

        Spacer(Modifier.height(4.dp))
    }
}
