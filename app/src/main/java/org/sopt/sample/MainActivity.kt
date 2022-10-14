package org.sopt.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import org.sopt.sample.databinding.ActivityMainBinding
import org.sopt.sample.gallery.GalleryFragment
import org.sopt.sample.home.HomeFragment
import org.sopt.sample.search.SearchFragment

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bnvMain.setOnItemSelectedListener {
            changeFragment(it.itemId)
            true
        }
        changeFragment(R.id.home_menu)
    }

    private fun changeFragment(menuId: Int) {
        val targetFragment = getFragment(menuId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_main_container, targetFragment)
            .commit()
    }

    private fun getFragment(menuId: Int): Fragment {
        return when (menuId) {
            R.id.home_menu -> HomeFragment.newInstance("home")
            R.id.gallery_menu -> GalleryFragment.newInstance("gallery")
            R.id.search_menu -> SearchFragment.newInstance("search")
            else -> throw IllegalArgumentException("menu item not found")
        }
    }
}