package com.example.mixmusic

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.media.MediaPlayer
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_sound.view.*


class SoundAdapter(val context: Context, var arrSounds: ArrayList<Sound>) :RecyclerView.Adapter<SoundViewHolder>(){

    lateinit var mListener: SoundItemClickListener
//    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SoundViewHolder {
        return SoundViewHolder(LayoutInflater.from(context).inflate(R.layout.item_sound, p0, false))
    }

    override fun getItemCount(): Int {
        return arrSounds.size
    }

    override fun onBindViewHolder(soundViewHolder: SoundViewHolder, position: Int) {
        soundViewHolder.textviewname.text = arrSounds[position].name
        soundViewHolder.textviewname.setBackgroundColor(Color.parseColor(arrSounds[position].hex))
        val img : Int = context.resources.getIdentifier("drawable/" + arrSounds[position].photo, null, context.packageName)
        soundViewHolder.imageviewsound.setImageResource(img)

        soundViewHolder.itemView.setOnClickListener{
            if(soundViewHolder.btnCheck.visibility == View.INVISIBLE){
                soundViewHolder.btnCheck.visibility = View.VISIBLE
            }
            else
                soundViewHolder.btnCheck.visibility = View.INVISIBLE
            mListener.onItemCLicked(position)
        }

        soundViewHolder.itemView.setOnLongClickListener{
            mListener.onItemLongCLicked(position)
            true
        }
    }

    fun setListener(listener: SoundItemClickListener) {
        this.mListener = listener
    }

    fun setData(items: java.util.ArrayList<Sound>){
        this.arrSounds = items
        notifyDataSetChanged()
    }

}

class SoundViewHolder(view: View):RecyclerView.ViewHolder(view){
    var textviewname = view.name
    var imageviewsound = view.imgPhoto
    var btnCheck = view.btnChecked
}
