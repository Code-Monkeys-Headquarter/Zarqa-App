package code.monkeys.zarqa.data.model

import java.nio.file.attribute.UserPrincipal

data class User (
    private val id: Int?,
    private val fullname: String?,
    private val email: String?,
    private val password: String?,
    private val phone: Int?,
    private val role: String?
)