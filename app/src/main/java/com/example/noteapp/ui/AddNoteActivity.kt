package com.example.noteapp.ui

import com.example.noteapp.R
import com.example.noteapp.database.Note
import com.example.noteapp.database.NotesDatabase


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_note.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AddNoteActivity : AppCompatActivity(), CoroutineScope {


    private var noteDB : NotesDatabase?= null
    private lateinit var mJob: Job

    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        mJob = Job()
        noteDB =
            NotesDatabase.getDatabase(this)

        addBtn.setOnClickListener {
            val strTitle: String = titleEt.text.toString()
            val strDesc: String = descriptionEt.text.toString()


            if (strTitle.isEmpty()) {
                titleEt.error = "Введите название"
                titleEt.requestFocus()
                return@setOnClickListener
            }
            if (strDesc.isEmpty()) {
                descriptionEt.error = "Введите описание"
                descriptionEt.requestFocus()
                return@setOnClickListener
            }
            launch {
                noteDB?.notesDao()?.insert(Note(title = strTitle, desc = strDesc))
                finish()
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        mJob.cancel()
    }
}
