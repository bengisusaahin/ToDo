package com.bengisusahin.odev_10.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    // id is nullable because it will be auto-generated by the database
    val id: Int?,
    val username: String,
    val password: String
)
