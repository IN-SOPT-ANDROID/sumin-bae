package org.sopt.sample.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivityMainBinding
import org.sopt.sample.main.follower.FollowerFragment
import org.sopt.sample.main.gallery.GalleryFragment
import org.sopt.sample.main.search.SearchFragment

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        changeFragment<FollowerFragment>()
        binding.bnvMain.setOnItemSelectedListener { menu ->
            when(menu.itemId) {
                R.id.home_menu -> changeFragment<FollowerFragment>()
                R.id.gallery_menu -> changeFragment<GalleryFragment>()
                R.id.search_menu -> changeFragment<SearchFragment>()
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