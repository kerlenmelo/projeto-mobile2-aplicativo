package com.mobile2.aconselhamentofinanceiropessoal.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile2.aconselhamentofinanceiropessoal.data.model.TransactionEntity
import com.mobile2.aconselhamentofinanceiropessoal.data.repository.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: TransactionRepository,
    private val userId: Int // Agora sabemos quem é o usuário!
) : ViewModel() {

    private val _transactions = MutableStateFlow<List<TransactionEntity>>(emptyList())
    val transactions: StateFlow<List<TransactionEntity>> = _transactions

    private val _balance = MutableStateFlow(0.0)
    val balance: StateFlow<Double> = _balance

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            // Busca transações apenas deste usuário
            repository.getAllTransactions(userId).collectLatest { list ->
                _transactions.value = list
            }
        }
        viewModelScope.launch {
            // Busca saldo apenas deste usuário
            repository.getBalance(userId).collectLatest { value ->
                _balance.value = value
            }
        }
    }

    fun addTransaction(transaction: TransactionEntity) {
        viewModelScope.launch {
            // A transaction já vem com o userId preenchido pela MainActivity
            repository.insert(transaction)
        }
    }

    fun updateTransaction(transaction: TransactionEntity) {
        viewModelScope.launch {
            repository.update(transaction)
        }
    }

    fun deleteTransaction(transaction: TransactionEntity) {
        viewModelScope.launch {
            repository.delete(transaction)
        }
    }
}