package com.mobile2.aconselhamentofinanceiropessoal.ui.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.mobile2.aconselhamentofinanceiropessoal.data.model.UserModel
import com.mobile2.aconselhamentofinanceiropessoal.data.repository.UserRepository
import com.mobile2.aconselhamentofinanceiropessoal.viewmodel.LoginUiState
import com.mobile2.aconselhamentofinanceiropessoal.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: (UserModel) -> Unit, // Agora aceita o UserModel
    onNavigateToRegister: () -> Unit
) {
    val context = LocalContext.current
    // Instanciação manual para simplicidade (sem Hilt)
    val repository = remember { UserRepository(context) }
    val viewModel = remember { LoginViewModel(repository) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val state by viewModel.loginState.collectAsState()

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Login Financeiro", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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
                onClick = { viewModel.login(email, password) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Entrar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { onNavigateToRegister() }) {
                Text("Criar uma conta")
            }

            // Tratamento do Estado
            when (state) {
                is LoginUiState.Error -> {
                    val message = (state as LoginUiState.Error).message
                    LaunchedEffect(message) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        viewModel.resetState()
                    }
                }
                is LoginUiState.Success -> {
                    val user = (state as LoginUiState.Success).user
                    LaunchedEffect(user) {
                        Toast.makeText(context, "Bem-vindo, ${user.name}!", Toast.LENGTH_SHORT).show()
                        onLoginSuccess(user) // Passa o objeto usuário para a MainActivity
                        viewModel.resetState()
                    }
                }
                else -> {}
            }
        }
    }
}