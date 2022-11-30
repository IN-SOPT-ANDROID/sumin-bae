package org.sopt.sample.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseLoginDto(
    val message: String,
    val result: Result,
    val status: Int
) {
    @Serializable
    data class Result(
        val bio: String?,
        val email: String,
        val id: Int,
        val name: String,
        @SerialName("password") val pw: String,
        @SerialName("profileImage") val image: String?
    )
}
