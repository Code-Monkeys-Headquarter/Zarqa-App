package code.monkeys.zarqa.data.source.remote.request.product

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("name") val name: String,
    @SerializedName("images") val images: List<String>? = null ,
    @SerializedName("color") val color: String,
    @SerializedName("productType") val productType: List<ProductType>
)

data class ProductType(
    @SerializedName("size") val size: String,
    @SerializedName("price") val price: Int,
    @SerializedName("stock") val stock: Int
)
