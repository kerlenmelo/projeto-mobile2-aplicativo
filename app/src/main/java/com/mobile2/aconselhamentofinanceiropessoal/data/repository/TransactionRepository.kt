package com.mobile2.aconselhamentofinanceiropessoal.data.repository

import com.mobile2.aconselhamentofinanceiropessoal.data.local.TransactionDao
import com.mobile2.aconselhamentofinanceiropessoal.data.model.TransactionEntity
import kotlinx.coroutines.flow.Flow

class TransactionRepository(private val transactionDao: TransactionDao) {

    fun getAllTransactions(userId: Int): Flow<List<TransactionEntity>> {
        return transactionDao.getAllTransactions(userId)
    }

    fun getBalance(userId: Int): Flow<Double> {
        return transactionDao.getBalance(userId)
    }

    fun getTotalByType(userId: Int, type: String): Flow<Double> {
        return transactionDao.getTotalByType(userId, type)
    }

    suspend fun insert(transaction: TransactionEntity) {
        transactionDao.insert(transaction)
    }

    suspend fun update(transaction: TransactionEntity) {
        transactionDao.update(transaction)
    }

    suspend fun delete(transaction: TransactionEntity) {
        transactionDao.delete(transaction)
    }
}