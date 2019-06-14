package com.hnam.recyclerview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.transition.Visibility
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_movie.*
import android.R.fraction
import android.R.attr.button
import android.support.constraint.ConstraintSet.VISIBLE


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
            val vote_star = data.getFloat(MOVIE_VOTE_KEY)
            val video_check = data.getBoolean(MOVIE_VIDEO_KEY)

            tvTitle.text = title
            tvDescription.text = overview
            tvDate.text = release
            if(video_check == true)
                btnPlay.show()
            ratingBar.rating = vote_star

            Glide.with(this)
                .load( "https://image.tmdb.org/t/p/w500/" + backdrop)
                .into(ivBackdrop)

        }
    }

}
