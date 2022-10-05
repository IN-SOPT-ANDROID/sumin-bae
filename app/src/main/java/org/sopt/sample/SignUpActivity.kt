package org.sopt.sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignUpBinding

    // 회원가입 성공 조건
    private fun isValidId(id : String) : Boolean {
        return id.length in 6..10
    }
    private fun isValidPw(pw : String) : Boolean {
        return pw.length in 8..12
    }

    // 성공 여부 판단
    private fun checkSignup() {
        val nameText = binding.signupNameInput.text.toString()
        val idText = binding.signupIdInput.text.toString()
        val pwText = binding.signupPwInput.text.toString()
        val mbtiText = binding.signupMbtiInput.text.toString()

        if (isValidId(idText) && isValidPw(pwText)) {
            intentToLogin(nameText, idText, pwText, mbtiText)
        } else if (isValidId(idText)) {
            Snackbar.make(binding.root, "비밀번호를 8-12글자 사이로 설정하세요", Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(binding.root, "아이디를 6-10글자 사이로 설정하세요", Snackbar.LENGTH_SHORT).show()
        }
    }

    // 로그인 페이지로 이동
    private fun intentToLogin(name : String, id: String, pw: String, mbti : String) {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("name", name)
        intent.putExtra("id", id)
        intent.putExtra("pw", pw)
        intent.putExtra("mbti", mbti)

        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupSubmitButton.setOnClickListener {
            checkSignup()
        }
    }
}