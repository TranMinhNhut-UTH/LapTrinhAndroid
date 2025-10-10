package com.example.bt3_checkage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AgeClassificationScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgeClassificationScreen() {
    var name by remember { mutableStateOf<String?>("") }
    var age by remember { mutableStateOf<String?>("") }
    var resultMessage by remember { mutableStateOf("") }
    var showResult by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "THỰC HÀNH 01",
                        fontWeight = FontWeight.Medium
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFF5F5F5),
                    titleContentColor = Color.Black
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Card chứa form
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE0E0E0)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Họ và tên input
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Họ và tên",
                            fontSize = 16.sp,
                            modifier = Modifier.width(100.dp)
                        )
                        OutlinedTextField(
                            value = name ?: "",
                            onValueChange = {
                                name = if (it.isEmpty()) null else it
                                showResult = false
                            },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF2196F3),
                                unfocusedBorderColor = Color.Gray,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            ),
                            textStyle = LocalTextStyle.current
                        )
                    }

                    // Tuổi input
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Tuổi",
                            fontSize = 16.sp,
                            modifier = Modifier.width(100.dp)
                        )
                        OutlinedTextField(
                            value = age ?: "",
                            onValueChange = {
                                age = if (it.isEmpty()) null else it
                                showResult = false
                            },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF2196F3),
                                unfocusedBorderColor = Color.Gray,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            )
                        )
                    }
                }
            }

            // Result message
            if (showResult && resultMessage.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE3F2FD)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = resultMessage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        color = Color(0xFF1565C0),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Kiểm tra button
            Button(
                onClick = {
                    val ageValue = age?.toIntOrNull()

                    when {
                        name == null || name!!.isEmpty() -> {
                            resultMessage = "Vui lòng nhập họ và tên"
                            showResult = true
                        }
                        age == null || age!!.isEmpty() -> {
                            resultMessage = "Vui lòng nhập tuổi"
                            showResult = true
                        }
                        ageValue == null -> {
                            resultMessage = "Tuổi phải là số"
                            showResult = true
                        }
                        ageValue > 65 -> {
                            resultMessage = "$name là Người già"
                            showResult = true
                        }
                        ageValue in 6..65 -> {
                            resultMessage = "$name là Người lớn"
                            showResult = true
                        }
                        ageValue in 2..5 -> {
                            resultMessage = "$name là Trẻ em"
                            showResult = true
                        }
                        ageValue > 2 -> {
                            resultMessage = "$name là Em bé"
                            showResult = true
                        }
                        else -> {
                            resultMessage = "Tuổi không hợp lệ"
                            showResult = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3)
                ),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = "Kiểm tra",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }
    }
}