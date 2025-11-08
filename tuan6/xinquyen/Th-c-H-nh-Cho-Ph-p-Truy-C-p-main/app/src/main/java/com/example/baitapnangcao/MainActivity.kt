package com.example.baitapnangcao

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.baitapnangcao.ui.theme.*

@Composable
fun OnboardingScreen(
    onFlowFinished: () -> Unit
) {
    var currentScreen by remember { mutableStateOf(0) }


    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            currentScreen = 1
        }
    )

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            currentScreen = 2
        }
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            onFlowFinished() // << THAY ĐỔI: Báo cho MainActivity biết đã xong
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)) // Nền mờ
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Dialog 1: Location
            AnimatedVisibility(visible = currentScreen == 0, enter = fadeIn(), exit = fadeOut()) {
                PermissionDialog(
                    icon = rememberVectorPainter(Icons.Default.LocationOn),
                    iconBackgroundColor = MintBg, iconTint = IconTintMint,
                    title = "Location", description = "Allow maps to access your location while you use the app?",
                    primaryButtonText = "Allow",
                    onPrimaryButtonClick = {
                        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    },
                    onSkipButtonClick = { currentScreen = 1 } // Bỏ qua, sang màn hình 2
                )
            }
            // Dialog 2: Notification
            AnimatedVisibility(visible = currentScreen == 1, enter = fadeIn(), exit = fadeOut()) {
                PermissionDialog(
                    icon = rememberVectorPainter(Icons.Default.Notifications),
                    iconBackgroundColor = CreamBg, iconTint = IconTintYellow,
                    title = "Notification", description = "Please enable notifications to receive updates and reminders",
                    primaryButtonText = "Turn on",
                    onPrimaryButtonClick = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        } else {
                            currentScreen = 2
                        }
                    },
                    onSkipButtonClick = { currentScreen = 2 } // Bỏ qua, sang màn hình 3
                )
            }
            // Dialog 3: Camera
            AnimatedVisibility(visible = currentScreen == 2, enter = fadeIn(), exit = fadeOut()) {
                PermissionDialog(
                    icon = rememberVectorPainter(Icons.Default.CameraAlt),
                    iconBackgroundColor = MintBg, iconTint = IconTintMint,
                    title = "Camera", description = "We need access to your camera to scan QR codes",
                    primaryButtonText = "Turn on",
                    onPrimaryButtonClick = {
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    },
                    onSkipButtonClick = { onFlowFinished() } // << THAY ĐỔI: Báo cho MainActivity biết đã xong
                )
            }
        }
    }
}

@Composable
fun PermissionDialog(
    icon: Painter, iconBackgroundColor: Color, iconTint: Color,
    title: String, description: String, primaryButtonText: String,
    onPrimaryButtonClick: () -> Unit, onSkipButtonClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .wrapContentHeight(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(percent = 50))
                    .background(iconBackgroundColor)
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = icon, contentDescription = title,
                    modifier = Modifier.fillMaxSize(),
                    colorFilter = ColorFilter.tint(iconTint)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = description, fontSize = 16.sp, color = Color.DarkGray,
                textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onPrimaryButtonClick,
                colors = ButtonDefaults.buttonColors(containerColor = Orange500),
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(primaryButtonText, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = onSkipButtonClick,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.5.dp, Orange100)
            ) {
                Text("Skip for now", color = MidGrey, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}
