package org.sopt.sample.data.service

import org.sopt.sample.data.model.request.RequestLoginDto
import org.sopt.sample.data.model.request.RequestSignUpDto
import org.sopt.sample.data.model.response.ResponseLoginDto
import org.sopt.sample.data.model.response.ResponseSignUpDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("api/user/signin")
    fun login(
        @Body request: RequestLoginDto
    ): Call<ResponseLoginDto>

    @POST("api/user/signup")
    fun signup(
        @Body request: RequestSignUpDto
    ): Call<ResponseSignUpDto>
}