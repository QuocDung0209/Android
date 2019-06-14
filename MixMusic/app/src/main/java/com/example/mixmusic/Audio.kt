package com.example.mixmusic

import android.support.annotation.DrawableRes

class Audio(val data: String = "",
            val title: String = "",
            val album: String = "",
            val artist: String = "",
            @DrawableRes val coverID: Int = UNDEFINED_AUDIO_IMAGE) {

    override fun toString(): String {
        return "Audio(title='$title', artist='$artist)"
    }

    companion object {
        @DrawableRes val UNDEFINED_AUDIO_IMAGE: Int = R.drawable.undefined_audio_image
    }
}
