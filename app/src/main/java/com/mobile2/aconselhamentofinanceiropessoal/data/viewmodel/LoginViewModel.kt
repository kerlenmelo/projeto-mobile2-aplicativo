package com.mobile2.aconselhamentofinanceiropessoal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile2.aconselhamentofinanceiropessoal.data.model.UserModel
import com.mobile2.aconselhamentofinanceiropessoal.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginState: StateFlow<LoginUiState> = _loginState

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _loginState.value = LoginUiState.Error("Preencha todos os campos")
            return
        }

        viewModelScope.launch {
            val user = repository.loginUser(email, password)
            if (user != null) {
                _loginState.value = LoginUiState.Success(user)
            } else {
                _loginState.value = LoginUiState.Error("Usuário ou senha inválidos")
            }
        }
    }

    fun resetState() {
        _loginState.value = LoginUiState.Idle
    }
}

sealed class LoginUiState {
    object Idle : LoginUiState()
    data class Success(val user: UserModel) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}