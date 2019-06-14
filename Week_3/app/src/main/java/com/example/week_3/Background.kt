package com.example.week_3

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_background.*
import kotlinx.android.synthetic.main.activity_main.*

class Background : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_background)
        val data1:Bundle? = intent.extras
        if(data1!=null){
            imageView2.setImageLevel(data1.getInt("level"))
        }

        var level_img:Int = 1
        imgGreen.setOnClickListener{
            imageView2.setImageResource(R.drawable.green)
            level_img = 1
        }
        imgR.setOnClickListener{
            imageView2.setImageResource(R.drawable.r)
            level_img = 2
        }
        imgMonero.setOnClickListener{
            imageView2.setImageResource(R.drawable.monero)
            level_img = 3
        }
        imgFive.setOnClickListener{
            imageView2.setImageResource(R.drawable.five)
            level_img = 4
        }
        imgTwo.setOnClickListener{
            imageView2.setImageResource(R.drawable.two)
            level_img = 5
        }
        imgOne.setOnClickListener{
            imageView2.setImageResource(R.drawable.one)
            level_img = 6
        }
        btnSaveBG.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("logoName", level_img)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
