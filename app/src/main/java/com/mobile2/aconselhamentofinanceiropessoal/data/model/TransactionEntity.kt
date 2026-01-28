package com.mobile2.aconselhamentofinanceiropessoal.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val type: String,
    val category: String,
    val description: String,
    val value: Double,
    val date: Date = Date()
)
