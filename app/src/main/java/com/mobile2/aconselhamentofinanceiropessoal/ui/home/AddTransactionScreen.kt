package com.mobile2.aconselhamentofinanceiropessoal.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mobile2.aconselhamentofinanceiropessoal.data.model.TransactionEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    transaction: TransactionEntity? = null, // Make transaction optional
    onSave: (String, String, String, Double) -> Unit,
    onBack: () -> Unit
) {
    var type by remember { mutableStateOf("Receita") }
    var category by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    // Pre-fill fields if editing
    LaunchedEffect(transaction) {
        if (transaction != null) {
            type = transaction.type
            category = transaction.category
            description = transaction.description
            value = transaction.value.toString()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (transaction == null) "Nova Transação" else "Editar Transação") }, // Dynamic title
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            // Seletor de Tipo
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = type,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    listOf("Receita", "Despesa").forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = { type = option; expanded = false }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Categoria") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = value,
                onValueChange = { if (it.all { c -> c.isDigit() || c == '.' }) value = it },
                label = { Text("Valor (R$)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    val numericValue = value.toDoubleOrNull() ?: 0.0
                    if (category.isNotBlank() && numericValue > 0) {
                        onSave(type, category, description, numericValue)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar Transação")
            }
        }
    }
}