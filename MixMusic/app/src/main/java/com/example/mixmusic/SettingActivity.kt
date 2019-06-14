package com.example.mixmusic

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        btnSettings.setBackgroundResource(R.drawable.circle_button_click)

        btnHomes.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnLibrarys.setOnClickListener{
            startActivity(Intent(this,ActivityLibrary::class.java))
        }
    }
}
