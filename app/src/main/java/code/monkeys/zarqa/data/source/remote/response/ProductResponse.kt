package code.monkeys.zarqa.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductResponse(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: List<DataItem?>? = null,

    @field:SerializedName("status")
    val status: String? = null
) : Parcelable

@Parcelize
data class ProductTypeItem(

                                                                @field:SerializedName("size")
    val size: String? = null,

    @field:SerializedName("price")
    val price: Int? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("stock")
    val stock: Int?                                                                                                                                                                      = null
) : Parcelable

@Parcelize
data class Supplier(

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("email")
    val email: String? = null
) : Parcelable

@Parcelize
data class DataItem(

    @field:SerializedName("images")
    val images: List<String?>? = null,

    @field:SerializedName("color")
    val color: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("total_stock")
    val totalStock: Int? = null,

    @field:SerializedName("productType")
    val productType: List<ProductTypeItem?>? = null,

    @field:SerializedName("supplier")
    val supplier: Supplier? = null
) : Parcelable
