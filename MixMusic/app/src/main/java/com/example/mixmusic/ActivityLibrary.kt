package com.example.mixmusic

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.example.mixmusic.AudioDetailActivity.Companion.ALBUM_GSON
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_library.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class ActivityLibrary : AppCompatActivity() {

    lateinit var adapter: AudioAdapter
    lateinit private var audioList: MutableList<Audio>
    val REQUEST_ID_MULTIPLE_PERMISSIONS = 1

    fun loadAudioList(): MutableList<Audio> {
        val audioList = ArrayList<Audio>()

        if (checkAndRequestPermissions()) {
            val contentResolver = contentResolver

            val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"
            val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"
            val cursor = contentResolver.query(uri, null, selection, null, sortOrder)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    audioList.add(Audio(data, title, album, artist))
                }
            }
            cursor?.close()
        }
        return audioList
    }

    private fun checkAndRequestPermissions(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissionStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            val listPermissionsNeeded = ArrayList<String>()

            if (permissionStorage != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), REQUEST_ID_MULTIPLE_PERMISSIONS)
                return false
            } else {
                return true
            }
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)

        btnLibraryl.setBackgroundResource(R.drawable.circle_button_click)

        btnHomel.setOnClickListener{
            startActivity(Intent(this, MainActivity ::class.java))
        }

        btnSettingl.setOnClickListener{
            startActivity(Intent(this,SettingActivity::class.java))
        }

        audioList = loadAudioList()

        adapter = AudioAdapter(audioList) { it: Audio ->
            startActivity(Intent(this, AudioDetailActivity::class.java).putExtra(ALBUM_GSON, Gson().toJson(it)))
        }
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()

        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, mLayoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

        recyclerView.adapter = adapter

        adapter.notifyDataSetChanged()
    }
}
