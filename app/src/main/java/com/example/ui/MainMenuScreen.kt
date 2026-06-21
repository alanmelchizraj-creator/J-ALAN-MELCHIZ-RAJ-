package com.example.ui

import android.app.Activity
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun MainMenuScreen(
    onPlayClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAchievementsClick: () -> Unit,
    onEncyclopediaClick: () -> Unit,
    onCreditsClick: () -> Unit,
    onStoryIntroClick: () -> Unit
) {
    val context = LocalContext.current
    val infiniteTransition = rememberInfiniteTransition(label = "menu_bg")
    
    // Wind leaf animation parameter
    val leafProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "leaf_progress"
    )

    // Breathing forest glow factor
    val forestGlow by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = SineWaveEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    // Dynamic drifting leaves data list
    val leaves = remember {
        List(15) {
            LeafState(
                startX = Random.nextFloat(),
                startY = Random.nextFloat() * 0.4f,
                weight = Random.nextFloat() * 0.4f + 0.6f,
                rotationSpeed = Random.nextFloat() * 180f + 90f,
                scale = Random.nextFloat() * 0.4f + 0.8f
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C1D0E)) // Deep rich velvet green backdrop
    ) {
        // Nature Background Canvas (Rich breathing trees, hills, water, sun and drifting leaves)
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Sky gradient
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF64B5F6), Color(0xFFA5D6A7))
                )
            )

            // Dynamic light rays from top left
            drawArc(
                color = Color(0xFFFFF59D).copy(alpha = 0.15f * forestGlow),
                startAngle = 45f,
                sweepAngle = 40f,
                useCenter = true,
                topLeft = Offset(-100f, -100f),
                size = Size(w * 1.5f, h * 1.5f)
            )

            // Misty mountain silhouettes in distance
            drawCircle(
                color = Color(0xFF455A64).copy(alpha = 0.4f),
                radius = w * 0.6f,
                center = Offset(w * 0.2f, h * 0.75f)
            )
            drawCircle(
                color = Color(0xFF37474F).copy(alpha = 0.4f),
                radius = w * 0.5f,
                center = Offset(w * 0.8f, h * 0.8f)
            )

            // Rolling foreground green hills
            drawCircle(
                color = Color(0xFF33691E), // Solid deep grass green
                radius = w * 0.8f,
                center = Offset(w * 0.1f, h * 1.05f)
            )
            drawCircle(
                color = Color(0xFF1B5E20), // Dark background hill
                radius = w * 0.6f,
                center = Offset(w * 0.9f, h * 1.1f)
            )

            // Static tree structures
            drawTree(Offset(w * 0.12f, h * 0.68f), 190f, levelToAngle(leafProgress))
            drawTree(Offset(w * 0.85f, h * 0.72f), 210f, levelToAngle(leafProgress) * -1.2f)

            // Stream of Life running
            drawRect(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF29B6F6), Color(0xFF0288D1))
                ),
                topLeft = Offset(0f, h * 0.85f),
                size = Size(w, h * 0.15f)
            )

            // Draw leaves fluttering
            leaves.forEach { leaf ->
                val calculatedX = ((leaf.startX * w) + (leafProgress * w * 0.6f * leaf.weight)) % w
                val calculatedY = ((leaf.startY * h) + (leafProgress * h * 0.4f * leaf.weight)) % (h * 0.85f)
                val rotateDeg = leaf.rotationSpeed * leafProgress

                drawLeafParticle(
                    centerX = calculatedX,
                    centerY = calculatedY,
                    rotation = rotateDeg,
                    scale = leaf.scale
                )
            }
        }

        // Foreground Game Menu Controls Card Layer
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header Logo banner
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = "CALL OF NATURE",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .shadow(4.dp, RoundedCornerShape(8.dp))
                        .background(Color(0xFF1B5E20).copy(alpha = 0.85f), RoundedCornerShape(16.dp))
                        .padding(horizontal = 24.dp, vertical = 12.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Become the Guardian of the Island",
                    fontSize = 14.sp,
                    color = Color(0xFFFFD54F),
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }

            // Buttons Block
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // Large Play Now Action
                MainMenuButton(
                    text = "PLAY GUARDIAN MAP",
                    icon = Icons.Default.PlayArrow,
                    color = Color(0xFF4CAF50),
                    onClick = onPlayClick
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        MainMenuButton(
                            text = "ENCYCLOPEDIA",
                            icon = Icons.Default.Book,
                            color = Color(0xFFff9100),
                            fontSize = 12.sp,
                            onClick = onEncyclopediaClick
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        MainMenuButton(
                            text = "ACHIEVEMENTS",
                            icon = Icons.Default.EmojiEvents,
                            color = Color(0xFFFFB300),
                            fontSize = 12.sp,
                            onClick = onAchievementsClick
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        MainMenuButton(
                            text = "SETTINGS",
                            icon = Icons.Default.Settings,
                            color = Color(0xFF00ACC1),
                            fontSize = 12.sp,
                            onClick = onSettingsClick
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        MainMenuButton(
                            text = "STORY INTRO",
                            icon = Icons.Default.AutoStories,
                            color = Color(0xFF8E24AA),
                            fontSize = 12.sp,
                            onClick = onStoryIntroClick
                        )
                    }
                }

                MainMenuButton(
                    text = "CREDITS & ABOUT",
                    icon = Icons.Default.Info,
                    color = Color(0xFF78909C),
                    onClick = onCreditsClick
                )

                MainMenuButton(
                    text = "EXIT GAME",
                    icon = Icons.Default.ExitToApp,
                    color = Color(0xFFD32F2F),
                    onClick = {
                        (context as? Activity)?.finish()
                    }
                )
            }

            // Clean eco-tip banner at extreme bottom
            Text(
                text = "🌱 \"Protect trees, preserve waterways, rescue habitats.\"",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }
    }
}

@Composable
fun MainMenuButton(
    text: String,
    icon: ImageVector,
    color: Color,
    fontSize: androidx.compose.ui.unit.TextUnit = 14.sp,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = fontSize,
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}

// Data holder representing fluttering leaf state
data class LeafState(
    val startX: Float,
    val startY: Float,
    val weight: Float,
    val rotationSpeed: Float,
    val scale: Float
)

// Helper cubic wave function for natural organic breathing
val SineWaveEasing = Easing { fraction ->
    kotlin.math.sin(fraction * Math.PI.toFloat() * 2f) * 0.5f + 0.5f
}

fun levelToAngle(progress: Float): Float {
    return kotlin.math.sin(progress * Math.PI.toFloat() * 4) * 8f
}

// Particle Leaf drawer helpers
fun androidx.compose.ui.graphics.drawscope.DrawScope.drawLeafParticle(
    centerX: Float,
    centerY: Float,
    rotation: Float,
    scale: Float
) {
    // Beautiful little golden and lime green leaf shapes
    val leafColor = if (scale > 1.0f) Color(0xFFA5D6A7) else Color(0xFFD4E157)
    drawCircle(
        color = leafColor,
        radius = 8f * scale,
        center = Offset(centerX, centerY)
    )
    drawArc(
        color = leafColor.copy(alpha = 0.8f),
        startAngle = rotation,
        sweepAngle = 120f,
        useCenter = true,
        topLeft = Offset(centerX - 12f * scale, centerY - 12f * scale),
        size = Size(24f * scale, 24f * scale)
    )
}
