package com.mobile2.aconselhamentofinanceiropessoal.data.repository

import android.content.Context
import androidx.room.Room
import com.mobile2.aconselhamentofinanceiropessoal.data.local.AppDatabase
import com.mobile2.aconselhamentofinanceiropessoal.data.model.TransactionEntity

class TransactionRepository(context: Context) {

    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "finansmart_db"
    ).fallbackToDestructiveMigration().build()

    private val dao = db.transactionDao()

    suspend fun insert(transaction: TransactionEntity) = dao.insert(transaction)
    suspend fun update(transaction: TransactionEntity) = dao.update(transaction)
    suspend fun delete(transaction: TransactionEntity) = dao.delete(transaction)
    fun getAllTransactions(userId: Int) = dao.getAllTransactions(userId)
    fun getTotalByType(userId: Int, type: String) = dao.getTotalByType(userId, type)
    fun getBalance(userId: Int) = dao.getBalance(userId)
}
