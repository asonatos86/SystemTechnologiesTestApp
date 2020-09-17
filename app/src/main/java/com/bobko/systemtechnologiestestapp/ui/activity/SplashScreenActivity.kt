package com.bobko.systemtechnologiestestapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.bobko.systemtechnologiestestapp.R


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        startMainActivity()
    }

    private fun startMainActivity()
    {
        //правильно было бы сделать через тему, но так как требуется определенное врепя показа splash генерируется задержка
        Handler().postDelayed({

            startActivity(Intent(this,
                MainActivity::class.java))

            finish()
        }, 2000)
    }
}