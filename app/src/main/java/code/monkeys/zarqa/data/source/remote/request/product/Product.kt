package code.monkeys.zarqa.data.source.remote.request.product

data class Product(
    val name: String,
    val images: List<String>,
    val color: String,
    val productType: List<ProductType>
)

data class ProductType(
    val size: String,
    val price: Int,
    val stock: Int,
    val lowStockAlert: Int
)
