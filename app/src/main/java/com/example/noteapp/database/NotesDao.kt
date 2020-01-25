package com.example.noteapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NotesDao {

    @Query("SELECT * FROM note ORDER BY id DESC")
    suspend fun getAllNotes(): List<Note>

    @Insert
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

}