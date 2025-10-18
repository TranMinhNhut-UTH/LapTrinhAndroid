package com.example.uicomponentsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.font.FontStyle

// Enum để quản lý màn hình một cách an toàn hơn
enum class UthScreen {
    Intro,
    List,
    Detail
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UTHTheme {
                // Quản lý trạng thái ở cấp cao nhất
                var currentScreen by rememberSaveable { mutableStateOf(UthScreen.Intro) }
                var selectedComponent by rememberSaveable { mutableStateOf<ComponentItem?>(null) }

                // Điều hướng chính
                when (currentScreen) {
                    UthScreen.Intro -> {
                        IntroScreen(
                            onStartClick = {
                                currentScreen = UthScreen.List
                            }
                        )
                    }
                    UthScreen.List -> {
                        ComponentListScreen(
                            onNavigateToDetail = { component ->
                                selectedComponent = component
                                currentScreen = UthScreen.Detail
                            }
                        )
                    }
                    UthScreen.Detail -> {
                        selectedComponent?.let { component ->
                            ComponentDetailScreen(
                                component = component,
                                onNavigateBack = {
                                    currentScreen = UthScreen.List
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

// --- Giao diện người dùng ---

@Composable
fun UTHTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF00796B), // Màu xanh lá đậm
            secondary = Color(0xFFF44336), // Màu đỏ
            background = Color(0xFFF5F5F5) // Nền xám nhạt
        )
    ) {
        content()
    }
}

@Composable
fun IntroScreen(onStartClick: () -> Unit) {
    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.compose_logo),
                contentDescription = "Jetpack Compose Logo",
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Tiêu đề
            Text(
                text = "Jetpack Compose",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Mô tả
            Text(
                text = "A modern UI toolkit for building native Android applications with a declarative approach.",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Nút bấm
            Button(
                onClick = onStartClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF42A5F5),
                    contentColor = Color.White
                )
            ) {
                Text("I'm ready", fontSize = 18.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComponentListScreen(onNavigateToDetail: (ComponentItem) -> Unit) {
    val components = getComponents()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("UI Components List", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            components.forEach { (category, items) ->
                item {
                    Text(
                        text = category,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                items(items) { component ->
                    ComponentCard(component = component, onClick = { onNavigateToDetail(component) })
                }
            }
        }
    }
}

@Composable
fun ComponentCard(component: ComponentItem, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = component.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = component.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComponentDetailScreen(component: ComponentItem, onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(component.name, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Phần Demo
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    component.demo()
                }
            }
        }
    }
}
// --- Dữ liệu và các Component Demo ---

data class ComponentItem(
    val name: String,
    val description: String,
    val details: String,
    val demo: @Composable () -> Unit
)

fun getComponents(): Map<String, List<ComponentItem>> {
    return mapOf(
        "Display Components" to listOf(
            ComponentItem(
                name = "Text",
                description = "Displays text with various styles.",
                details = "Sử dụng để hiển thị văn bản với các kiểu khác nhau như Bold, Italic, Underline.",
                demo = { TextDemo() }
            ),
            ComponentItem(
                name = "Image",
                description = "Displays images from resources.",
                details = "Hiển thị hình ảnh từ thư mục drawable của dự án.",
                demo = { ImageDemo() }
            )
        ),
        "Input Components" to listOf(
            ComponentItem(
                name = "TextField",
                description = "Input field for text.",
                details = "Trường nhập liệu cho phép người dùng nhập dữ liệu văn bản.",
                demo = { TextFieldDemo() }
            ),
            ComponentItem(
                name = "PasswordField",
                description = "Input field for passwords.",
                details = "Trường nhập liệu mật khẩu với ký tự ẩn để bảo vệ dữ liệu.",
                demo = { PasswordFieldDemo() }
            )
        ),
        "Layout Components" to listOf(
            ComponentItem(
                name = "Row Layout",
                description = "Arranges elements horizontally.",
                details = "Sắp xếp các phần tử theo hướng ngang.",
                demo = { RowDemo() }
            ),
            ComponentItem(
                name = "Column Layout",
                description = "Arranges elements vertically.",
                details = "Sắp xếp các phần tử theo hướng dọc.",
                demo = { ColumnDemo() }
            )
        )
    )
}

@Composable
fun TextDemo() {
    Box(
        modifier = Modifier
            .fillMaxSize(), // chiếm toàn màn hình
        contentAlignment = Alignment.Center // căn giữa nội dung
    ) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            // The - chữ thường, viết hoa chữ T
            Text("The", fontSize = 18.sp)

            // quick - gạch ngang chữ
            Text(
                "quick",
                fontSize = 18.sp,
                textDecoration = TextDecoration.LineThrough
            )

            // Brown - có màu và in hoa chữ B
            Text(
                "Brown",
                fontSize = 18.sp,
                color = Color(0xFF1565C0), // xanh lam đậm
                fontWeight = FontWeight.SemiBold
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            // fox - chữ thường
            Text("fox", fontSize = 18.sp)

            // jumps - tách rời từng chữ cái
            Text(
                "j u m p s",
                fontSize = 18.sp,
                letterSpacing = 2.sp
            )

            // over - in đậm
            Text(
                "over",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            // the - gạch chân
            Text(
                "the",
                fontSize = 18.sp,
                textDecoration = TextDecoration.Underline
            )

            // lazy - in nghiêng
            Text(
                "lazy",
                fontSize = 18.sp,
                fontStyle = FontStyle.Italic
            )

            // dog - ghi thường
            Text("dog", fontSize = 18.sp)
        }
        }
    }
}

@Composable
fun ImageDemo() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.compose_logo),
            contentDescription = "Demo Image",
            modifier = Modifier.size(100.dp)
        )
        Text("UTH Campus Image", modifier = Modifier.padding(top = 8.dp))
    }
}

@Composable
fun TextFieldDemo() {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Enter text here") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun PasswordFieldDemo() {
    var password by remember { mutableStateOf("") }
    TextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Enter password") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun RowDemo() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(modifier = Modifier.size(60.dp).background(Color.Cyan, RoundedCornerShape(8.dp)))
        Box(modifier = Modifier.size(60.dp).background(Color.Magenta, RoundedCornerShape(8.dp)))
        Box(modifier = Modifier.size(60.dp).background(Color.Yellow, RoundedCornerShape(8.dp)))
    }
}

@Composable
fun ColumnDemo() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(60.dp).background(Color.Cyan, RoundedCornerShape(8.dp)))
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.fillMaxWidth().height(60.dp).background(Color.Magenta, RoundedCornerShape(8.dp)))
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.fillMaxWidth().height(60.dp).background(Color.Yellow, RoundedCornerShape(8.dp)))
    }
}

// --- Previews ---
@Preview(showBackground = true)
@Composable
fun IntroPreview() {
    UTHTheme {
        IntroScreen(onStartClick = {})
    }
}