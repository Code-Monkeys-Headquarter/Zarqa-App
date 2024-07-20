package code.monkeys.zarqa.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import code.monkeys.zarqa.data.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password AND role = :role")
    suspend fun getUser(email: String, password: String, role: String): User?

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?
}