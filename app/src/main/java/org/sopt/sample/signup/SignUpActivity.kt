package org.sopt.sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.databinding.ActivitySignUpBinding
import org.sopt.sample.login.LoginActivity
import java.io.Serializable

data class User(
    val name: String,
    val id: String,
    val pw: String,
    val mbti: String
) : Serializable

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignupSubmit.setOnClickListener {
            checkSignup()
        }
    }

    // 회원가입 성공 조건
    private fun isValidId(id: String): Boolean = id.length in 6..10
    private fun isValidPw(pw: String): Boolean = pw.length in 8..12

    // 성공 여부 판단
    private fun checkSignup() {
        val nameText = binding.etSignupName.text.toString()
        val idText = binding.etSignupId.text.toString()
        val pwText = binding.etSignupPw.text.toString()
        val mbtiText = binding.etSignupMbti.text.toString()

        if (!isValidId(idText)) {
            return Snackbar.make(binding.root, R.string.sign_up_id_fail, Snackbar.LENGTH_SHORT)
                .show()
        }
        if (!isValidPw(pwText)) {
            return Snackbar.make(binding.root, R.string.sign_up_pw_fail, Snackbar.LENGTH_SHORT)
                .show()
        }
        val info = User(nameText, idText, pwText, mbtiText)
        intentToLogin(info)
    }

    // 로그인 페이지로 이동
    private fun intentToLogin(user: User) {
        val intent = Intent(this, LoginActivity::class.java).apply {
            putExtra("info", user as java.io.Serializable)
        }
        setResult(RESULT_OK, intent)
        finish()
    }
}