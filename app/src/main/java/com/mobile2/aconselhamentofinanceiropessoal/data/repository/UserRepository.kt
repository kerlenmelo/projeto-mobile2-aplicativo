package com.mobile2.aconselhamentofinanceiropessoal.data.repository

import android.content.Context
import com.mobile2.aconselhamentofinanceiropessoal.data.local.AppDatabase
import com.mobile2.aconselhamentofinanceiropessoal.data.model.UserModel

class UserRepository(context: Context) {

    private val userDao = AppDatabase.getDatabase(context).userDao()

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