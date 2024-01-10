package com.example.news.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.news.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val anim = binding.animationView
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            anim.playAnimation()
            val intent= Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)

        }, 2000)




    }
}