package com.example.library_management

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Data class cho sách
data class Book(
    val id: String,
    val title: String,
    var isBorrowed: Boolean = false
)

// Data class cho sinh viên
data class Student(
    val id: String,
    val name: String,
    val borrowedBooks: MutableList<Book> = mutableListOf()
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LibraryManagementTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LibraryManagementApp()
                }
            }
        }
    }
}

@Composable
fun LibraryManagementTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF1976D2),
            background = Color.White,
            surface = Color(0xFFF5F5F5)
        ),
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryManagementApp() {
    var selectedTab by remember { mutableStateOf(0) }

    // Danh sách TẤT CẢ các sách trong hệ thống (không đổi)
    val allBooks = remember {
        listOf(
            Book("B001", "Sách 01"),
            Book("B002", "Sách 02"),
            Book("B003", "Sách 03"),
            Book("B004", "Sách 04"),
            Book("B005", "Sách 05")
        )
    }

    // Dữ liệu sinh viên
    val students = remember {
        mutableStateListOf(
            Student("SV001", "Nguyen Van A"),
            Student("SV002", "Nguyen Thi B"),
            Student("SV003", "Nguyen Van C")
        )
    }

    var selectedStudentIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Hệ thống",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Quản lý Thư viện",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = {
                        Icon(
                            if (selectedTab == 0) Icons.Filled.Home else Icons.Outlined.Home,
                            contentDescription = "Quản lý"
                        )
                    },
                    label = { Text("Quản lý") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF1976D2),
                        selectedTextColor = Color(0xFF1976D2),
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color(0xFFE3F2FD)
                    )
                )

                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = {
                        Icon(
                            painterResource(android.R.drawable.ic_menu_agenda),
                            contentDescription = "DS Sách"
                        )
                    },
                    label = { Text("DS Sách") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF1976D2),
                        selectedTextColor = Color(0xFF1976D2),
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color(0xFFE3F2FD)
                    )
                )

                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = {
                        Icon(
                            if (selectedTab == 2) Icons.Filled.Person else Icons.Outlined.Person,
                            contentDescription = "Sinh viên"
                        )
                    },
                    label = { Text("Sinh viên") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF1976D2),
                        selectedTextColor = Color(0xFF1976D2),
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color(0xFFE3F2FD)
                    )
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedTab) {
                0 -> ManagementScreen(
                    students = students,
                    allBooks = allBooks,
                    selectedStudentIndex = selectedStudentIndex,
                    onStudentIndexChanged = { selectedStudentIndex = it }
                )
                1 -> BookListScreen(allBooks, students)
                2 -> StudentListScreen(students)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagementScreen(
    students: MutableList<Student>,
    allBooks: List<Book>,
    selectedStudentIndex: Int,
    onStudentIndexChanged: (Int) -> Unit
) {
    var showStudentDialog by remember { mutableStateOf(false) }
    var showBookDialog by remember { mutableStateOf(false) }

    val selectedStudent = students[selectedStudentIndex]

    // Lấy danh sách sách chưa bị mượn bởi BẤT KỲ AI
    val borrowedBookIds = students.flatMap { it.borrowedBooks.map { book -> book.id } }.toSet()
    val availableBooks = allBooks.filter { it.id !in borrowedBookIds }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Chọn sinh viên
        Text(
            text = "Sinh viên",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = selectedStudent.name,
                onValueChange = {},
                modifier = Modifier.weight(1f),
                enabled = false,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = Color.LightGray,
                    disabledTextColor = Color.Black
                )
            )

            Button(
                onClick = { showStudentDialog = true },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1976D2)
                )
            ) {
                Text("Thay đổi")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Danh sách sách
        Text(
            text = "Danh sách sách",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            if (selectedStudent.borrowedBooks.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Bạn chưa mượn quyển sách nào",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Nhấn 'Thêm' để bắt đầu hành trình đọc sách!",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = selectedStudent.borrowedBooks,
                        key = { it.id }
                    ) { book ->
                        BookItem(
                            book = book,
                            onRemove = {
                                selectedStudent.borrowedBooks.remove(book)
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nút thêm
        Button(
            onClick = { showBookDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1976D2)
            ),
            enabled = availableBooks.isNotEmpty()
        ) {
            Text(
                text = "Thêm",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    // Dialog chọn sinh viên
    if (showStudentDialog) {
        AlertDialog(
            onDismissRequest = { showStudentDialog = false },
            title = { Text("Chọn sinh viên") },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp)
                ) {
                    students.forEachIndexed { index, student ->
                        TextButton(
                            onClick = {
                                println("Clicked student: ${student.name}, index: $index")
                                onStudentIndexChanged(index)
                                println("After onStudentIndexChanged")
                                showStudentDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = student.name,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start,
                                color = if (index == selectedStudentIndex) Color(0xFF1976D2) else Color.Black,
                                fontWeight = if (index == selectedStudentIndex) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                        if (index < students.size - 1) {
                            HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showStudentDialog = false }) {
                    Text("Đóng")
                }
            }
        )
    }

    // Dialog chọn sách để mượn
    if (showBookDialog) {
        AlertDialog(
            onDismissRequest = { showBookDialog = false },
            title = { Text("Chọn sách để mượn") },
            text = {
                if (availableBooks.isEmpty()) {
                    Text(
                        text = "Không còn sách nào để mượn!",
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                } else {
                    LazyColumn {
                        items(availableBooks) { book ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                onClick = {
                                    // Thêm COPY của sách vào danh sách mượn
                                    selectedStudent.borrowedBooks.add(book.copy())
                                    showBookDialog = false
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                )
                            ) {
                                Text(
                                    text = book.title,
                                    modifier = Modifier.padding(16.dp),
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showBookDialog = false }) {
                    Text("Đóng")
                }
            }
        )
    }
}

@Composable
fun BookItem(
    book: Book,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = true,
                    onCheckedChange = null,
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFFE91E63)
                    )
                )
                Text(
                    text = book.title,
                    fontSize = 16.sp
                )
            }

            IconButton(onClick = onRemove) {
                Icon(
                    painter = painterResource(android.R.drawable.ic_menu_close_clear_cancel),
                    contentDescription = "Xóa",
                    tint = Color.Red
                )
            }
        }
    }
}

@Composable
fun BookListScreen(allBooks: List<Book>, students: List<Student>) {
    // Tính toán sách nào đang được mượn
    val borrowedBookIds = students.flatMap { it.borrowedBooks.map { book -> book.id } }.toSet()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Danh sách tất cả sách",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(allBooks) { book ->
                val isBorrowed = book.id in borrowedBookIds
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isBorrowed) Color(0xFFFFEBEE) else Color.White
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = book.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (isBorrowed) "Đã mượn" else "Còn sách",
                                fontSize = 14.sp,
                                color = if (isBorrowed) Color.Red else Color(0xFF4CAF50)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StudentListScreen(students: List<Student>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Danh sách sinh viên",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(students) { student ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = student.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "MSSV: ${student.id}",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "Sách đã mượn: ${student.borrowedBooks.size}",
                            fontSize = 14.sp,
                            color = Color(0xFF1976D2)
                        )
                    }
                }
            }
        }
    }
}