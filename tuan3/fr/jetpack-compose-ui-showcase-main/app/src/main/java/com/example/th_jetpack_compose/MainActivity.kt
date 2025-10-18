package com.example.th_jetpack_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage // <-- IMPORT MỚI cho Coil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "welcome") {
                    composable("welcome") { WelcomeScreen(navController) }
                    composable("list") { ComponentsListScreen(navController) }
                    composable("textDetail") { TextDetailScreen(navController) }

                    // --- [MỚI] THÊM 2 ROUTE MỚI ---
                    composable("imageDetail") { ImageDetailScreen(navController) }
                    composable("textFieldDetail") { TextFieldDetailScreen(navController) }
                }
            }
        }
    }
}

// --- MÀN HÌNH 1: WELCOME SCREEN (Không đổi) ---
@Composable
fun WelcomeScreen(navController: NavHostController) {
    val imageLogo = painterResource(id = R.drawable.logo_app)

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Trần Minh Nhựt", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "087204014710", fontSize = 14.sp, color = Color.Gray)
            }
        },
        bottomBar = {
            Button(
                onClick = { navController.navigate("list") }, // Điều hướng khi nhấn nút
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3), // Xanh lam (Blue 500)
                    contentColor = Color.White          // Màu chữ trắng
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 48.dp)
                    .height(50.dp)
            ) {
                Text(text = "I'm ready", fontSize = 16.sp)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = imageLogo,
                contentDescription = "Logo ứng dụng",
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "Jetpack Compose", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Jetpack Compose is a modern UI toolkit for building native Android applications using a declarative programming approach.",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        }
    }
}

// --- MÀN HÌNH 2: COMPONENTS LIST SCREEN (Cập nhật onClick) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComponentsListScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("UI Components List", color = Color(0xFF2196F3), fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // Nhóm Display
            item { CategoryHeader("Display") }
            item {
                ComponentItem("Text", "Displays text", onClick = { navController.navigate("textDetail") })
            }
            // --- [CẬP NHẬT] Thêm onClick cho Image ---
            item {
                ComponentItem("Image", "Displays an image", onClick = { navController.navigate("imageDetail") })
            }

            // Nhóm Input
            item { CategoryHeader("Input") }
            // --- [CẬP NHẬT] Thêm onClick cho TextField ---
            item {
                ComponentItem("TextField", "Input field for text", onClick = { navController.navigate("textFieldDetail") })
            }
            item { ComponentItem("PasswordField", "Input field for passwords") }

            // Nhóm Layout
            item { CategoryHeader("Layout") }
            item { ComponentItem("Column", "Arranges elements vertically") }
            item { ComponentItem("Row", "Arranges elements horizontally") }

            // Mục đặc biệt
            item {
                SpecialComponentItem("Tự tìm hiểu", "Tìm ra tất cả các thành phần UI Cơ bản")
            }
        }
    }
}

// --- CÁC COMPOSABLE HỖ TRỢ (Không đổi) ---
@Composable
fun CategoryHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    )
}

@Composable
fun ComponentItem(title: String, description: String, onClick: (() -> Unit)? = null) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFD0E4FF)) // Màu xanh nhạt
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(16.dp)
    ) {
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(text = description, fontSize = 14.sp, color = Color.DarkGray)
    }
}

@Composable
fun SpecialComponentItem(title: String, description: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFFFD5D5)) // Màu đỏ nhạt
            .padding(16.dp)
    ) {
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Red)
        Text(text = description, fontSize = 14.sp, color = Color.DarkGray)
    }
}

// --- MÀN HÌNH 3: TEXT DETAIL SCREEN (Không đổi) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextDetailScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Text Detail", color = MaterialTheme.colorScheme.primary) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) { // Xử lý quay lại
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            FormattedText()
        }
    }
}

@Composable
fun FormattedText() {
    val annotatedString = buildAnnotatedString {
        append("The ")
        withStyle(style = SpanStyle(textDecoration = TextDecoration.LineThrough)) {
            append("quick")
        }
        append(" ")
        withStyle(style = SpanStyle(color = Color(0xFFB56D07), fontSize = 32.sp, fontWeight = FontWeight.Bold)) {
            append("Brown")
        }
        append("\nfox j u m p s")
        withStyle(style = SpanStyle(fontSize = 12.sp)) {
        }

        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(" over")
        }
        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
            append("\nthe")
        }
        withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
            append(" lazy")
        }
        append(" dog.")
    }

    Text(
        text = annotatedString,
        fontSize = 24.sp,
        fontFamily = FontFamily.Serif,
        lineHeight = 40.sp
    )
}

// ---MÀN HÌNH 4: IMAGE DETAIL SCREEN ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Images", color = MaterialTheme.colorScheme.primary) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = "https://logo.svgcdn.com/devicon/jetpackcompose-original.png",
                contentDescription = "Image from URL",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop // Crop ảnh cho vừa
            )
            Text(
                text = "https://logo.svgcdn.com/devicon/jetpackcompose-original.png",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 4.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.logo_app),
                contentDescription = "In app image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "In app",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

// ---MÀN HÌNH 5: TEXTFIELD DETAIL SCREEN ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldDetailScreen(navController: NavHostController) {
    var textValue by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TextField", color = MaterialTheme.colorScheme.primary) }, // Tiêu đề như trong hình
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // TextField
            OutlinedTextField(
                value = textValue,
                onValueChange = { textValue = it },
                placeholder = { Text("Thông tin nhập") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Dòng text màu đỏ như trong hình
            Text(
                text = "Tự động cập nhật dữ liệu theo textfield",
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Dữ liệu đang nhập: $textValue",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// --- PREVIEWS ---
@Preview(showBackground = true, name = "Welcome Screen", widthDp = 360, heightDp = 780)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(navController = rememberNavController())
}

@Preview(showBackground = true, name = "UI Components List", widthDp = 360, heightDp = 780)
@Composable
fun UIComponentsListScreenPreview() {
    ComponentsListScreen(navController = rememberNavController())
}

@Preview(showBackground = true, name = "Text Detail Screen", widthDp = 360, heightDp = 780)
@Composable
fun TextDetailScreenPreview() {
    TextDetailScreen(navController = rememberNavController())
}

@Preview(showBackground = true, name = "Image Detail Screen", widthDp = 360, heightDp = 780)
@Composable
fun ImageDetailScreenPreview() {
    MaterialTheme {
        ImageDetailScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true, name = "TextField Detail Screen", widthDp = 360, heightDp = 780)
@Composable
fun TextFieldDetailScreenPreview() {
    MaterialTheme {
        TextFieldDetailScreen(navController = rememberNavController())
    }
}