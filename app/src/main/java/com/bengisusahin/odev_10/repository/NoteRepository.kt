package com.bengisusahin.odev_10.repository

import com.bengisusahin.odev_10.dao.NoteDao
import com.bengisusahin.odev_10.models.Note

// This class is used to Dao class to access the database and perform operations on the Note table
class NoteRepository(private val noteDao: NoteDao) {
    // This function is used to insert a note into the database
    suspend fun insertNote (note: Note) : Long {
        return noteDao.insertNote(note)
    }
    suspend fun updateNote (note: Note) : Int {
        return noteDao.updateNote(note)
    }
    suspend fun deleteNote (note: Note) : Int {
        return noteDao.deleteNote(note)
    }
    suspend fun deleteAllNotesForUser(uid: Int) : Int {
        return noteDao.deleteAllNotesForUser(uid)
    }
    suspend fun getAllNotesForUser(uid: Int) : List<Note> {
        return noteDao.getAllNotesForUser(uid)
    }
    suspend fun getNoteById(nid: Int) : Note {
        return noteDao.getNoteById(nid)
    }
    suspend fun searchNotes(uid: Int, q: String) : List<Note> {
        return noteDao.searchNotes(uid, q)
    }

}