package com.example.noteapp.ui

import android.content.Intent
import com.example.noteapp.R
import com.example.noteapp.adapter.NotesListAdapter
import com.example.noteapp.database.Note
import com.example.noteapp.database.NotesDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() , CoroutineScope {
    private var noteDB: NotesDatabase? = null
    private var adapter: NotesListAdapter? = null
    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mJob = Job()

        noteDB = NotesDatabase.getDatabase(this)
        adapter = NotesListAdapter(this, noteDB!!)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        getAllNotes()
    }

    override fun onDestroy() {
        super.onDestroy()
        mJob.cancel()
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_addbtn -> {
                startActivity(Intent(this, AddNoteActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun getAllNotes() {
        launch {
            val listNotes: List<Note>? = noteDB?.notesDao()?.getAllNotes()
            if (listNotes != null) {
                adapter?.setNotes(listNotes)
            }
        }
    }
}

