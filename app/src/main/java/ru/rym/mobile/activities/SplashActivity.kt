package ru.rym.mobile.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import ru.rym.mobile.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setTheme(R.style.Theme_Mirmobile)
        super.onCreate(savedInstanceState)
        startActivity(Intent(this@SplashActivity, ContentActivity::class.java))
        overridePendingTransition(0, 0)
     }
}