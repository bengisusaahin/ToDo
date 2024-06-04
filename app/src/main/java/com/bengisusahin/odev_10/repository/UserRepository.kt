package com.bengisusahin.odev_10.repository

import com.bengisusahin.odev_10.dao.UserDao
import com.bengisusahin.odev_10.models.User

class UserRepository(private val userDao: UserDao) {
    suspend fun insertUser (user: User) : Long {
        return userDao.insertUser(user)
    }
    suspend fun getUser (username: String, password: String): User? {
        return userDao.getUser(username, password)
    }
}