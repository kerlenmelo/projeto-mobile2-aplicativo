package com.mobile2.aconselhamentofinanceiropessoal.data.repository

import android.content.Context
import androidx.room.Room
import com.mobile2.aconselhamentofinanceiropessoal.data.local.AppDatabase
import com.mobile2.aconselhamentofinanceiropessoal.data.model.UserModel

class UserRepository(context: Context) {

    private val db = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "finance_app_db"
    ).build()

    private val userDao = db.userDao()

    suspend fun registerUser(user: UserModel): Boolean {
        val existing = userDao.findByEmail(user.email)
        return if (existing == null) {
            userDao.insertUser(user)
            true
        } else {
            false
        }
    }

    suspend fun loginUser(email: String, password: String): UserModel? {
        return userDao.login(email, password)
    }
}
