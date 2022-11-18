package org.sopt.sample.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import org.sopt.sample.R
import org.sopt.sample.data.local.SeminarApp
import org.sopt.sample.databinding.ActivityMainBinding
import org.sopt.sample.login.LoginActivity
import org.sopt.sample.main.follower.FollowerFragment
import org.sopt.sample.main.gallery.GalleryFragment
import org.sopt.sample.main.mypage.MyPageFragment
import org.sopt.sample.main.search.SearchFragment

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 로그인하지 않은 상태이면 로그인 페이지로 이동
        if (SeminarApp.prefs.getString("email", "") == "") {
            val intent = Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }

        // 프래그먼트 전환
        changeFragment<FollowerFragment>()
        binding.bnvMain.setOnItemSelectedListener { menu ->
            when(menu.itemId) {
                R.id.menu_follower -> changeFragment<FollowerFragment>()
                R.id.menu_gallery -> changeFragment<GalleryFragment>()
                R.id.menu_search -> changeFragment<SearchFragment>()
                R.id.menu_mypage -> changeFragment<MyPageFragment>()
            }
            return@setOnItemSelectedListener true
        }
    }

    private inline fun <reified T: Fragment> changeFragment() {
        supportFragmentManager.commit {
            replace<T>(R.id.fcv_main_container)
        }
    }
}