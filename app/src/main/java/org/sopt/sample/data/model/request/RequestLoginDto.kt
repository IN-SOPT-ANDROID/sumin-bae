package org.sopt.sample.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestLoginDto(
    val email: String,
    @SerialName("password") val pw: String
)
