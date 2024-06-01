package com.bengisusahin.odev_10.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bengisusahin.odev_10.models.User

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User) : Long  // return id

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    fun getUser(username: String, password: String) : User?
}