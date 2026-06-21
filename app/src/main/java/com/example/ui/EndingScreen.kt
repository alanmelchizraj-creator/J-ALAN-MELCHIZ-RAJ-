package com.example.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun EndingScreen(
    onContinue: () -> Unit
) {
    var step by remember { mutableStateOf(1) }

    LaunchedEffect(Unit) {
        delay(4000)
        step = 2
        delay(4000)
        step = 3
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C1D0E)) // Deep rich velvet green backdrop
    ) {
        // Nature Healing Canvas Background
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Vibrant bright blue sky
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF80DEEA), Color(0xFFA5D6A7))
                )
            )

            // Flowing sparkling clean river
            drawArc(
                color = Color(0xFF29B6F6),
                startAngle = 0f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(-100f, h * 0.65f),
                size = Size(w + 200f, h * 0.4f)
            )

            // Radiant Sun beams rising!
            drawCircle(Color(0xFFFFF59D).copy(alpha = 0.25f), 450f, Offset(w / 2f, h * 0.2f))
            drawCircle(Color(0xFFFFF176), 110f, Offset(w / 2f, h * 0.2f))

            // Blooming landscape hills
            drawCircle(Color(0xFF4CAF50), w * 0.9f, Offset(w * 0.1f, h * 1.1f))
            drawCircle(Color(0xFF4CAF50), w * 0.8f, Offset(w * 0.8f, h * 1.12f))

            // Many colorful wild flowers popping everywhere
            drawCircle(Color(0xFFEC407A), 10f, Offset(w * 0.2f, h * 0.82f))
            drawCircle(Color(0xFFFFB300), 12f, Offset(w * 0.35f, h * 0.8f))
            drawCircle(Color(0xFFBA68C8), 8f, Offset(w * 0.68f, h * 0.85f))
            drawCircle(Color(0xFFEC407A), 14f, Offset(w * 0.8f, h * 0.78f))
        }

        // Narrations Overlays
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(24.dp))
                    .padding(28.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Eco,
                    contentDescription = "Spirit of Nature Gift",
                    tint = Color(0xFF81C784),
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                AnimatedContent(
                    targetState = step,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(1000)) togetherWith fadeOut(animationSpec = tween(1000))
                    },
                    label = "end"
                ) { currentStep ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        when (currentStep) {
                            1 -> {
                                Text(
                                    text = "With all ten sanctuaries secured, the dark pollution cloud disintegrates...",
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    fontFamily = FontFamily.Serif
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Trees regrow, rivers run clear, and animals rejoice in harmony!",
                                    color = Color(0xFFA5D6A7),
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                            2 -> {
                                Text(
                                    text = "You answered the Call Of Nature.",
                                    color = Color(0xFFFFD54F),
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Black,
                                    textAlign = TextAlign.Center,
                                    fontFamily = FontFamily.Serif
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "\"Thank you, faithful Guardian. Balance and light have been restored to Evergreen Island.\"\n— The Spirit of Nature",
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    textAlign = TextAlign.Center
                                )
                            }
                            else -> {
                                Text(
                                    text = "Every small action can help protect our planet.",
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    fontFamily = FontFamily.Serif
                                )
                                Spacer(modifier = Modifier.height(24.dp))
                                Button(
                                    onClick = onContinue,
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text("THANK YOU, GUARDIAN", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
