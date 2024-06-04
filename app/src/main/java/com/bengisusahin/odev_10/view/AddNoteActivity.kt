package com.bengisusahin.odev_10.view

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bengisusahin.odev_10.R
import com.bengisusahin.odev_10.adapter.NoteAdapter
import com.bengisusahin.odev_10.configs.AppDatabase
import com.bengisusahin.odev_10.databinding.ActivityAddNoteBinding
import com.bengisusahin.odev_10.models.Note
import com.bengisusahin.odev_10.repository.NoteRepository
import com.bengisusahin.odev_10.utils.DateUtils
import kotlinx.coroutines.launch

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var noteRepository: NoteRepository
    lateinit var allNotes:MutableList<Note>
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val database = AppDatabase(this)
        val noteDao = database.noteDao()
        noteRepository = NoteRepository(noteDao)

        // get the userId from the intent that started this activity
        userId = intent.getIntExtra("userId", -1)

        DateUtils.showDatePickerDialog(this, binding.dateEditText)

        // save the note to the database when the save button is clicked
        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val date = binding.dateEditText.text.toString()

            if (title.isEmpty() || content.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                // stops the execution of the code if any of the fields are empty
                return@setOnClickListener
            }else {
                lifecycleScope.launch {
                    val note = Note(nid = 0, uid = userId, title = title, content = content, date = date)
                    val noteId = noteRepository.insertNote(note)
                    // if noteId is greater than -1, it means note is added successfully, addNote method returns the id of the note
                    if (noteId > -1) {
                        binding.titleEditText.text.clear()
                        binding.contentEditText.text.clear()

                        Toast.makeText(this@AddNoteActivity, "Note added successfully", Toast.LENGTH_SHORT).show()

                        // after adding the note, update the list
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                }

            }
        }
    }
}