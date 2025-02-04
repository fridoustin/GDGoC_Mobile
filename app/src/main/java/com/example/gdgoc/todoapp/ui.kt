package com.example.gdgoc.todoapp

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoApp(viewModel: TaskViewModel){
    var newTask by remember { mutableStateOf("") }
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
                    value = newTask,
                    onValueChange = { newTask = it },
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
                        viewModel.addTask(newTask)
                        newTask = ""
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
                items(viewModel.tasks) { task ->
                    TaskList(task = task, viewModel = viewModel)
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