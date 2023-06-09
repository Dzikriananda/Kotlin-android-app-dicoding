package com.example.aplikasigithubuser.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Users(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "username")
    var username: String? = null,
    @ColumnInfo(name = "url_avatar")
    var url_avatar: String? = null
)