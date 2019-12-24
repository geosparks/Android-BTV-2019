package com.launchnet.btv.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.launchnet.btv.R
import com.launchnet.btv.db.isLoggedIn


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val thread = object : Thread() {
            override fun run() {
                sleep(1000)
                if (isLoggedIn()!!) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                } else {
                    startActivity(Intent(this@SplashActivity, RegisterActivity::class.java))
                }
                finish()
            }
        }
        thread.start()
    }
}