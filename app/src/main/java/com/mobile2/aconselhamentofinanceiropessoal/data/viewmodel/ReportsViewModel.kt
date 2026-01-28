package com.mobile2.aconselhamentofinanceiropessoal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile2.aconselhamentofinanceiropessoal.data.model.TransactionEntity
import com.mobile2.aconselhamentofinanceiropessoal.data.repository.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ReportsViewModel(
    private val repository: TransactionRepository,
    private val userId: Int
) : ViewModel() {

    private val _balance = MutableStateFlow(0.0)
    val balance: StateFlow<Double> = _balance

    private val _incomes = MutableStateFlow(0.0)
    val incomes: StateFlow<Double> = _incomes

    private val _expenses = MutableStateFlow(0.0)
    val expenses: StateFlow<Double> = _expenses

    private val _transactions = MutableStateFlow<List<TransactionEntity>>(emptyList())
    val transactions: StateFlow<List<TransactionEntity>> = _transactions

    init {
        fetchReportData()
    }

    private fun fetchReportData() {
        viewModelScope.launch {
            repository.getBalance(userId).collectLatest { _balance.value = it }
        }
        viewModelScope.launch {
            repository.getTotalByType(userId, "Receita").collectLatest { _incomes.value = it }
        }
        viewModelScope.launch {
            repository.getTotalByType(userId, "Despesa").collectLatest { _expenses.value = it }
        }
        viewModelScope.launch {
            repository.getAllTransactions(userId).collectLatest { _transactions.value = it }
        }
    }
}