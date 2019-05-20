package com.example.room_assigment

interface TaskItemClickListener {
    fun onItemCLicked(position: Int)
    fun onItemLongCLicked(position: Int)
    fun onEditIconClicked(position: Int)
}
