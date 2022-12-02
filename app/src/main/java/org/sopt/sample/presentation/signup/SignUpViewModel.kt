package org.sopt.sample.presentation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import org.sopt.sample.data.model.request.RequestSignUpDto
import org.sopt.sample.data.model.response.ResponseSignUpDto
import org.sopt.sample.data.service.ServicePool
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class SignUpViewModel: ViewModel() {
    val inputId = MutableLiveData<String>()
    val inputPw = MutableLiveData<String>()
    val inputName = MutableLiveData<String>()

    val isValidId = Transformations.map(inputId) { id -> checkId(id) }
    val isValidPw = Transformations.map(inputPw) { pw -> checkPw(pw) }
    val isValidName = Transformations.map(inputName) { name -> checkName(name) }

    private fun checkId(id: String): Boolean {
        val idPattern = "^(?=.*[A-Za-z])(?=.*[0-9]).{6,10}\$"
        return id.matches(idPattern.toRegex())
    }
    private fun checkPw(pw: String): Boolean {
        val pwPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#\$%^&*]).{6,12}\$"
        return pw.matches(pwPattern.toRegex())
    }
    private fun checkName(name: String): Boolean {
        return name.isNotEmpty()
    }

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val loginService = ServicePool.loginService

    fun signup(id: String, password: String, name: String) {
        loginService.signup(
            RequestSignUpDto(
                id, password, name
            )
        ).enqueue(object : Callback<ResponseSignUpDto> {
            override fun onResponse(
                call: Call<ResponseSignUpDto>,
                response: Response<ResponseSignUpDto>,
            ) {
                if (response.isSuccessful) {
                    _errorMessage.value = ""
                } else {
                    if (response.code() in 400..499) {
                        _errorMessage.value = "이미 가입된 아이디입니다"
                    } else {
                        _errorMessage.value = "서버에 문제가 발생했습니다"
                    }
                }
            }

            override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                _errorMessage.value = "네트워크 연결에 문제가 있습니다"
            }

        })
    }
}