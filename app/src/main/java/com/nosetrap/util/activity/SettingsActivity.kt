package com.nosetrap.util.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nosetrap.util.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }
}
