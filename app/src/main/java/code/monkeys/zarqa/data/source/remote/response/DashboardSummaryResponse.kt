package code.monkeys.zarqa.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DashboardSummaryResponse(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: DataDashboardSummary? = null,

    @field:SerializedName("success")
    val success: Boolean? = null
) : Parcelable

@Parcelize
data class DataDashboardSummary(

    @field:SerializedName("successProductCountToday")
    val successProductCountToday: Int? = null,

    @field:SerializedName("totalProductPrice")
    val totalProductPrice: Int? = null,

    @field:SerializedName("todayProductCount")
    val todayProductCount: Int? = null,

    @field:SerializedName("totalProductCount")
    val totalProductCount: Int? = null
) : Parcelable
