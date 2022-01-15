package com.dicoding.mysecondsubmission


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val pref = SettingPreferences.getInstance(dataStore)
        val preferencesViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            PreferencesViewModel::class.java
        )
        preferencesViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })

        Handler().postDelayed ({
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
            finish()
        }, 3000)
    }
}