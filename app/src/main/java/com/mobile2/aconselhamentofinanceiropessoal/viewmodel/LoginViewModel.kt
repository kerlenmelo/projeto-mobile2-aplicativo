package com.mobile2.aconselhamentofinanceiropessoal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile2.aconselhamentofinanceiropessoal.data.model.UserModel
import com.mobile2.aconselhamentofinanceiropessoal.data.repository.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    private val _loginResult = MutableStateFlow<UserModel?>(null)
    val loginResult: StateFlow<UserModel?> = _loginResult

    private val _registerResult = MutableStateFlow<Boolean?>(null)
    val registerResult: StateFlow<Boolean?> = _registerResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val user = repository.loginUser(email, password)
            _loginResult.value = user
        }
    }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            val success = repository.registerUser(UserModel(name = name, email = email, password = password))
            _registerResult.value = success
        }
    }
}
