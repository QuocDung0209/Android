package com.example.week_3

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_title.*

class Title : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title)
        val data:Bundle? = intent.extras
        if(data!=null){
            editText.setText(data.getString("content"))
            val color_text = data.getString("color_text")
            when(color_text){
                "-16776961" -> imgColor.setImageResource(R.drawable.blue)
                "-16711936" -> imgColor.setImageResource(R.drawable.lime)
                "-16711681" -> imgColor.setImageResource(R.drawable.cyan)
                "-65281" -> imgColor.setImageResource(R.drawable.magenta)
                "-8388480" -> imgColor.setImageResource(R.drawable.purple)
                "-65536" -> imgColor.setImageResource(R.drawable.red)
            }
        }

        var text_color:Int = 0xFF0000
        imgBlue.setOnClickListener{
            imgColor.setImageResource(R.drawable.blue)
            text_color = 0
        }
        imgLime.setOnClickListener{
            imgColor.setImageResource(R.drawable.lime)
            text_color = 1
        }
        imgCyan.setOnClickListener{
            imgColor.setImageResource(R.drawable.cyan)
            text_color = 2
        }
        imgMagenta.setOnClickListener{
            imgColor.setImageResource(R.drawable.magenta)
            text_color = 3
        }
        imgPurple.setOnClickListener{
            imgColor.setImageResource(R.drawable.purple)
            text_color = 4
        }
        imgRed.setOnClickListener{
            imgColor.setImageResource(R.drawable.red)
            text_color = 5
        }
        btnSaveTitle.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            val text = editText.text.toString()
            intent.putExtra("color_text", text_color)
            intent.putExtra("name", text)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
