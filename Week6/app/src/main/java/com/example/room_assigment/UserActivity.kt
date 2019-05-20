package com.example.room_assigment

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_user.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class UserActivity:AppCompatActivity() {
    var user: ArrayList<User> = ArrayList()
    lateinit var userDao: UserDAO
    lateinit var userAdapter: UserAdapter

    lateinit var db:UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        initUserDatabase()
        setUpRecycleView()
        getUser()
        db = UserDatabase.invoke(this)

        //setUpRecycleView()
       // getUser()
        title = "Users"

        btnAdd.setOnClickListener {
            val userBtn = User()
            userBtn.name = editTitle_User.text.toString()
            val userDao = db.userDAO()
            val id = userDao.insert(userBtn)
            userBtn.id = id.toInt()
            userAdapter.appendData(userBtn)
        }
    }
    private fun initUserDatabase(){
        val db = Room.databaseBuilder(applicationContext,UserDatabase::class.java,
            DATABASE_USER_NAME).allowMainThreadQueries().build()
        userDao =db.userDAO()
    }
    private fun getUser(){
        val user =userDao.getAll()
        this.user.addAll(user)
        userAdapter.notifyDataSetChanged()
    }
    private fun setUpRecycleView(){
        rvUser.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter(user,this)
        rvUser.adapter =userAdapter
        userAdapter.setListener(userItemClickListener)
    }
    private val userItemClickListener = object : UserItemClickListener {
        override fun onItemLongClicked(position: Int) {
        }
        override fun onItemDeleteClicked(position: Int) {
            delete(position)
        }
    }

    private fun delete(position: Int) {
        userDao.delete(user[position])

        user.removeAt(position)

        userAdapter.notifyItemRemoved(position)
        Timer(false).schedule(500) {
            runOnUiThread {
                userAdapter.setData(user)
                userAdapter.notifyDataSetChanged()
            }
        }
    }
}