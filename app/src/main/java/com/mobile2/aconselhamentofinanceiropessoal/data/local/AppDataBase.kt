package com.mobile2.aconselhamentofinanceiropessoal.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mobile2.aconselhamentofinanceiropessoal.data.model.UserModel

@Database(entities = [UserModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
