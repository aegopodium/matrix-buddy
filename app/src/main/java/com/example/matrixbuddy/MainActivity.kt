package com.example.matrixbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.border
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.matrixbuddy.ui.theme.MatrixBuddyTheme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MatrixBuddyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MatrixBuddyApp(
                        modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MatrixBuddyApp(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .border(1.dp, Color.Red)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Red)
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            QuadrantBox(
                title = "Important & Urgent",
                color = Color(0xFFFFCDD2),
                modifier = Modifier.weight(1f)
            )
            QuadrantBox(
                title = "Important & Not Urgent",
                color = Color(0xFFC8E6C9),
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Red)
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            QuadrantBox(
                title = "Not Important & Urgent",
                color = Color(0xFFFFF9C4),
                modifier = Modifier.weight(1f)
            )
            QuadrantBox(
                title = "Not Important & Not Urgent",
                color = Color(0xFFCFD8DC),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun QuadrantBox(title: String, color: Color, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxHeight()
            .background(color, shape = RoundedCornerShape(16.dp))
            .clickable { /* Later: open task list */ }
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}