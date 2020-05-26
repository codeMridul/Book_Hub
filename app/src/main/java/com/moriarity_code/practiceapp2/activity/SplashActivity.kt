package com.moriarity_code.practiceapp2.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.moriarity_code.practiceapp2.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val startActivity = Intent(
                this@SplashActivity,
                MainActivity::class.java
            )
            startActivity(startActivity)
        }, 1000)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
