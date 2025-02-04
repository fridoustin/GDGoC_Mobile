package com.example.gdgoc.todoapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskList(task: Task, viewModel: TaskViewModel){
    var isEditing by remember {mutableStateOf(false)}
    var editedTitle by remember { mutableStateOf(task.title) }

    if (isEditing) {
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = editedTitle,
                onValueChange = { editedTitle = it },
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
                    viewModel.updateTask(task.id, editedTitle)
                    isEditing = false
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6DADE8),
                contentColor = Color.Black
            ),
            shape = CircleShape,
            modifier = Modifier.size(48.dp),
            contentPadding = PaddingValues(0.dp)
            ){
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Save",
                    tint = Color.Black
                )
            }
        }
    } else {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = task.title,
                modifier = Modifier.weight(1f),
                fontSize = 18.sp
            )
            Row {
                IconButton(onClick = { isEditing = true }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = { viewModel.deleteTask(task.id) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}