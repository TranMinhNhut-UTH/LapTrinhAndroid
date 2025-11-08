package com.example.myapplication.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.model.Task
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Composable
fun TaskCard(task: Task, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp) // Thêm padding ngang
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(task.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Hiển thị Status
                Text(
                    "Status: ${task.status}",
                    color = if (task.status == "In Progress") Color(0xFFFFA726) else Color.Gray,
                    fontSize = 14.sp
                )

                // Hiển thị và định dạng dueDate
                Text(
                    formatDateTime(task.dueDate), // Dùng hàm helper
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
    }
}

// Hàm helper để định dạng ngày giờ
private fun formatDateTime(dateTimeString: String): String {
    return try {
        // Định dạng đầu vào (ví dụ: "2024-03-26T09:00:00Z")
        val inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val dateTime = LocalDateTime.parse(dateTimeString, inputFormatter)

        // Định dạng đầu ra (ví dụ: "09:00 26/03")
        val outputFormatter = DateTimeFormatter.ofPattern("HH:mm dd/MM")
        dateTime.format(outputFormatter)
    } catch (e: DateTimeParseException) {
        dateTimeString.split("T").firstOrNull() ?: dateTimeString // Fallback
    }
}