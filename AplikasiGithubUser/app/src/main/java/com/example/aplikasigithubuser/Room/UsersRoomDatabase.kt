package com.example.aplikasigithubuser.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Users::class], version = 1)
abstract class UsersRoomDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao

    companion object {
        @Volatile
        private var INSTANCE: UsersRoomDatabase? = null

        fun getDatabase(context: Context): UsersRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UsersRoomDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}