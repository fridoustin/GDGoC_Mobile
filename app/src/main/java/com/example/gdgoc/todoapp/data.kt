package com.example.gdgoc.todoapp

data class Task(
    val id: Int,
    var title: String,
    var isComplete: Boolean =  false
)