package com.example.room_assigment

import android.app.Activity
import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_detail_task.*

class DetailTaskActivity : AppCompatActivity(){
    val spinnerData = ArrayList<Pair<String, Int>>()
    var user: ArrayList<User> = ArrayList()
    lateinit var userDao: UserDAO
    lateinit var db: UserDatabase
    var userId = "Unassigned"
    var userIntentId = -1
    var userAssignedName = "unassigned"
    var userPosition = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_task)
        db = UserDatabase.invoke(this)

        initUserDatabase()
        getUser()           //get user to display in spinner
        getData()           //get data to display
        setupSpinner()
        saveData()
        title = "Tasks"
    }

    private fun initUserDatabase(){
        val db = Room.databaseBuilder(applicationContext, UserDatabase::class.java,
            DATABASE_USER_NAME).allowMainThreadQueries().build()
        userDao = db.userDAO()
    }
    private fun getUser(){
        val user =userDao.getAll()
        this.user.addAll(user)

        spinnerData.add(Pair("Assign Task",-1))
        for (item in user){                 //include in users list
            spinnerData.add(Pair(item.name,item.id) as Pair<String, Int>)
        }
        spinnerData.add(Pair("Unassign",-1))
    }
    private fun getData(){
        val data =intent.extras
        if(data!=null){
            val description =data.getString(TASK_DESCRIPTION_KEY)
            val completed =data.getBoolean(TASK_COMPLETED_KEY)
            userId =data.getString(TASK_ASSIGNED_KEY)
            userPosition =data.getInt(TASK_POSITION_KEY)
            userIntentId =data.getInt(TASK_ID_KEY)
            tvTitle.text =description
            checkBox.isChecked =completed
            tvAssigned.text =userId
        }
    }
    private fun saveData() {
        btnSave.setOnClickListener {
            val intent = Intent()
            intent.putExtra(TASK_POSITION_KEY, userPosition)
            intent.putExtra(TASK_ID_KEY, userIntentId)
            intent.putExtra(TASK_DESCRIPTION_KEY, tvTitle.text)
            intent.putExtra(TASK_COMPLETED_KEY, checkBox.isChecked)
            intent.putExtra(TASK_ASSIGNED_KEY, userAssignedName)

            intent.putExtra(TASK_DELETE_KEY, 0)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
    private fun setupSpinner() {
        getUser()
        val spinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerData.map{
            if (it.second >= 0)
                "${it.first} (id = ${it.second})"
            else
                it.first
        })
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                tvAssigned.text = userId
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                userAssignedName = spinnerData[position].first
            }
        }
    }
}