package com.example.gdgoc

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Data model untuk kontak
data class Contact(val id: Int, val name: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactApp() {
    // State untuk input teks, daftar kontak, dan filter yang sedang aktif
    val (contactInput, setContactInput) = remember { mutableStateOf("") }
    val (contacts, setContacts) = remember { mutableStateOf(listOf<Contact>()) }
    val (selectedFilter, setSelectedFilter) = remember { mutableStateOf("All") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Contact App",
                        color = Color.Black,
                        fontSize = 22.sp,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF6DADE8))
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Input untuk menambahkan kontak baru
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = contactInput,
                    onValueChange = setContactInput,
                    modifier = Modifier.weight(1f),
                    label = { Text("Nama Kontak") }
                )
                Button(
                    onClick = {
                        if (contactInput.isNotBlank()) {
                            val newContact = Contact(id = contacts.size + 1, name = contactInput)
                            setContacts(contacts + newContact)
                            setContactInput("")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6DADE8),
                        contentColor = Color.Black
                    )
                ) {
                    Text("Add")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val filters = listOf("All") + ('A'..'Z').map { it.toString() }

            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filters) { filter ->
                    FilterChip(
                        label = filter,
                        isSelected = filter == selectedFilter,
                        onClick = { setSelectedFilter(filter) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Menampilkan daftar kontak yang telah difilter menggunakan LazyColumn
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Filter daftar kontak sesuai pilihan filter
                val filteredContacts = if (selectedFilter == "All") {
                    contacts
                } else {
                    contacts.filter { it.name.startsWith(selectedFilter, ignoreCase = true) }
                }
                items(filteredContacts, key = { it.id }) { contact ->
                    ContactItem(contact = contact)
                }
            }
        }
    }
}

@Composable
fun FilterChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    // Komponen sederhana yang menampilkan label filter dengan tampilan yang berbeda saat dipilih
    Surface(
        color = if (isSelected) Color(0xFF6DADE8) else MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .clickable { onClick() }
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ContactItem(contact: Contact) {
    // Komponen untuk menampilkan satu item kontak
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 4.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ){
            Icon(
                modifier = Modifier.padding(start = 16.dp),
                imageVector = Icons.Default.Person,
                contentDescription = "person"
            )
            Text(
                text = contact.name,
                modifier = Modifier.padding(16.dp),
                fontSize = 16.sp
            )
        }

    }
}
