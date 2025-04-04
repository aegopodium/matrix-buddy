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

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*

import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.tween

import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.IconButton
import androidx.activity.compose.BackHandler
import com.example.matrixbuddy.ui.theme.*

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.GoogleFont.Provider
import androidx.compose.ui.text.googlefonts.Font as GoogleFontTypeface
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography

import androidx.compose.material3.Surface

data class Quadrant(
    val name: String,
    val color: Color
)

val provider = Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val interFontFamily = FontFamily(
    GoogleFontTypeface(
        googleFont = GoogleFont("Inter"),
        fontProvider = provider
    )
)

val AppTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    titleLarge = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MatrixBuddyTheme {

                var isSheetOpen by remember { mutableStateOf(false) }
                var selectedQuadrant by remember { mutableStateOf<Quadrant?>(null) }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AppBackground),
                    floatingActionButton = {
                        AddTaskButton(onClick = { isSheetOpen = true })
                    },
                    floatingActionButtonPosition = FabPosition.Center
                ) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        MatrixBuddyApp(
                            onQuadrantClick = { name, color -> selectedQuadrant = Quadrant(name, color) }
                        )

                        AnimatedVisibility(
                            visible = selectedQuadrant != null,
                            enter = fadeIn(animationSpec = tween(300)),
                            exit = fadeOut(animationSpec = tween(300))
                        ) {
                            ExpandedQuadrant(
                                quadrantName = selectedQuadrant!!.name,
                                color = selectedQuadrant!!.color,
                                onClose = { selectedQuadrant = null }
                            )
                        }

                        AnimatedVisibility(
                            visible = isSheetOpen,
                            enter = fadeIn(animationSpec = tween(durationMillis = 250)),
                            exit = fadeOut(animationSpec = tween(durationMillis = 250))
                        ) {
                            TaskInputOverlay(
                                onDismiss = { isSheetOpen = false }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MatrixBuddyTheme(
    content: @Composable () -> Unit,

) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = AppTypography,
        content = content
    )
}

@Composable
fun MatrixBuddyApp(
    modifier: Modifier = Modifier,
    onQuadrantClick: (String, Color) -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            // .border(1.dp, Color.Red)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                // .border(1.dp, Color.Red)
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            QuadrantBox(
                title = "Important + Urgent",
                color = UrgentRed,
                modifier = Modifier.weight(1f),
                onClick = { onQuadrantClick("Important + Urgent", UrgentRed) }
            )
            QuadrantBox(
                title = "Important",
                color = ImportantGreen,
                modifier = Modifier.weight(1f),
                onClick = { onQuadrantClick("Important", ImportantGreen) }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                // .border(1.dp, Color.Red)
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            QuadrantBox(
                title = "Urgent",
                color = NotImportantUrgentYellow,
                modifier = Modifier.weight(1f),
                onClick = { onQuadrantClick("Urgent", NotImportantUrgentYellow) }
            )
            QuadrantBox(
                title = "Low Priority",
                color = NotImportantNotUrgentGray,
                modifier = Modifier.weight(1f),
                onClick = { onQuadrantClick("Low Priority", NotImportantNotUrgentGray) }
            )
        }
    }
}

@Composable
fun AddTaskButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() }
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add Task")
    }
}

@Composable
fun TaskInputOverlay(onDismiss: () -> Unit) {
    BackHandler(enabled = true, onBack = { onDismiss() })
    Box(
        Modifier
            .fillMaxSize()
            .background(OverlayBackground) // semi-transparent black background
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .background(
                    TaskInputOverlayBackground,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onDismiss() }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = Color.Gray
                    )
                }
            }

            var taskText by remember { mutableStateOf("") }
            var deadlineText by remember { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                value = taskText,
                onValueChange = { taskText = it },
                label = { Text("Task Description")
                }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = deadlineText,
                onValueChange = { deadlineText = it },
                label = { Text("Deadline") }
            )
            Button(
                onClick = { onDismiss() }
            ) {
                Text("Save Task")
            }
        }
    }
}

@Composable
fun QuadrantBox(
    title: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            .fillMaxHeight()
            .background(color, shape = RoundedCornerShape(16.dp))
            .clickable(
                onClick = onClick
            )
            .padding(8.dp)
    ) {
        Text(
            text = title,
            style = typography.titleLarge,
            color = TextPrimary
        )
    }
}

@Composable
fun ExpandedQuadrant(
    quadrantName: String,
    color: Color,
    onClose: () -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(OverlayBackground) // semi-transparent dark background
    ) {
        Surface(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.8f), // slightly smaller than full screen
            shape = RoundedCornerShape(16.dp),
            color = color,
            tonalElevation = 8.dp, // little shadow
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = quadrantName,
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary
                    )
                    IconButton(onClick = { onClose() }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close",
                            tint = Color.Gray
                        )
                    }
                }

                // 🐾 Later here: List of tasks!

                Text(
                    text = "Here will be your tasks!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextPrimary
                )
            }
        }
    }
}