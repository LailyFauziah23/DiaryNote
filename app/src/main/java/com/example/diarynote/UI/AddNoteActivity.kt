package com.example.diarynote.UI


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.diarynote.Data.Note
import com.example.diarynote.Data.NoteDatabase
import com.example.diarynote.databinding.ActivityAddNoteBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val journey = binding.journeytEditText.text.toString() // Ambil nilai journey dari input user

            if (title.isNotEmpty() && content.isNotEmpty() && journey.isNotEmpty()) {
                val note = Note(title = title, content = content, journey = journey)
                saveNoteToDatabase(note)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveNoteToDatabase(note: Note) {
        val database = NoteDatabase.getDatabase(this)
        CoroutineScope(Dispatchers.IO).launch {
            database.noteDao().insert(note)
            finish()
        }
    }
}