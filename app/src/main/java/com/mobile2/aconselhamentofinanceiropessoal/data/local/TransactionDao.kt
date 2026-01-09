package com.mobile2.aconselhamentofinanceiropessoal.data.local

import androidx.room.*
import com.mobile2.aconselhamentofinanceiropessoal.data.model.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionEntity)

    @Update
    suspend fun update(transaction: TransactionEntity)

    @Delete
    suspend fun delete(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions WHERE userId = :userId ORDER BY date DESC")
    fun getAllTransactions(userId: Int): Flow<List<TransactionEntity>>

    @Query("SELECT SUM(value) FROM transactions WHERE userId = :userId AND type = :type")
    fun getTotalByType(userId: Int, type: String): Flow<Double?>

    @Query("SELECT SUM(CASE WHEN type = 'Receita' THEN value ELSE -value END) FROM transactions WHERE userId = :userId")
    fun getBalance(userId: Int): Flow<Double?>
}
