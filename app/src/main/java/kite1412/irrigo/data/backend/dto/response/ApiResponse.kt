package kite1412.irrigo.data.backend.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<out T>(
    val success: Boolean,
    val data: T? = null,
    val message: String? = null
)
