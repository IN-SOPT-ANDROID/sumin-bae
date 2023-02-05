package org.sopt.sample.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import org.sopt.sample.R
import org.sopt.sample.SeminarApp
import org.sopt.sample.databinding.ActivityMainBinding
import org.sopt.sample.presentation.login.LoginActivity
import org.sopt.sample.presentation.main.follower.FollowerFragment
import org.sopt.sample.presentation.main.gallery.GalleryFragment
import org.sopt.sample.presentation.main.mypage.MyPageFragment
import org.sopt.sample.presentation.main.search.SearchFragment

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 프래그먼트 전환
        changeFragment<FollowerFragment>()
        binding.bnvMain.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.menu_follower -> changeFragment<FollowerFragment>()
                R.id.menu_gallery -> changeFragment<GalleryFragment>()
                R.id.menu_search -> changeFragment<SearchFragment>()
                R.id.menu_mypage -> changeFragment<MyPageFragment>()
            }
            return@setOnItemSelectedListener true
        }
    }

    private inline fun <reified T : Fragment> changeFragment() {
        supportFragmentManager.commit {
            replace<T>(R.id.fcv_main_container)
        }
    }
}