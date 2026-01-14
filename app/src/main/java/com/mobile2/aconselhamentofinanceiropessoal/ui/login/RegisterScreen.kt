package com.mobile2.aconselhamentofinanceiropessoal.ui.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.mobile2.aconselhamentofinanceiropessoal.data.repository.UserRepository
import com.mobile2.aconselhamentofinanceiropessoal.viewmodel.RegisterUiState
import com.mobile2.aconselhamentofinanceiropessoal.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val context = LocalContext.current
    val repository = remember { UserRepository(context) }
    val viewModel = remember { RegisterViewModel(repository) }

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val state by viewModel.registerResult.collectAsState()

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Criar Conta", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nome completo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Senha") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { viewModel.register(name, email, password) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cadastrar")
            }

            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = onNavigateToLogin) {
                Text("Já tenho uma conta")
            }

            when (state) {
                is RegisterUiState.Error -> {
                    val message = (state as RegisterUiState.Error).message
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    viewModel.resetState()
                }
                is RegisterUiState.Success -> {
                    Toast.makeText(context, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                    viewModel.resetState()
                    onRegisterSuccess()
                }
                else -> {}
            }
        }
    }
}
