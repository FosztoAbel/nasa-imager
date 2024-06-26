package models

import kotlinx.serialization.Serializable

@Serializable
data class NasaApiResponse(
    val copyright: String? = null,
    val date: String,
    val explanation: String? = null,
    val hdurl: String? = null,
    val media_type: String? = null,
    val service_version: String? = null,
    val title: String,
    val url: String
)