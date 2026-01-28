package com.mobile2.aconselhamentofinanceiropessoal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile2.aconselhamentofinanceiropessoal.data.model.UserModel
import com.mobile2.aconselhamentofinanceiropessoal.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {

    private val _registerResult = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val registerResult: StateFlow<RegisterUiState> = _registerResult

    fun register(name: String, email: String, password: String) {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            _registerResult.value = RegisterUiState.Error("Preencha todos os campos")
            return
        }

        viewModelScope.launch {
            val success = repository.registerUser(
                UserModel(name = name, email = email, password = password)
            )
            if (success) {
                _registerResult.value = RegisterUiState.Success
            } else {
                _registerResult.value = RegisterUiState.Error("E-mail já cadastrado")
            }
        }
    }

    fun resetState() {
        _registerResult.value = RegisterUiState.Idle
    }
}

sealed class RegisterUiState {
    object Idle : RegisterUiState()
    object Success : RegisterUiState()
    data class Error(val message: String) : RegisterUiState()
}