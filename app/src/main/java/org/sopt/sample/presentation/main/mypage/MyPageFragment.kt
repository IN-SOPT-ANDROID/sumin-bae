package org.sopt.sample.presentation.main.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.sopt.sample.R
import org.sopt.sample.SeminarApp
import org.sopt.sample.databinding.FragmentMyPageBinding
import org.sopt.sample.presentation.login.LoginActivity

class MyPageFragment : Fragment() {
    private var _binding: FragmentMyPageBinding? = null
    private val binding: FragmentMyPageBinding
        get() = requireNotNull(_binding) { "value of _binding is null" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        binding.tvMypageName.text = SeminarApp.prefs.getString("name", "")
        binding.tvMypageEmail.text = SeminarApp.prefs.getString("email", "")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnMypageLogout.setOnClickListener {
            Toast.makeText(activity, R.string.logout_success, Toast.LENGTH_SHORT).show()
            SeminarApp.prefs.apply {
                setString("email", "")
                setString("pw", "")
                setString("name", "")
            }
            val intent = Intent(activity, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}