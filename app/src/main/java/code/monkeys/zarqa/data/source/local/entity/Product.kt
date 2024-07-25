package code.monkeys.zarqa.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class Product(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productImage: String? = null,
    val productName: String,
    val productPrice: Int,
    val productColor: String,
    val productTotalStock: Int,
    val productLowStockAlert: Int,
    val size: String,
    val dateAdded: String? = null // Format Date as "2023-07-01" or "yyyy-MM-dd"
)
