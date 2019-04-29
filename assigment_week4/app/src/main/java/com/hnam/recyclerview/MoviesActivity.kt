package com.hnam.recyclerview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat
import java.util.Date

class MoviesActivity : AppCompatActivity() {

    var movies: ArrayList<Movie> = ArrayList()
    lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addMovies()

        rvMovies.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?

        movieAdapter = MovieAdapter(movies, this)

        rvMovies.adapter = movieAdapter

        movieAdapter.setListener(movieItemCLickListener)

    }

    private val movieItemCLickListener = object : MovieItemClickListener {
        override fun onItemCLicked(position: Int) {

            val intent = Intent(this@MoviesActivity,activity_detail::class.java)
            intent.putExtra(MOVIE_TITLE_KEY, movies[position].original_title)
            intent.putExtra(MOVIE_OVERVIEW_KEY, movies[position].overview)
            intent.putExtra(MOVIE_BACKDROP_KEY, movies[position].backdrop_path)
            intent.putExtra(MOVIE_DATE_KEY, DateFormat.getDateInstance().format(movies[position].release_date).toString())
            startActivity(intent)

        }
        override fun onItemLongCLicked(position: Int) {

            val builder = AlertDialog.Builder(this@MoviesActivity)
            builder.setTitle("Confirmation")
                .setMessage("Remove ${movies[position].original_title} ?")
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

    private fun removeItem(position: Int) {
        movies.removeAt(position)
        movieAdapter.setData(movies)
    }

    private fun addMovies(){
//        val json = assets.open("movies.json").bufferedReader()
//            .use { it.readText() }
        val json = FakeService.getMovieRaw()

        val collectionType = object : TypeToken<Collection<Movie>>() {}.type

        movies = Gson().fromJson(json, collectionType)
    }
}
