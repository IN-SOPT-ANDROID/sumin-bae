package org.sopt.sample.presentation.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivitySignUpBinding
import org.sopt.sample.presentation.login.LoginActivity

class SignUpActivity : AppCompatActivity() {
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 잊지 않겠습니다....
        binding.signup = viewModel
        binding.lifecycleOwner = this

        binding.btnSignupSubmit.setOnClickListener {
            viewModel.signup(
                binding.etSignupId.text.toString(),
                binding.etSignupPw.text.toString(),
                binding.etSignupName.text.toString()
            )
        }
        viewModel.errorMessage.observe(this) {
            if (it == "") {
                Toast.makeText(this@SignUpActivity, R.string.sign_up_success, Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(this@SignUpActivity, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                }
                startActivity(intent)
            } else {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }

        }
    }
}

