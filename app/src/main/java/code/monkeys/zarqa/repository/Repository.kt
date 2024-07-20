package code.monkeys.zarqa.repository

import android.content.Context
import code.monkeys.zarqa.data.model.User
import code.monkeys.zarqa.data.source.local.database.AppDatabase

class Repository(context: Context) {
    private val userDao = AppDatabase.getDatabase(context).userDao()

    suspend fun register(user: User) {
        userDao.createUser(user)
    }
    suspend fun login(email: String, password: String): User? {
        return userDao.loginUser(email, password)
    }
}