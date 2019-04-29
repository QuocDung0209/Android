package com.hnam.recyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.view.*
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(var items: ArrayList<Movie>, val  context: Context): RecyclerView.Adapter<MovieViewHolder>() {

    lateinit var mListener: MovieItemClickListener

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.item_movie, p0,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: MovieViewHolder, p1: Int) {
        p0.tvOTitle.text = "${items[p1].original_title}"
        p0.tvOverview.text = items[p1].overview
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w500/" + items[p1].poster_path)
            .into(p0.ivPoster)

        p0.itemView.setOnClickListener{
            mListener.onItemCLicked(p1)
        }

        p0.itemView.setOnLongClickListener{
            mListener.onItemLongCLicked(p1)
            true
        }
    }

    fun setListener(listener: MovieItemClickListener) {
        this.mListener = listener
    }

    fun setData(items: ArrayList<Movie>){
        this.items = items
        notifyDataSetChanged()
    }
}

class MovieViewHolder(view: View): RecyclerView.ViewHolder(view){
    var tvOTitle = view.tvOTitle
    var tvOverview = view.tvOverview
    var ivPoster = view.ivPoster
}