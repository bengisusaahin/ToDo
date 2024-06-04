package com.bengisusahin.odev_10.configs

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bengisusahin.odev_10.dao.NoteDao
import com.bengisusahin.odev_10.dao.UserDao
import com.bengisusahin.odev_10.models.Note
import com.bengisusahin.odev_10.models.User

// This class is used to create a database with the given entities and access the dao classes
@Database(entities = [User::class, Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun noteDao() : NoteDao

    // Singleton pattern is used to ensure that only one instance of the database is created
    companion object {
        // Volatile means that writes to this field are immediately made visible to other threads
        @Volatile private var instance : AppDatabase? = null

        private val lock = Any()

        // synchronized block is used to ensure that only one thread can execute the block of code at a time
        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: buildDatabase(context).also { instance = it }
        }

        // This function is used to create the database
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            // context parameter is used to if the app is turn right side up or upside down
            context.applicationContext,
            AppDatabase::class.java,
            "todo.db"
        ).build()
    }


}