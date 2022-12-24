package org.sopt.sample.presentation.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.R
import org.sopt.sample.util.UiState
import org.sopt.sample.databinding.ActivitySignUpBinding
import org.sopt.sample.presentation.login.LoginActivity

class SignUpActivity : AppCompatActivity() {
    private val viewModel by viewModels<SignUpViewModel>()
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        viewModel.signupResult.observe(this) {
            when (it) {
                UiState.Success -> intentToLogin()
                UiState.Failure -> Snackbar.make(binding.root, R.string.sign_up_duplicate, Snackbar.LENGTH_SHORT).show()
                UiState.Error -> Snackbar.make(binding.root, R.string.sign_up_fail, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun intentToLogin()  {
        Toast.makeText(this@SignUpActivity, R.string.sign_up_success, Toast.LENGTH_SHORT)
            .show()
        val intent = Intent(this@SignUpActivity, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
    }
}

