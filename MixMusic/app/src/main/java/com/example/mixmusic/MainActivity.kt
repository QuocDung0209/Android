package com.example.mixmusic

import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.media.AudioManager;
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.View.generateViewId
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_sound.*
import java.io.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    var sounds: ArrayList<Sound> = ArrayList()
    lateinit var soundAdapter:SoundAdapter
    lateinit private var mSoundPlayer: SoundPlayer
    private var click: IntArray = IntArray(30)
    private var streamID: IntArray = IntArray(30)
    val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 12
    var songNameSet: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setVolumeControlStream(AudioManager.STREAM_MUSIC)

        btnHome.setBackgroundResource(R.drawable.circle_button_click)

        addSounds()

        initPlayers()

        rvSounds.layoutManager = GridLayoutManager(this, 2)

        soundAdapter = SoundAdapter(this, sounds)

        rvSounds.adapter = soundAdapter

        soundAdapter.setListener(soundItemClickListener)

        if(!checkPermissionForReadExtertalStorage()) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
        }

        addSound.setOnClickListener{
            addSound.setBackgroundResource(R.drawable.btn_click)
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
            if(intent.resolveActivity(packageManager) != null){
                startActivityForResult(intent, 0)
            }
        }

        btnLibrary.setOnClickListener{
            startActivity(Intent(this, ActivityLibrary ::class.java))
        }

        btnSetting.setOnClickListener{
            startActivity(Intent(this,SettingActivity::class.java))
        }

        btnSave.setOnClickListener{
            btnSave.setBackgroundResource(R.drawable.btn_click)
            exitProcess(-1)
        }
    }

    private fun checkPermissionForReadExtertalStorage(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            return result == PackageManager.PERMISSION_GRANTED
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val result = data?.data
            if (result != null) {
                val selectedSound = data.data
                val filePath = arrayOf(MediaStore.Audio.Media.DATA)
                try {
                    val c = getContentResolver().query(selectedSound, filePath, null, null, null)
                    c.moveToFirst()
                    val columnIndex = c.getColumnIndex(filePath[0])
                    var soundPath = c.getString(columnIndex)
                    var colIndex = c.getColumnIndex(filePath[0])
                    var songName = c.getString(colIndex)
                    c.close()
                    if (soundPath == null) {
                        soundPath = selectedSound.path
                    }
                    mSoundPlayer.addSound(soundPath)
                    songNameSet = soundPath.split("/").last().split(".").first()
                    sounds.add(Sound(songNameSet, "new_item", "#a4b0c4", "newSound"))
                    soundAdapter.setData(sounds)
                    addSound.setBackgroundResource(R.drawable.radius)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


    private val soundItemClickListener = object : SoundItemClickListener{
        override fun onItemCLicked(position: Int) {
            click[position] += 1
            if (click[position] % 2 !=0){
                streamID[position] = mSoundPlayer.playSound(mSoundPlayer.getSounds()[position].id.toLong())
            }
            else{
                mSoundPlayer.stopSound(streamID[position])
            }
        }

        override fun onItemLongCLicked(position: Int) {
            val builder = android.app.AlertDialog.Builder(this@MainActivity)
            builder.setTitle("Confirmation")
                .setMessage("Remove ${sounds[position].name} ?")
                .setPositiveButton("OK") { _, _ ->
                    removeItem(position)
                }
                .setNegativeButton(
                    "Cancel"
                ) { dialog, _ -> dialog?.dismiss() }

            val myDialog = builder.create();
            myDialog.show()
        }

    }

    private fun initPlayers() {
        mSoundPlayer = SoundPlayer(this)
        mSoundPlayer.initSounds()
    }

    private fun removeItem(position: Int){
        sounds.removeAt(position)
        soundAdapter.setData(sounds)
    }

    private fun addSounds() {
        val json = assets.open("sounds.json").bufferedReader()
            .use { it.readText() }

        val collectionType = object : TypeToken<Collection<Sound>>() {}.type

        sounds = Gson().fromJson(json, collectionType)

    }

    override fun onDestroy() {
        super.onDestroy()
        mSoundPlayer.onDestroy()
    }
}



