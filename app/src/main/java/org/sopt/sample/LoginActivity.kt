package org.sopt.sample

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private var userInfo = arrayOf("", "", "", "")

    // 회원가입 페이지에서 입력한 정보 가져오기
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Snackbar.make(binding.root, "회원가입이 완료되었습니다!", Snackbar.LENGTH_SHORT).show()
            val info : Intent? = result.data

            val userName = info?.getStringExtra("name").toString()
            val userId = info?.getStringExtra("id").toString()
            val userPw = info?.getStringExtra("pw").toString()
            val userMbti = info?.getStringExtra("mbti").toString().uppercase()

            userInfo = arrayOf(userId, userPw, userName, userMbti)
        }
    }

    // 아이디 비밀번호 입력란이 비었는지 판단
    private fun isEmpty(inputId : String, inputPw : String) : Boolean {
        return inputId == "" && inputPw == ""
    }

    // 로그인 성공 여부 판단
    private fun isMember(inputId : String, inputPw : String, id : String, pw : String) : Boolean {
        return inputId == id && inputPw == pw
    }

    // 홈 페이지로 이동
    private fun intentToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("name", userInfo[2])
        intent.putExtra("mbti", userInfo[3])

        setResult(RESULT_OK, intent)
        startActivity(intent)
        finish()
    }

    // 성공 여부에 따라 다르게 실행
    private fun executeLogin() {
        val inputId = binding.loginIdInput.text.toString()
        val inputPw = binding.loginPwInput.text.toString()

        if (isEmpty(inputId, inputPw)){
            Snackbar.make(binding.root, "아이디 또는 비밀번호를 입력해주세요", Snackbar.LENGTH_SHORT).show()
        }
        else if (!isMember(inputId, inputPw, userInfo[0], userInfo[1])) {
            Snackbar.make(binding.root, "아이디 또는 비밀번호를 잘못 입력했습니다", Snackbar.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(this, "로그인에 성공했습니다!", Toast.LENGTH_SHORT).show()
            intentToHome()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            resultLauncher.launch(intent)
        }
        binding.loginButton.setOnClickListener {
            executeLogin()
        }
    }
}