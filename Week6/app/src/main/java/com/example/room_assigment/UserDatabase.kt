package com.example.room_assigment

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = arrayOf(User::class),version = 3)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDAO(): UserDAO
    companion object {
        @Volatile
        private var instance: UserDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            UserDatabase::class.java, DATABASE_USER_NAME).allowMainThreadQueries().build()
    }
}
