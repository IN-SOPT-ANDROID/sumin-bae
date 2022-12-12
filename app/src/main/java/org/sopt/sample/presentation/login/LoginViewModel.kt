package org.sopt.sample.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.sample.SeminarApp
import org.sopt.sample.data.model.request.RequestLoginDto
import org.sopt.sample.data.model.response.ResponseLoginDto
import org.sopt.sample.data.service.ServicePool
import org.sopt.sample.data.service.UiState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    // backing property
    private val _loginResult = MutableLiveData<UiState>()
    val loginResult: LiveData<UiState>
        get() = _loginResult

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean>
        get() = _isLogin

    private val loginService = ServicePool.loginService


    // functions
    fun autoLogin() {
        _isLogin.value = SeminarApp.prefs.isSignedIn()
    }

    fun login(id: String, password: String) {
        loginService.login(
            RequestLoginDto(
                id, password
            )
        ).enqueue(object : Callback<ResponseLoginDto> {
            override fun onResponse(
                call: Call<ResponseLoginDto>,
                response: Response<ResponseLoginDto>,
            ) {
                if (response.isSuccessful) {
                    _loginResult.value = UiState.Success
                    response.body()?.result?.let {
                        SeminarApp.prefs.apply {
                            setString("id", it.email)
                            setString("pw", it.pw)
                            setString("name", it.name)
                        }
                    }
                } else {
                    _loginResult.value = UiState.Failure
                }
            }

            override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
                _loginResult.value = UiState.Error
            }
        })
    }
}