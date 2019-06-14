package com.example.room_assigment

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null,
    var name:String)
{
    constructor(): this(null, " ")
}
