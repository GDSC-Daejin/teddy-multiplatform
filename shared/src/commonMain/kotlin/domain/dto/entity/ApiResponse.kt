package domain.dto.entity

import kotlinx.serialization.Serializable

@Serializable
class ApiResponse<T>(
    val error: Boolean,
    val code: Int,
    val message: String,
    val data: T
)
