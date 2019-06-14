package com.example.room_assigment

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

//@Parcelize
@Entity
data class Task (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var description: String,
    var completed: Boolean,
    var userId: String)
{
    constructor() : this(null, "", false, "")
}
