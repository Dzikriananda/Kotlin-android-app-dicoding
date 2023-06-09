package com.example.aplikasigithubuser

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.aplikasigithubuser.Room.Users
import com.example.aplikasigithubuser.Room.UsersRoomDatabase
import com.example.aplikasigithubuser.Room.UsersDao


class FavoriteActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val db: UsersRoomDatabase = Room.databaseBuilder(application, UsersRoomDatabase::class.java, "user_database")
        .build()

    private val allUsers: LiveData<List<Users>> = db.usersDao().getAllUsers()

    fun getAllUsers(): LiveData<List<Users>> {
        return allUsers
    }



}