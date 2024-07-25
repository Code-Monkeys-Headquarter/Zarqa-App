package code.monkeys.zarqa.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_table")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: Int, // Foreign key referring to Product
    val quantity: Int,
    val type: String, // 'IN' for stock in, 'OUT' for stock out
    val date: String
)
