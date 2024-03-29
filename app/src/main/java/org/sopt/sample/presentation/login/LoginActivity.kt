package org.sopt.sample.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.R
import org.sopt.sample.util.UiState
import org.sopt.sample.databinding.ActivityLoginBinding
import org.sopt.sample.presentation.main.MainActivity
import org.sopt.sample.presentation.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        // 로그인한 상태이면 메인 페이지로 이동
        viewModel.autoLogin()

        addObservers()
        addListeners()
    }

    private fun addListeners() {
        binding.btnLoginSubmit.setOnClickListener {
            val inputId = binding.etLoginId.text
            val inputPw = binding.etLoginPw.text

            // 입력칸이 비었을 때
            if (inputId.isEmpty() || inputPw.isEmpty()) {
                Snackbar.make(binding.root, R.string.login_empty, Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
        binding.btnToSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addObservers() {
        viewModel.isLogin.observe(this) {
            if (it) {
                intentToHome()
            }
        }
        viewModel.loginResult.observe(this) {
            when(it) {
                UiState.Success -> {
                    showSuccessToast()
                    intentToHome()
                }
                UiState.Failure -> Snackbar.make(binding.root, R.string.login_wrong_input, Snackbar.LENGTH_SHORT).show()
                UiState.Error -> Snackbar.make(binding.root, R.string.login_request_fail, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun showSuccessToast() {
        Toast.makeText(this@LoginActivity,
            R.string.login_success,
            Toast.LENGTH_SHORT).show()
    }

    private fun intentToHome() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }
}