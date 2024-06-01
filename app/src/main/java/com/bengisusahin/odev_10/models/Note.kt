package com.bengisusahin.odev_10.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "note",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["uid"],
            onDelete = ForeignKey.CASCADE // When a user is deleted, all notes of that user will be deleted
        )
    ]
)
data class Note(
    @PrimaryKey(autoGenerate = true)
    val nid: Int,
    val uid: Int,
    val title: String,
    val content: String,
    val date: String
)
