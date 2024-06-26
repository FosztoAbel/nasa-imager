package models

import kotlinx.serialization.Serializable

@Serializable
data class ServerError(
    val error: ErrorDetails
)

@Serializable
data class ErrorDetails(
    val code: String,
    val message: String
)