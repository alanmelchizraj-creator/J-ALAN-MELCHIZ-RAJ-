package com.example.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun IntroScreen(onSkipIntro: () -> Unit) {
    var sceneIndex by remember { mutableStateOf(1) }
    var visible by remember { mutableStateOf(true) }

    // Auto-advance scenes every 5 seconds
    LaunchedEffect(sceneIndex) {
        visible = true
        delay(4000)
        visible = false
        delay(600) // wait for fade out
        if (sceneIndex < 4) {
            sceneIndex++
        } else {
            onSkipIntro()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C1D0E)) // Deep dark green backdrop
    ) {
        // Render Animated Scene Graphics
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(1000)),
            exit = fadeOut(animationSpec = tween(500)),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                when (sceneIndex) {
                    1 -> PeacefulForestScene()
                    2 -> PollutedFireScene()
                    3 -> SpiritAppearsScene()
                    4 -> GuardianAwakeningScene()
                }
            }
        }

        // Scene Narration Overlay at bottom
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 48.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                    )
                )
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = when (sceneIndex) {
                        1 -> "Long ago, the island of Evergreen lived in beautiful harmony..."
                        2 -> "But humans became careless, pollution spread, and fires destroyed habitats."
                        3 -> "The balance was broken. In our darkest hour, the ancient Spirit of Nature appeared..."
                        else -> "\"Guardian: Arise! Nature needs your courage to restore Evergreen Island!\""
                    },
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    lineHeight = 28.sp,
                    fontFamily = FontFamily.Serif
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                // Indicators
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(4) { idx ->
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(if (sceneIndex == idx + 1) 12.dp else 8.dp)
                                .background(
                                    color = if (sceneIndex == idx + 1) Color(0xFF81C784) else Color.Gray.copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(50)
                                )
                        )
                    }
                }
            }
        }

        // Top skip action bar
        Row(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = onSkipIntro,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black.copy(alpha = 0.5f),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("SKIP STORY", fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun PeacefulForestScene() {
    val infiniteTransition = rememberInfiniteTransition(label = "forest")
    val breezeOffset by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breeze"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        // Background Sky gradient
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF80DEEA), Color(0xFFC8E6C9))
            )
        )

        // Draw rolling green hills
        drawCircle(
            color = Color(0xFF4CAF50),
            radius = w * 0.9f,
            center = Offset(w * 0.2f, h * 1.1f)
        )
        drawCircle(
            color = Color(0xFF388E3C),
            radius = w * 0.7f,
            center = Offset(w * 0.8f, h * 1.15f)
        )

        // Draw beautiful sun
        drawCircle(
            color = Color(0xFFFFF176),
            radius = 120f,
            center = Offset(w * 0.15f, h * 0.25f)
        )

        // Draw some gorgeous styled trees gently blowing in the breeze
        drawTree(Offset(w * 0.3f, h * 0.65f), 150f, breezeOffset)
        drawTree(Offset(w * 0.7f, h * 0.7f), 220f, breezeOffset * 0.7f)
        drawTree(Offset(w * 0.85f, h * 0.62f), 130f, breezeOffset * 1.2f)

        // Little stream running through
        drawArc(
            color = Color(0xFF29B6F6),
            startAngle = 45f,
            sweepAngle = 90f,
            useCenter = false,
            topLeft = Offset(-100f, h * 0.75f),
            size = Size(w + 200f, h * 0.5f)
        )
    }
}

@Composable
fun PollutedFireScene() {
    val infiniteTransition = rememberInfiniteTransition(label = "fire")
    val flick by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flick"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        // Dark sky with heavy orange smoke glow
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF212121), Color(0xFFE64A19))
            )
        )

        // Scorched Earth hill
        drawCircle(
            color = Color(0xFF3E2723),
            radius = w * 0.8f,
            center = Offset(w * 0.5f, h * 1.2f)
        )

        // Draw structural log silhouette under fire
        drawRect(
            color = Color(0xFF212121),
            topLeft = Offset(w * 0.2f, h * 0.7f),
            size = Size(180f, 350f)
        )

        // Draw Fire flames using gradient
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFFFFEB3B), Color(0xFFFF5722), Color.Transparent),
                center = Offset(w * 0.4f, h * 0.65f),
                radius = 280f * flick
            ),
            radius = 280f * flick,
            center = Offset(w * 0.4f, h * 0.65f)
        )

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFFFFEB3B), Color(0xFFD84315), Color.Transparent),
                center = Offset(w * 0.65f, h * 0.68f),
                radius = 200f * flick
            ),
            radius = 200f * flick,
            center = Offset(w * 0.65f, h * 0.68f)
        )

        // Dark grey factory smoke stacks in background
        drawRect(
            color = Color(0xFF424242),
            topLeft = Offset(w * 0.75f, h * 0.35f),
            size = Size(80f, 250f)
        )
        // Smoke plume
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFF212121).copy(alpha = 0.9f), Color.Transparent),
                center = Offset(w * 0.8f, h * 0.3f),
                radius = 110f
            ),
            radius = 110f,
            center = Offset(w * 0.8f, h * 0.3f)
        )
    }
}

@Composable
fun SpiritAppearsScene() {
    val infiniteTransition = rememberInfiniteTransition(label = "spirit")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        // Dark teal neon magical background
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFF004D40), Color(0xFF001A11)),
                center = Offset(w * 0.5f, h * 0.5f),
                radius = w
            )
        )

        // Magical nature sparkle particles
        drawCircle(Color(0xFF81C784), 8f, Offset(w * 0.2f, h * 0.3f))
        drawCircle(Color(0xFFE8F5E9), 12f, Offset(w * 0.8f, h * 0.25f))
        drawCircle(Color(0xFFFFF176), 6f, Offset(w * 0.35f, h * 0.7f))
        drawCircle(Color(0xFF81C784), 10f, Offset(w * 0.75f, h * 0.62f))

        // Large glowing aura for the Spirit of Nature
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFFA5D6A7).copy(alpha = 0.7f * pulse), Color(0xFF1B5E20).copy(alpha = 0.3f), Color.Transparent),
                center = Offset(w * 0.5f, h * 0.45f),
                radius = 350f * pulse
            ),
            radius = 350f,
            center = Offset(w * 0.5f, h * 0.45f)
        )

        // Draw stylized leafy crown face (the Spirit)
        drawCircle(Color(0xFFE8F5E9), 80f, Offset(w * 0.5f, h * 0.45f)) // Head
        drawArc( // Leaf crown
            color = Color(0xFF4CAF50),
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = true,
            topLeft = Offset(w * 0.5f - 110f, h * 0.45f - 140f),
            size = Size(220f, 150f)
        )
    }
}

@Composable
fun GuardianAwakeningScene() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        // Sunny beam of hope background
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF64B5F6), Color(0xFF81C784))
            )
        )

        // Draw light rays centered on top
        drawArc(
            color = Color.White.copy(alpha = 0.15f),
            startAngle = 60f,
            sweepAngle = 60f,
            useCenter = true,
            topLeft = Offset(w * 0.5f - 1000f, -400f),
            size = Size(2000f, 2000f)
        )

        // Ground
        drawCircle(
            color = Color(0xFF558B2F),
            radius = w * 0.9f,
            center = Offset(w * 0.5f, h * 1.2f)
        )

        // Guardian silhouette with a staff rising towards the sun!
        drawCircle(Color(0xFF1B5E20), 50f, Offset(w * 0.5f, h * 0.53f)) // Head
        drawRect( // Torso cloak
            color = Color(0xFF1B5E20),
            topLeft = Offset(w * 0.5f - 40f, h * 0.59f),
            size = Size(80f, 150f)
        )

        // Staff
        drawLine(
            color = Color(0xFF3E2723),
            start = Offset(w * 0.58f, h * 0.45f),
            end = Offset(w * 0.58f, h * 0.72f),
            strokeWidth = 12f
        )
        // Glowing Leaf Gem on top of Staff
        drawCircle(
            color = Color(0xFF81C784),
            radius = 25f,
            center = Offset(w * 0.58f, h * 0.43f)
        )
    }
}

// Custom Helper to draw a parameterized tree
fun androidx.compose.ui.graphics.drawscope.DrawScope.drawTree(
    base: Offset,
    height: Float,
    tiltX: Float
) {
    // Trunk
    drawLine(
        color = Color(0xFF5D4037),
        start = base,
        end = Offset(base.x, base.y - height * 0.4f),
        strokeWidth = height * 0.08f
    )

    // Foliage layers on top of each other
    drawCircle(
        color = Color(0xFF2E7D32),
        radius = height * 0.35f,
        center = Offset(base.x + tiltX * 0.7f, base.y - height * 0.5f)
    )
    drawCircle(
        color = Color(0xFF4CAF50),
        radius = height * 0.28f,
        center = Offset(base.x + tiltX, base.y - height * 0.7f)
    )
    drawCircle(
        color = Color(0xFF81C784),
        radius = height * 0.2f,
        center = Offset(base.x + tiltX * 1.3f, base.y - height * 0.85f)
    )
}
