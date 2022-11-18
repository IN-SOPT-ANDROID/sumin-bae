package org.sopt.sample.data.remote

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