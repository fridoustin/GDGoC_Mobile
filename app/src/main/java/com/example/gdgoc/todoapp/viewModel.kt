package com.example.gdgoc.todoapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {
    private var _tasks = mutableStateListOf<Task>()
    val tasks: List<Task> get() = _tasks

    private var taskIdCounter = 0

    fun addTask(title: String){
        if(title.isNotEmpty()){
            _tasks.add(
                Task(
                id = taskIdCounter++,
                title = title
            )
            )
        }
    }

    fun updateTask(id: Int, newTitle: String){
        _tasks.find {it.id == id}?.title = newTitle
    }

    fun deleteTask(id: Int){
        _tasks.removeIf {it.id == id}
    }
}