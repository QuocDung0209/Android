package com.hnam.recyclerview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_movie.*


class activity_detail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        getAndPutData()
    }

    private fun getAndPutData() {
        val data = intent.extras

        if (data != null) {
            val title = data.getString(MOVIE_TITLE_KEY)
            val overview = data.getString(MOVIE_OVERVIEW_KEY)
            val backdrop = data.getString(MOVIE_BACKDROP_KEY)
            val release = data.getString(MOVIE_DATE_KEY)

            tvTitle.text = title
            tvDescription.text = overview
            tvDate.text = release
            Glide.with(this)
                .load( "https://image.tmdb.org/t/p/w500/" + backdrop)
                .into(ivBackdrop)

        }
    }
}
