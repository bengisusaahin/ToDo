package com.bengisusahin.odev_10.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bengisusahin.odev_10.models.Note

@Dao
interface NoteDao {

    // suspend keyword is used to make the function asynchronous
    @Insert
    suspend fun insertNote(note: Note) : Long  // return id

    @Update
    suspend fun updateNote(note: Note) : Int

    @Delete
    suspend fun deleteNote (note: Note) : Int

    @Query("DELETE FROM note WHERE uid = :uid")
    suspend fun deleteAllNotesForUser(uid: Int) : Int

    @Query("SELECT * FROM note WHERE uid = :uid")
    suspend fun getAllNotesForUser(uid: Int) : List<Note>

    @Query("SELECT * FROM note WHERE nid = :nid")
    suspend fun getNoteById(nid: Int) : Note

    @Query("SELECT * FROM note WHERE uid = :uid AND (title LIKE :q OR content LIKE :q)")
    suspend fun searchNotes(uid: Int, q: String) : List<Note>

}