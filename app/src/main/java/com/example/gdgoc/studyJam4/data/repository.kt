package com.example.gdgoc.studyJam4.data

import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoDao: ToDoDao) {
    val allTodos: Flow<List<Todo>> = todoDao.getAllTodos()

    suspend fun insert(todo: Todo) {
        todoDao.insertTodo(todo)
    }

    suspend fun update(todo: Todo) {
        todoDao.updateTodo(todo)
    }

    suspend fun delete(todo: Todo) {
        todoDao.deleteTodo(todo)
    }
}