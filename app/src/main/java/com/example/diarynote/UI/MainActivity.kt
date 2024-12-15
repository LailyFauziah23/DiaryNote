package com.example.diarynote.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diarynote.Data.Note
import com.example.diarynote.Data.NoteDatabase
import com.example.diarynote.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var notesAdapter: NotesAdapter
    private val noteDatabase by lazy { NoteDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeNotes()

        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        notesAdapter = NotesAdapter(
            onEditClick = { note -> navigateToUpdateNoteActivity(note) },
            onDeleteClick = { note -> deleteNoteFromDatabase(note) }
        )
        binding.notesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = notesAdapter
        }
    }

    private fun observeNotes() {
        noteDatabase.noteDao().getAllNotes().observe(this, Observer { notes ->
            notesAdapter.updateNotes(notes) // Gunakan fungsi updateNotes
        })
    }

    private fun deleteNoteFromDatabase(note: Note) {
        lifecycleScope.launch(Dispatchers.IO) {
            noteDatabase.noteDao().delete(note)
            runOnUiThread {
                Toast.makeText(this@MainActivity, "Note deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToUpdateNoteActivity(note: Note) {
        val intent = Intent(this, UpdateActivity::class.java).apply {
            putExtra("NOTE_ID", note.id)
            putExtra("NOTE_TITLE", note.title)
            putExtra("NOTE_CONTENT", note.content)
            putExtra("NOTE_JOURNEY", note.journey) // Kirimkan journey
        }
        startActivity(intent)
    }
}