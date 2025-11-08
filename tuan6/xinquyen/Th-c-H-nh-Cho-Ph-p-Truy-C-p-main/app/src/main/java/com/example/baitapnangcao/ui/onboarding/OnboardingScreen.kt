package com.example.baitapnangcao.ui.onboarding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.baitapnangcao.OnboardingScreen
import com.example.baitapnangcao.ui.theme.BaiTapNangCaoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaiTapNangCaoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var showPermissionFlow by remember { mutableStateOf(false) }

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(onClick = { showPermissionFlow = true }) {
                            Text("Bắt đầu quy trình xin quyền")
                        }

                        if (showPermissionFlow) {
                            OnboardingScreen(
                                onFlowFinished = {
                                    showPermissionFlow = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}