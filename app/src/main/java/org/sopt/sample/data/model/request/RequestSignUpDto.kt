package org.sopt.sample.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestSignUpDto(
    val email: String,
    @SerialName("password") val pw: String,
    val name: String
)
