package code.monkeys.zarqa.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val fullname: String?,
    val email: String?,
    val password: String?,
    val role: String?
)