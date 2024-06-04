package com.bengisusahin.odev_10.view

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bengisusahin.odev_10.R
import com.bengisusahin.odev_10.configs.AppDatabase
import com.bengisusahin.odev_10.databinding.ActivityDetailBinding
import com.bengisusahin.odev_10.models.Note
import com.bengisusahin.odev_10.repository.NoteRepository
import com.bengisusahin.odev_10.repository.UserRepository
import com.bengisusahin.odev_10.utils.DateUtils
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var noteRepository: NoteRepository
    private var note: Note? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
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

        DateUtils.showDatePickerDialog(this, binding.etDetailDate)

        val noteId = intent.getIntExtra("noteId", -1)
        lifecycleScope.launch {
            note = noteRepository.getNoteById(noteId)

            note?.let {
                binding.apply {
                    etDetailTitle.setText(it.title)
                    etDetailDate.setText(it.date)
                    etDetailContent.setText(it.content)
                }
            }
        }

        // delete button click listener to delete the note
        binding.deleteButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Yes") { _, _ ->
                    note?.let { note ->
                        lifecycleScope.launch {
                            noteRepository.deleteNote(note)
                            Toast.makeText(this@DetailActivity, "Note deleted", Toast.LENGTH_SHORT)
                                .show()
                            finish()
                        }
                    }
                }
                .setNegativeButton("No",){ _, _ ->
                    finish()
                }
                .setNeutralButton("Cancel", ){dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        // update button click listener to update the note
        binding.updateButton.setOnClickListener {
            val title = binding.etDetailTitle.text.toString()
            val date = binding.etDetailDate.text.toString()
            val content = binding.etDetailContent.text.toString()

            // check if there is any change in the note fields
            note?.let {
                if (title == it.title && date == it.date && content == it.content) {
                    Toast.makeText(this, "No changes made", Toast.LENGTH_SHORT).show()
                }else{
                    lifecycleScope.launch {
                        noteRepository.updateNote(Note(nid = noteId, uid = it.uid, title = title, date = date, content = content))
                        Toast.makeText(this@DetailActivity, "Note updated", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
    }
}