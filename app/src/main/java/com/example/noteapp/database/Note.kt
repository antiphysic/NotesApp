package com.example.noteapp.database

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note (
    @NonNull
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val title:String,
    val desc:String
)
