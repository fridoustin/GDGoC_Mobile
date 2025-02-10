package com.example.gdgoc.todoapp

import android.app.Application
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoApp(){
    val context = LocalContext.current
    // Meng-cast context menjadi Application (pastikan context ini adalah application context)
    val application = context.applicationContext as Application
    // Menggunakan factory untuk mendapatkan TodoViewModel
    val todoViewModel: TodoViewModel = viewModel(factory = TodoViewModelFactory(application))

    val todos by todoViewModel.allTodos.observeAsState(emptyList())
    var text by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    BackHandler {
        focusManager.clearFocus()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title =  {
                    Text(
                        text = "To Do App",
                        color = Color.Black,
                        fontSize = 22.sp,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF6DADE8))
            )
        }
    ) {
        innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Row(modifier = Modifier.
                    padding(horizontal = 24.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically)
            {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = {Text("Add Task")},
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(fontSize = 18.sp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF6DADE8),
                        unfocusedBorderColor = Color.Black,
                        cursorColor = Color(0xFF6DADE8)
                    ),
                )
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {
                        if(text.isNotBlank()){
                            todoViewModel.insert(Todo(title = text))
                            text = ""
                        }
                        focusManager.clearFocus()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6DADE8),
                        contentColor = Color.Black
                    ),
                    shape = CircleShape,
                    modifier = Modifier.size(48.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "+",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
                items(todos) { todo ->
                    TaskList(
                        todo = todo,
                        onUpdate = {newTodo ->
                            val updatedTodo = todo.copy(title = newTodo)
                            todoViewModel.update(updatedTodo)},
                        onDelete = {todoViewModel.delete(todo)})
                    HorizontalDivider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                }
            }
        }
    }


}