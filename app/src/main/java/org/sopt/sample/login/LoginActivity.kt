package org.sopt.sample.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.main.MainActivity
import org.sopt.sample.R
import org.sopt.sample.data.local.SeminarApp
import org.sopt.sample.signup.SignUpActivity
import org.sopt.sample.data.remote.RequestLoginDto
import org.sopt.sample.data.remote.ResponseLoginDto
import org.sopt.sample.data.remote.ServicePool
import org.sopt.sample.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginService = ServicePool.loginService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val inputEmail = binding.etLoginEmail.text
        val inputPw = binding.etLoginPw.text

        binding.btnLoginSubmit.setOnClickListener {
            // 입력칸이 비었을 때
            if (inputEmail.isEmpty() || inputPw.isEmpty()) {
                Snackbar.make(binding.root, R.string.login_empty, Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginService.login(
                RequestLoginDto(
                    binding.etLoginEmail.text.toString(),
                    binding.etLoginPw.text.toString()
                )
            ).enqueue(object : Callback<ResponseLoginDto> {
                // 서버통신 성공
                override fun onResponse(
                    call: Call<ResponseLoginDto>,
                    response: Response<ResponseLoginDto>
                ) {
                    // 로그인 성공
                    if (response.isSuccessful) {
                        Toast.makeText(this@LoginActivity, R.string.login_success, Toast.LENGTH_SHORT).show()
                        intentToHome(response.body()!!.result.name)
                    }
                    // 로그인 실패
                    else {
                        Snackbar.make(binding.root, R.string.login_wrong_input, Snackbar.LENGTH_SHORT).show()
                    }
                }
                // 서버통신 실패
                override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
                    Snackbar.make(binding.root, R.string.login_fail, Snackbar.LENGTH_SHORT).show()
                    Log.e("failed login", "cause: " + t.cause)
                    Log.e("failed login", "message: " + t.message)
                }
            })
        }
        binding.btnToSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            clearInput()
        }
    }

    // 입력창 텍스트 제거
    private fun clearInput() {
        binding.etLoginEmail.text.clear()
        binding.etLoginPw.text.clear()
    }

    // 홈 페이지로 이동
    private fun intentToHome(name: String) {
        SeminarApp.prefs.apply {
            setString("email", binding.etLoginEmail.text.toString())
            setString("pw", binding.etLoginPw.text.toString())
            setString("name", name)
        }
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }
}