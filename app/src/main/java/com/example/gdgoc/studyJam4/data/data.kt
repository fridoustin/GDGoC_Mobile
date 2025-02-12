package com.example.gdgoc.studyJam4.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String,
    var isComplete: Boolean =  false
)