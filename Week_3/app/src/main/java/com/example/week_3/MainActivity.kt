package com.example.week_3

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.service.quicksettings.Tile
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnBackgroud.setOnClickListener{
            gotoBackground()
        }
        btnTitle.setOnClickListener{
              gotoSettingTile()
        }
    }

    private fun gotoBackground(){
        val intent = Intent(this, Background::class.java)
        intent.putExtra("level",imageView.drawable.level )
        startActivityForResult(intent, REQUEST_SETTING_CODE)
    }
    private fun gotoSettingTile(){
        val intent = Intent(this, Title::class.java)
        intent.putExtra("content", name.text)
        intent.putExtra("color_text", name.currentTextColor.toString())
        startActivityForResult(intent, 2997)
    }
    companion object {
        const val REQUEST_SETTING_CODE = 1099;
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_SETTING_CODE && resultCode == Activity.RESULT_OK){
            val logo = data?.getIntExtra("logoName", 0)
            when(logo){
                1 -> imageView.setImageLevel(1)
                2 -> imageView.setImageLevel(2)
                3 -> imageView.setImageLevel(3)
                4 -> imageView.setImageLevel(4)
                5 -> imageView.setImageLevel(5)
                6 -> imageView.setImageLevel(6)
            }
        }
        if(requestCode == 2997 && resultCode == Activity.RESULT_OK) {
            name.setText(data?.getStringExtra("name"))
            val textColor = data?.getIntExtra("color_text", 0)
            when(textColor){
                0 -> name.setTextColor(Color.rgb(0,0,255))
                1 -> name.setTextColor(Color.rgb(0,255,0))
                2 -> name.setTextColor(Color.rgb(0,255,255))
                3 -> name.setTextColor(Color.rgb(255,0,255))
                4 -> name.setTextColor(Color.rgb(128,0,128))
                5 -> name.setTextColor(Color.rgb(255,0,0))
            }
        }
    }

}
