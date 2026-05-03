package com.fichamed.ui.ficha

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.fichamed.model.FichaMedica
import com.fichamed.ui.*

val UFS = listOf("AC","AL","AP","AM","BA","CE","DF","ES","GO","MA","MT","MS",
    "MG","PA","PB","PR","PE","PI","RJ","RN","RS","RO","RR","SC","SP","SE","TO")

val SEXOS = listOf("Masculino", "Feminino", "Outro")

val ESTADOS_CIVIS = listOf("Solteiro(a)", "Casado(a)", "Divorciado(a)", "Viúvo(a)", "União estável")

@Composable
fun TabPessoal(ficha: FichaMedica, onUpdate: (FichaMedica) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Identificação
        SectionCard("Identificação", Icons.Default.Badge, Primary, PrimaryLight) {
            FichaTextField(
                value = ficha.nome, onValueChange = { onUpdate(ficha.copy(nome = it)) },
                label = "Nome completo *",
                leadingIcon = Icons.Default.Person,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words, imeAction = ImeAction.Next)
            )
            Spacer(Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FichaTextField(
                    value = ficha.dataNascimento, onValueChange = { onUpdate(ficha.copy(dataNascimento = it)) },
                    label = "Nascimento", placeholder = "DD/MM/AAAA",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                    modifier = Modifier.weight(1f)
                )
                FichaDropdown(
                    value = ficha.sexo, onValueChange = { onUpdate(ficha.copy(sexo = it)) },
                    label = "Sexo", options = SEXOS,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FichaTextField(
                    value = ficha.cpf, onValueChange = { onUpdate(ficha.copy(cpf = it)) },
                    label = "CPF", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                    modifier = Modifier.weight(1f)
                )
                FichaTextField(
                    value = ficha.rg, onValueChange = { onUpdate(ficha.copy(rg = it)) },
                    label = "RG", keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.height(10.dp))
            FichaDropdown(
                value = ficha.estadoCivil, onValueChange = { onUpdate(ficha.copy(estadoCivil = it)) },
                label = "Estado civil", options = ESTADOS_CIVIS,
                leadingIcon = Icons.Default.People
            )
        }

        // Contato
        SectionCard("Contato", Icons.Default.Phone, Accent, AccentLight) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FichaTextField(
                    value = ficha.telefone, onValueChange = { onUpdate(ficha.copy(telefone = it)) },
                    label = "Telefone", leadingIcon = Icons.Default.Phone,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next),
                    modifier = Modifier.weight(1f)
                )
                FichaTextField(
                    value = ficha.email, onValueChange = { onUpdate(ficha.copy(email = it)) },
                    label = "E-mail", leadingIcon = Icons.Default.Email,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Endereço
        SectionCard("Endereço", Icons.Default.LocationOn, Success, SuccessLight) {
            FichaTextField(
                value = ficha.endereco, onValueChange = { onUpdate(ficha.copy(endereco = it)) },
                label = "Rua, número, complemento", leadingIcon = Icons.Default.Home,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences, imeAction = ImeAction.Next)
            )
            Spacer(Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FichaTextField(
                    value = ficha.cidade, onValueChange = { onUpdate(ficha.copy(cidade = it)) },
                    label = "Cidade",
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words, imeAction = ImeAction.Next),
                    modifier = Modifier.weight(2f)
                )
                FichaDropdown(
                    value = ficha.estado, onValueChange = { onUpdate(ficha.copy(estado = it)) },
                    label = "UF", options = UFS,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.height(10.dp))
            FichaTextField(
                value = ficha.cep, onValueChange = { onUpdate(ficha.copy(cep = it)) },
                label = "CEP", placeholder = "00000-000",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done)
            )
        }

        Spacer(Modifier.height(4.dp))
    }
}
