package org.sopt.sample.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

// Retrofit 로그인, 회원가입 객체
object ApiFactory {

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://3.39.169.52:3000/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))    //
            .build()
    }
    inline fun <reified T> create(): T = retrofit.create<T>(T::class.java)
    //    val authService by lazy { retrofit.create<AuthService>() }
}

object ServicePool {
    val loginService = ApiFactory.create<LoginService>()
}