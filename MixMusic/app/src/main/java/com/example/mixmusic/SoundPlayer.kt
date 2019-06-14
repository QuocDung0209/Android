package com.example.mixmusic

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool

class SoundPlayer(private val context: Context) {
    private val mAttributes: AudioAttributes = AudioAttributes.Builder()
        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
        .setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)
        .setUsage(AudioAttributes.USAGE_GAME)
        .build()

    private val mSoundPool: SoundPool =
        SoundPool.Builder()
            .setAudioAttributes(mAttributes)
            .setMaxStreams(10)
            .build()

    private val mSoundArray: ArrayList<Song> = ArrayList()

    fun getSounds(): ArrayList<Song> {
        return mSoundArray
    }

    fun initSounds() {
        val msounds = arrayOf("airplane", "chim_chich_choe", "ambulance", "dog", "stream", "chim_vanh_khuyen", "buffalo", "car", "cow", "elephant", "heavyrain", "motorbike", "sheep", "thunder", "train", "wind", "waterfall", "cat")

        for (msound in msounds) {
            val resourceId = context.resources.getIdentifier(msound, "raw", context.packageName)
            val soundId = mSoundPool.load(context, resourceId, 1)
            mSoundArray.add(Song(msound, soundId))
        }
    }

    fun addSound(path: String): Boolean {
        val soundId = mSoundPool.load(path, 1)
        return mSoundArray.add(Song(path, soundId))
    }

    fun playSound(id: Long): Int {
        val mgr = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC)
        val streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val volume = streamVolumeCurrent.toFloat() / streamVolumeMax
        return mSoundPool.play(id.toInt(), volume, volume, 0, -1, 1f)
    }

    fun stopSound(streamId: Int){
        mSoundPool.pause(streamId)
    }

    fun setVolume(streamId: Int, num: Float){
        mSoundPool.setVolume(streamId, num, num)
    }

    fun onDestroy(){
        mSoundPool.release()
    }
}