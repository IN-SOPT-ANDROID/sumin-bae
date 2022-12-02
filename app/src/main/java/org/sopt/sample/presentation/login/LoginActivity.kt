package org.sopt.sample.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivityLoginBinding
import org.sopt.sample.presentation.main.MainActivity
import org.sopt.sample.presentation.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLoginSubmit.setOnClickListener {
            val inputEmail = binding.etLoginEmail.text
            val inputPw = binding.etLoginPw.text

            // 입력칸이 비었을 때
            if (inputEmail.isEmpty() || inputPw.isEmpty()) {
                Snackbar.make(binding.root, R.string.login_empty, Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.login(
                binding.etLoginEmail.text.toString(),
                binding.etLoginPw.text.toString()
            )
        }
        binding.btnToSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        viewModel.loginResult.observe(this) {
            if (it.status in 200..299) {
                Toast.makeText(this@LoginActivity,
                    R.string.login_success,
                    Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
            }
        }
        viewModel.errorMessage.observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
    }
}