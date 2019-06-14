package com.example.room_assigment

import android.app.Activity
import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.facebook.stetho.Stetho
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {
    var tasks: ArrayList<Task> = ArrayList()
    lateinit var taskAdapter: TaskAdapter
    lateinit var taskDao: TaskDAO
    lateinit var db: TaskDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Stetho.initializeWithDefaults(this)
        initTaskDatabase()
        setupRecycleView()
        getTasks()
        btnUsersList.setOnClickListener {

            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }

       // getTasks()
       // db = TaskDatabase.invoke(this)
       // setupRecycleView()
        title = "Room"

        btnAdd.setOnClickListener {

            var btnAdd =Task()
            btnAdd.description =editTitle_User.text.toString()
            btnAdd.completed =false
            btnAdd.userId ="Unassigned"             //default value
            //val taskDao =db.taskDAO()
            val id =taskDao.insert(btnAdd)
            btnAdd.id =id.toInt()
            taskAdapter.appendData(btnAdd)
        }
    }
    private fun initTaskDatabase() {
        val db = Room.databaseBuilder(applicationContext,
            TaskDatabase::class.java, DATABASE_TASK_NAME).allowMainThreadQueries().build()
        taskDao = db.taskDAO()
    }

    private fun getTasks() {
        val tasks = taskDao.getAll()
        Log.i("Task: ", tasks.toString())
        this.tasks.addAll(tasks)

        taskAdapter.notifyDataSetChanged()
    }
    private fun setupRecycleView(){
        rvTitle.layoutManager=LinearLayoutManager(this)
        taskAdapter = TaskAdapter(tasks,this)
        rvTitle.adapter = taskAdapter
        taskAdapter.setListener(taskItemClickListener)
    }
    private val taskItemClickListener = object : TaskItemClickListener {
        override fun onItemCLicked(position: Int) {
            val intent = Intent(this@MainActivity, DetailTaskActivity::class.java)
            intent.putExtra(TASK_ID_KEY, tasks[position].id)
            intent.putExtra(TASK_DESCRIPTION_KEY, tasks[position].description)
            intent.putExtra(TASK_ASSIGNED_KEY, tasks[position].userId)
            intent.putExtra(TASK_COMPLETED_KEY, tasks[position].completed)
            intent.putExtra(TASK_POSITION_KEY, position)
            startActivityForResult(intent, CODE_ADD_NEW_STUDENT)
        }
        override fun onItemLongCLicked(position: Int) {

        }
        override fun onEditIconClicked(position: Int) {

        }
    }
    private fun update(position: Int, task: Task) {
        taskDao.update(task)

        tasks.set(position, task)

        taskAdapter.notifyItemChanged(position)
        Timer(false).schedule(500) {
            runOnUiThread {
                taskAdapter.setData(tasks)
                taskAdapter.notifyDataSetChanged()
            }
        }
    }
    private fun delete(position: Int) {
        taskDao.delete(tasks[position])

        tasks.removeAt(position)

        taskAdapter.notifyItemRemoved(position)
        Timer(false).schedule(500) {
            runOnUiThread {
                taskAdapter.setData(tasks)
                taskAdapter.notifyDataSetChanged()
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODE_ADD_NEW_STUDENT && resultCode == Activity.RESULT_OK) {
            val test = data?.extras?.getInt(TASK_DELETE_KEY)
            val position = data?.extras?.getInt(TASK_POSITION_KEY)

            if (test == 0) {
                val id = data?.extras?.getInt(TASK_ID_KEY)
                val description = data?.extras?.getString(TASK_DESCRIPTION_KEY)
                val completed = data?.extras?.getBoolean(TASK_COMPLETED_KEY)
                val assigned = data?.extras?.getString(TASK_ASSIGNED_KEY)
                if (position != null && description != null && completed != null && assigned != null) {
                    update(position, Task(id, description, completed, assigned))
                    Log.e(
                        "Main_onActivityResult: ",
                        "user:" + assigned + " - id:" + id.toString() + " - com:" + completed.toString()
                    )
                }
            } else {
                if (position != null)
                    delete(position)
            }
        }
    }

}
