package org.sopt.sample.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseLoginDto(
    @SerialName("message") val message: String,
    @SerialName("result") val result: Result,
    @SerialName("status") val status: Int
) {
    @Serializable
    data class Result(
        @SerialName("bio") val bio: String?,
        @SerialName("email") val email: String,
        @SerialName("id") val id: Int,
        @SerialName("name") val name: String,
        @SerialName("password") val password: String,
        @SerialName("profileImage") val profileImage: String?
    )
}
