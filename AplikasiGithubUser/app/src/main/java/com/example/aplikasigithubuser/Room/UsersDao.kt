package com.example.aplikasigithubuser.Room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(users: Users)
    @Update
    fun update(users: Users)
    @Query("DELETE FROM users WHERE username = :username")
    fun delete(username: String)
    @Query("SELECT * from Users ORDER BY id ASC")
    fun getAllUsers(): LiveData<List<Users>>
    @Query("SELECT username FROM Users WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): String?
}