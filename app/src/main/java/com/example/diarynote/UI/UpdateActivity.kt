package com.example.diarynote.UI

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.diarynote.Data.Note
import com.example.diarynote.Data.NoteDatabase
import com.example.diarynote.databinding.ActivityUpdateBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private val noteDatabase by lazy { NoteDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val noteId = intent.getIntExtra("NOTE_ID", 0)
        val noteTitle = intent.getStringExtra("NOTE_TITLE")
        val noteContent = intent.getStringExtra("NOTE_CONTENT")
        val noteJourney = intent.getStringExtra("NOTE_JOURNEY") // Ambil journey dari intent

        // Isi field dengan data yang diterima
        binding.updateTitleEditText.setText(noteTitle)
        binding.updateContentEditText.setText(noteContent)
        binding.updateJourneyEditText.setText(noteJourney) // Pastikan ada EditText untuk journey

        binding.updateSaveButton.setOnClickListener {
            val updatedTitle = binding.updateTitleEditText.text.toString()
            val updatedContent = binding.updateContentEditText.text.toString()
            val updatedJourney = binding.updateJourneyEditText.text.toString() // Ambil journey yang diubah

            if (updatedTitle.isNotEmpty() && updatedContent.isNotEmpty() && updatedJourney.isNotEmpty()) {
                val updatedNote = Note(id = noteId, title = updatedTitle, content = updatedContent, journey = updatedJourney)
                updateNoteInDatabase(updatedNote)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateNoteInDatabase(note: Note) {
        lifecycleScope.launch(Dispatchers.IO) {
            noteDatabase.noteDao().update(note)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@UpdateActivity, "Note updated", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}