package com.mobile2.aconselhamentofinanceiropessoal.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile2.aconselhamentofinanceiropessoal.data.model.TransactionEntity
import com.mobile2.aconselhamentofinanceiropessoal.data.repository.TransactionRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel(context: Context) : ViewModel() {

    private val repository = TransactionRepository(context)
    private val userId = 1

    val transactions = repository.getAllTransactions(userId).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val balance = repository.getBalance(userId)
        .map { it ?: 0.0 }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            0.0
        )


    fun addTransaction(type: String, category: String, description: String, value: Double) {
        viewModelScope.launch {
            val transaction = TransactionEntity(
                userId = userId,
                type = type,
                category = category,
                description = description,
                value = value,
                date = Date()
            )
            repository.insert(transaction)
        }
    }
}
