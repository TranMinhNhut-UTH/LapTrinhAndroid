package com.example.onemitem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "root") {
                    composable("root") {
                        RootScreen(onPushClick = {
                            navController.navigate("choice")
                        })
                    }
                    composable("choice") {
                        ChoiceScreen(
                            onColumnClick = { navController.navigate("column_screen") },
                            onLazyColumnClick = { navController.navigate("lazy_column_screen") }
                        )
                    }
                    composable("column_screen") {
                        ColumnScreen()
                    }
                    composable("lazy_column_screen") {
                        LazyColumnScreen(onItemClick = { itemId ->
                            navController.navigate("item_detail/$itemId")
                        })
                    }
                    composable(
                        route = "item_detail/{itemId}",
                        arguments = listOf(navArgument("itemId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val itemId = backStackEntry.arguments?.getInt("itemId") ?: 0
                        ItemDetailScreen(
                            itemId = itemId,
                            onBackToRootClick = {
                                navController.navigate("root") {
                                    popUpTo("root") { inclusive = true }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootScreen(onPushClick: () -> Unit) {
    Scaffold(
        // Thêm một thanh tiêu đề (Top App Bar) cho màn hình
        topBar = {
            TopAppBar(
                title = { Text("Jetpack Compose Demo") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        // Nội dung chính của màn hình
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Áp dụng padding từ Scaffold để không bị che bởi TopAppBar
                .padding(24.dp), // Thêm padding cho nội dung
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Căn giữa các thành phần theo chiều dọc
        ) {
            // Tiêu đề lớn
            Text(
                text = "Chào mừng bạn!",
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Đoạn văn mô tả
            Text(
                text = "Ứng dụng này sẽ minh họa sự khác biệt về hiệu năng giữa Column và LazyColumn khi xử lý danh sách lớn.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Nút PUSH chính
            Button(onClick = onPushClick) {
                Text("PUSH")
            }
        }
    }
}

@Composable
fun ChoiceScreen(onColumnClick: () -> Unit, onLazyColumnClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onColumnClick,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Column")
        }
        Button(
            onClick = onLazyColumnClick,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("LazyColumn")
        }
    }
}

@Composable
fun ColumnScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        for (i in 1..1_000_000) {
            Text(
                text = "Item $i",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun LazyColumnScreen(onItemClick: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = rememberLazyListState()
    ) {
        items(count = 1_000_000) { index ->
            Text(
                text = "Item ${index + 1}",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(index + 1) }
                    .padding(16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailScreen(itemId: Int, onBackToRootClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Trang chi tiết") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Nội dung item $itemId",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onBackToRootClick) {
                Text("BACK TO ROOT")
            }
        }
    }
}

@Composable
fun MyApplicationTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(),
        typography = Typography(),
        content = content
    )
}