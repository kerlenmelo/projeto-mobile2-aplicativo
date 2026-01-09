package com.mobile2.aconselhamentofinanceiropessoal.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddTransactionScreen(onSave: (String, String, String, Double) -> Unit) {
    var type by remember { mutableStateOf("Receita") }
    var category by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Adicionar Transação", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        DropdownMenuBox(
            label = "Tipo",
            options = listOf("Receita", "Despesa"),
            selected = type,
            onSelect = { type = it }
        )
        OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Categoria") })
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descrição") })
        OutlinedTextField(value = value, onValueChange = { value = it }, label = { Text("Valor") })
        Spacer(Modifier.height(16.dp))
        Button(onClick = { onSave(type, category, description, value.toDoubleOrNull() ?: 0.0) }) {
            Text("Salvar")
        }
    }
}

@Composable
fun DropdownMenuBox(label: String, options: List<String>, selected: String, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            modifier = Modifier.fillMaxWidth().clickable { expanded = true }
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach {
                DropdownMenuItem(text = { Text(it) }, onClick = {
                    onSelect(it)
                    expanded = false
                })
            }
        }
    }
}
