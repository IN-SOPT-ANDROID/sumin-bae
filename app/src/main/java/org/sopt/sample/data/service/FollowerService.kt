package org.sopt.sample.data.service

import org.sopt.sample.data.model.response.ResponseFollowerListDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FollowerService {
    @GET("/api/users?")
    fun getUserList(
        @Query("per_page") index: Int = 12
    ): Call<ResponseFollowerListDto>
}