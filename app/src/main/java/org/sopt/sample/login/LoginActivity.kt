package org.sopt.sample.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.main.MainActivity
import org.sopt.sample.R
import org.sopt.sample.SignUpActivity
import org.sopt.sample.User
import org.sopt.sample.data.remote.RequestLogin
import org.sopt.sample.data.remote.ResponseLogin
import org.sopt.sample.data.remote.ServicePool
import org.sopt.sample.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var userInfo: User? = null
    private val loginService = ServicePool.loginService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnToSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            resultLauncher.launch(intent)
        }
        binding.btnLoginSubmit.setOnClickListener {
            loginService.login(
                RequestLogin(
                    binding.etLoginId.text.toString(),
                    binding.etLoginPw.text.toString()
                )
            ).enqueue(object : Callback<ResponseLogin> {
                override fun onResponse(
                    call: Call<ResponseLogin>,
                    response: Response<ResponseLogin>
                ) {
                    startActivity(MainActivity.getIntent(this@LoginActivity))
                }

                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "에러 발생", Toast.LENGTH_SHORT).show()
                }
            })
//            executeLogin()
        }
    }

    // 회원가입 페이지에서 입력한 정보 가져오기
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val info: Intent? = result.data
            userInfo = info?.getSerializableExtra("info") as User
        }
    }

    // 로그인 성공 여부 판단
    private fun isMember(inputId: String, inputPw: String, id: String?, pw: String?): Boolean =
        inputId == id && inputPw == pw

    // 홈 페이지로 이동
    private fun intentToHome() {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("name", userInfo?.name)
            putExtra("mbti", userInfo?.mbti)
        }
        setResult(RESULT_OK, intent)
        startActivity(intent)
        finish()
    }

    // 성공 여부에 따라 다르게 실행
    private fun executeLogin() {
        val inputId = binding.etLoginId.text.toString()
        val inputPw = binding.etLoginPw.text.toString()

        if (inputId.isEmpty() && inputPw.isEmpty()) {
            return Snackbar.make(binding.root, R.string.login_empty, Snackbar.LENGTH_SHORT).show()
        }
        if (!isMember(inputId, inputPw, userInfo?.id, userInfo?.pw)) {
            return Snackbar.make(binding.root, R.string.login_fail, Snackbar.LENGTH_SHORT).show()
        }
        Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show()
        intentToHome()
    }
}