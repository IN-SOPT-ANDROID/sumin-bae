package org.sopt.sample.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSignUpDto(
    val message: String,
    val newUser: NewUser,
    val status: Int
) {
    @Serializable
    data class NewUser(
        val bio: String?,
        val email: String,
        val id: Int,
        val name: String,
        @SerialName("password") val pw: String,
        @SerialName("profileImage") val image: String?
    )
}
