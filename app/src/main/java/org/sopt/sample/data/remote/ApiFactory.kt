package org.sopt.sample.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.sopt.sample.BuildConfig.BASE_URL_FOLLOWER
import org.sopt.sample.BuildConfig.BASE_URL_LOGIN
import retrofit2.Retrofit

// Retrofit 로그인, 회원가입 & 팔로워 객체
object ApiFactory {

    val retrofitLogin: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_LOGIN)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
    val retrofitFollower: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_FOLLOWER)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    inline fun <reified T> create(): T = retrofitLogin.create<T>(T::class.java)
    inline fun <reified T> createFollower(): T = retrofitFollower.create<T>(T::class.java)
}

object ServicePool {
    val loginService = ApiFactory.create<LoginService>()
    val followerService = ApiFactory.createFollower<FollowerService>()
}