package com.example.moviesdemo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.moviesdemo.BuildConfig
import com.example.moviesdemo.R
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.moviesdemo.databinding.ActivityMainBinding
import com.example.moviesdemo.fragments.HomeFragment
import com.example.moviesdemo.fragments.WatchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        setBindings()
        registerViewComponents()
        setCurrentFragment()
    }
    private fun hideStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
    private fun registerViewComponents(){
        val firstFragment = HomeFragment()
        val secondFragment = WatchFragment()
        val thirdFragment = HomeFragment()
        val forthFragment = HomeFragment()
        binding.bottomNavigationView.setOnItemSelectedListener  {
            when(it.itemId){
                R.id.mDashboard->setCurrentFragment(firstFragment)
                R.id.mWatch->setCurrentFragment(secondFragment)
                R.id.mMedia->setCurrentFragment(thirdFragment)
                R.id.mMore->setCurrentFragment(forthFragment)
            }
            true
        }
    }
    private fun setCurrentFragment(fragment: Fragment? = HomeFragment()) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameContainer, fragment ?: HomeFragment())
            commit()
        }
    private fun setBindings() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}