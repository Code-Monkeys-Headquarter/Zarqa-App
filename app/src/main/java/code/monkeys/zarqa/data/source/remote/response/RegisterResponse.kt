package code.monkeys.zarqa.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("status")
	val status: String
)