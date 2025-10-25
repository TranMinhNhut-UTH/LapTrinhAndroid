package com.example.navigation_tasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UTHSmartTasksTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OnboardingApp()
                }
            }
        }
    }
}

@Composable
fun UTHSmartTasksTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF2196F3),
            background = Color.White,
            surface = Color.White
        ),
        content = content
    )
}

@Composable
fun OnboardingApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(navController)
        }
        composable("onboarding1") {
            OnboardingScreen1(navController)
        }
        composable("onboarding2") {
            OnboardingScreen2(navController)
        }
        composable("onboarding3") {
            OnboardingScreen3(navController)
        }
    }
}

@Composable
fun SplashScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo UTH
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color(0xFFE3F2FD), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "UTH",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "UTH SmartTasks",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2196F3)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "UNIVERSITY\nOF TRANSPORT\nHO CHI MINH CITY",
                fontSize = 10.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp
            )
        }
    }

    // Auto navigate after 3 seconds
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(3000)
        navController.navigate("onboarding1") {
            popUpTo("splash") { inclusive = true }
        }
    }
}

@Composable
fun OnboardingScreen1(navController: NavHostController) {
    OnboardingTemplate(
        title = "Easy Time Management",
        description = "With management based on priority and daily tasks, it will give you convenience in managing and determining the tasks that must be done first",
        illustration = {
            // Illustration 1
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .background(Color(0xFFE3F2FD), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Calendar icon
                    Box(
                        modifier = Modifier
                            .size(140.dp)
                            .background(Color(0xFF2196F3), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "📅",
                            fontSize = 60.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Task checkboxes
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        repeat(3) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color(0xFF90CAF9), RoundedCornerShape(8.dp))
                            )
                        }
                    }
                }
            }
        },
        onNext = { navController.navigate("onboarding2") },
        showBack = false
    )
}

@Composable
fun OnboardingScreen2(navController: NavHostController) {
    OnboardingTemplate(
        title = "Increase Work Effectiveness",
        description = "Time management and the determination of more important tasks will give your job ambition better and always improve",
        illustration = {
            // Illustration 2
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .background(Color(0xFFE3F2FD), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Person 1
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color(0xFF2196F3), RoundedCornerShape(40.dp))
                    )

                    // Charts
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Box(
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(60.dp)
                                    .background(Color(0xFF90CAF9), RoundedCornerShape(4.dp))
                            )
                            Box(
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(80.dp)
                                    .background(Color(0xFF2196F3), RoundedCornerShape(4.dp))
                            )
                            Box(
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(40.dp)
                                    .background(Color(0xFF90CAF9), RoundedCornerShape(4.dp))
                            )
                        }
                    }

                    // Person 2
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color(0xFF2196F3), RoundedCornerShape(40.dp))
                    )
                }
            }
        },
        onNext = { navController.navigate("onboarding3") },
        onBack = { navController.popBackStack() },
        showBack = true
    )
}

@Composable
fun OnboardingScreen3(navController: NavHostController) {
    OnboardingTemplate(
        title = "Reminder Notification",
        description = "The advantage of this application is that it also provides reminders for you so you don't forget to keep doing your assignments on time that you have set",
        illustration = {
            // Illustration 3
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .background(Color(0xFFE3F2FD), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                // Phone with notification
                Box(
                    modifier = Modifier
                        .width(160.dp)
                        .height(240.dp)
                        .background(Color.White, RoundedCornerShape(24.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color(0xFF2196F3), RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "🔔",
                            fontSize = 50.sp
                        )
                    }
                }
            }
        },
        onNext = {
            // Navigate back to splash screen
            navController.navigate("splash") {
                popUpTo(0) { inclusive = true }
            }
        },
        onBack = { navController.popBackStack() },
        showBack = true,
        isLastScreen = true
    )
}

@Composable
fun OnboardingTemplate(
    title: String,
    description: String,
    illustration: @Composable () -> Unit,
    onNext: () -> Unit,
    onBack: (() -> Unit)? = null,
    showBack: Boolean = false,
    isLastScreen: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        // Back button
        if (showBack && onBack != null) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF2196F3)
                )
            }
        }

        // Skip button
        if (!isLastScreen) {
            TextButton(
                onClick = { /* Skip logic */ },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Text(
                    text = "Skip",
                    color = Color(0xFF2196F3),
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Illustration
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                illustration()
            }

            // Content
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 32.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
            }

            // Navigation button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onNext,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3)
                    ),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text(
                        text = if (isLastScreen) "Get Started" else "Next",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}