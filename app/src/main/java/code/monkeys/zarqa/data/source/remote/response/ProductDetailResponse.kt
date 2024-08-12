package code.monkeys.zarqa.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ProductDetailResponse(
    @field:SerializedName("images")
    val images: List<String>,

    @field:SerializedName("color")
    val color: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("total_stock")
    val totalStock: Int,

    @field:SerializedName("productType")
    val productType: List<ProductTypeItem>
)
