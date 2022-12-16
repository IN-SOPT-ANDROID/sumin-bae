package org.sopt.sample.presentation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import org.sopt.sample.data.model.request.RequestSignUpDto
import org.sopt.sample.data.model.response.ResponseSignUpDto
import org.sopt.sample.data.service.ServicePool
import org.sopt.sample.util.UiState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {
    val inputId = MutableLiveData("")
    val inputPw = MutableLiveData("")
    val inputName = MutableLiveData("")

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

    private val _signupResult = MutableLiveData<UiState>()
    val signupResult: LiveData<UiState>
        get() = _signupResult

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
                    _signupResult.value = UiState.Success
                } else {
                    _signupResult.value = UiState.Failure
                }
            }

            override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                _signupResult.value = UiState.Error
            }

        })
    }
}