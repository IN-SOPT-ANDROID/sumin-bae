package org.sopt.sample.signup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.R
import org.sopt.sample.data.remote.RequestSignUpDto
import org.sopt.sample.data.remote.ResponseSignUpDto
import org.sopt.sample.data.remote.ServicePool
import org.sopt.sample.databinding.ActivitySignUpBinding
import org.sopt.sample.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val loginService = ServicePool.loginService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignupSubmit.isEnabled = false
        checkSignup()
        binding.btnSignupSubmit.setOnClickListener {
            loginService.signup(
                RequestSignUpDto(
                    binding.etSignupEmail.text.toString(),
                    binding.etSignupPw.text.toString(),
                    binding.etSignupName.text.toString()
                )
            ).enqueue(object : Callback<ResponseSignUpDto> {
                // 서버통신 성공
                override fun onResponse(
                    call: Call<ResponseSignUpDto>,
                    response: Response<ResponseSignUpDto>,
                ) {
                    // 회원가입 성공
                    if (response.isSuccessful) {
                        Toast.makeText(this@SignUpActivity, R.string.sign_up_success, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@SignUpActivity, LoginActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        }
                        startActivity(intent)
                    }
                    // 회원가입 실패 (중복)
                    else {
                        Snackbar.make(binding.root, R.string.sign_up_duplicate, Snackbar.LENGTH_SHORT).show()
                    }
                }
                // 서버통신 실패
                override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                    Snackbar.make(binding.root, R.string.sign_up_fail, Snackbar.LENGTH_SHORT).show()
                }

            })
        }
    }

    // 모든 입력값이 올바른지 확인
    private fun checkSignup() {
        binding.etSignupEmail.addTextChangedListener(textWatcher)
        binding.etSignupPw.addTextChangedListener(textWatcher)
        binding.etSignupName.addTextChangedListener(textWatcher)
    }

    // 회원가입 버튼 클릭 활성화
    private val textWatcher = object : TextWatcher {

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            val email = binding.etSignupEmail.text
            val pw = binding.etSignupPw.text
            val name = binding.etSignupName.text

            binding.btnSignupSubmit.isEnabled =
                email.isNotEmpty()
                        && (pw.isNotEmpty() && pw.toString().length in 8..12)
                        && name.isNotEmpty()
        }
    }
}

