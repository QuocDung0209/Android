package com.example.mixmusic

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.activity_setting.view.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        btnTimer.setOnClickListener {
            startActivity(Intent(this,TimerActivity::class.java))
        }
        btnSettings.setBackgroundResource(R.drawable.circle_button_click)

        btnHomes.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnLibrarys.setOnClickListener{
            startActivity(Intent(this,ActivityLibrary::class.java))
        }
    }
}
