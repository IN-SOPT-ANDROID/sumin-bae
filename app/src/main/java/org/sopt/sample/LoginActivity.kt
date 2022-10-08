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
    // private var userInfo = arrayOf("", "", "", "")
    private var userInfo: User? = null

    // 회원가입 페이지에서 입력한 정보 가져오기
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Snackbar.make(binding.root, R.string.sign_up_success, Snackbar.LENGTH_SHORT).show()
            val info : Intent? = result.data
            userInfo = info?.getSerializableExtra("info") as User
            // val userName = info?.getStringExtra("name").toString()
            //val userId = info?.getStringExtra("id").toString()
            // val userPw = info?.getStringExtra("pw").toString()
            // val userMbti = info?.getStringExtra("mbti").toString().uppercase()
        // userInfo = arrayOf(userId, userPw, userName, userMbti)
        }
    }

    // 아이디 비밀번호 입력란이 비었는지 판단
    private fun isEmpty(inputId : String, inputPw : String) : Boolean {
        return inputId == "" && inputPw == ""
    }

    // 로그인 성공 여부 판단
    private fun isMember(inputId : String, inputPw : String, id : String?, pw : String?) : Boolean {
        return inputId == id && inputPw == pw
    }

    // 홈 페이지로 이동
    private fun intentToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("name", userInfo?.name)
        intent.putExtra("mbti", userInfo?.mbti)

        setResult(RESULT_OK, intent)
        startActivity(intent)
        finish()
    }

    // 성공 여부에 따라 다르게 실행
    private fun executeLogin() {
        val inputId = binding.etLoginId.text.toString()
        val inputPw = binding.etLoginPw.text.toString()

        if (isEmpty(inputId, inputPw)){
            Snackbar.make(binding.root, R.string.login_empty, Snackbar.LENGTH_SHORT).show()
        }
        else if (!isMember(inputId, inputPw, userInfo?.id, userInfo?.pw)) {
            Snackbar.make(binding.root, R.string.login_fail, Snackbar.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show()
            intentToHome()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnToSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            resultLauncher.launch(intent)
        }
        binding.btnLoginSubmit.setOnClickListener {
            executeLogin()
        }
    }
}