package org.sopt.sample.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FollowerService {
    @GET("/api/users?")
    fun getUserList(
        @Query("page") index: Int
    ): Call<ResponseFollowerListDto>
}