package org.sopt.sample.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.sample.SeminarApp
import org.sopt.sample.data.model.request.RequestLoginDto
import org.sopt.sample.data.model.response.ResponseLoginDto
import org.sopt.sample.data.service.ServicePool
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    // backing property
    private val _loginResult = MutableLiveData<ResponseLoginDto>()
    val loginResult: LiveData<ResponseLoginDto>
        get() = _loginResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val loginService = ServicePool.loginService

    fun login(email: String, password: String) {
        loginService.login(
            RequestLoginDto(
                email, password
            )
        ).enqueue(object : Callback<ResponseLoginDto> {
            override fun onResponse(
                call: Call<ResponseLoginDto>,
                response: Response<ResponseLoginDto>,
            ) {
                if (response.isSuccessful) {
                    _loginResult.value = response.body()
                    response.body()?.result?.let {
                        SeminarApp.prefs.apply {
                            setString("email", it.email)
                            setString("pw", it.pw)
                            setString("name", it.name)
                        }
                    }
                } else {
                    if (response.code() in 400..499) {
                        _errorMessage.value = "아이디 또는 비밀번호를 잘못 입력했습니다"
                    } else {
                        _errorMessage.value = "서버에 문제가 발생했습니다"
                    }
                }
            }

            override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
                _errorMessage.value = "네트워크 연결에 문제가 있습니다"
            }

        })
    }
}