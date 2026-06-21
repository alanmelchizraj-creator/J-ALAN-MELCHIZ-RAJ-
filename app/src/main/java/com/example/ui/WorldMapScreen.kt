package com.example.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PlayerState

// Description info for the ten missions
data class MissionInfo(
    val id: Int,
    val title: String,
    val story: String,
    val mechanic: String,
    val bgTheme: String
)

val AllMissionsList = listOf(
    MissionInfo(1, "Forest Runner", "Help a deer run and reach its safe woodland preserve habitat.", "Endless Runner Game", "Green Forest"),
    MissionInfo(2, "Baby Animal Maze", "Guide a lost baby bear through a maze back to its mother.", "Maze Puzzle Game", "Forest Maze"),
    MissionInfo(3, "Nature Collection Quest", "Catch falling seeds, fruits, and water - avoid harmful trash!", "Falling Catcher Game", "Meadow"),
    MissionInfo(4, "Protect the Plant", "Guard a rare blooming sprout from ravenous beetles.", "Plant Defense Game", "Garden"),
    MissionInfo(5, "Fire Extinguisher Hero", "Spray water to put out fires and rescue trapped animals.", "Flames Shooter Game", "Burning Forest"),
    MissionInfo(6, "Stop the Loggers", "Capture illegal logging sawmen with a net launcher.", "Logger Tap Game", "Logging Area"),
    MissionInfo(7, "Habitat Match", "Match lost animals to their correct biological habitat.", "Drag/Match Game", "Nature Reserve"),
    MissionInfo(8, "River Cleanup", "Hook toxic plastic trash out of the water but don't snag fish!", "River Cleanup Game", "River"),
    MissionInfo(9, "Wildlife Photographer", "Snap clear photos of rare animals camouflaged in local areas.", "Camera Click Game", "National Park"),
    MissionInfo(10, "Pollution Spirit Showdown", "Unleash all skills to defeat the giant dark toxic boss!", "Boss Battle Game", "Restored Island")
)

@Composable
fun WorldMapScreen(
    playerState: PlayerState,
    onBackToMenu: () -> Unit,
    onSelectMission: (Int) -> Unit
) {
    // Determine active unlocked mission: M(x) is unlocked if prior is completed or it is Mission 1
    val isUnlocked = { id: Int ->
        when (id) {
            1 -> true
            2 -> playerState.completedM1
            3 -> playerState.completedM2
            4 -> playerState.completedM3
            5 -> playerState.completedM4
            6 -> playerState.completedM5
            7 -> playerState.completedM6
            8 -> playerState.completedM7
            9 -> playerState.completedM8
            10 -> playerState.completedM9
            else -> false
        }
    }

    // Stars count per mission helper
    val getStars = { id: Int ->
        when (id) {
            1 -> playerState.starsM1
            2 -> playerState.starsM2
            3 -> playerState.starsM3
            4 -> playerState.starsM4
            5 -> playerState.starsM5
            6 -> playerState.starsM6
            7 -> playerState.starsM7
            8 -> playerState.starsM8
            9 -> playerState.starsM9
            10 -> playerState.starsM10
            else -> 0
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF032211)) // Deep organic terrain backdrop
    ) {
        // Island Map Graphic Canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Render deep ocean water
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFF00ACC1), Color(0xFF006064)),
                    center = Offset(w / 2f, h / 2f),
                    radius = w * 0.9f
                )
            )

            // Dynamic Island contours using multiple drawing layers
            drawCircle(
                color = Color(0xFFC8E6C9), // Sandy beach contour
                radius = w * 0.44f,
                center = Offset(w * 0.5f, h * 0.55f)
            )
            drawCircle(
                color = Color(0xFF4CAF50), // Main island grass
                radius = w * 0.41f,
                center = Offset(w * 0.5f, h * 0.55f)
            )

            // Highlight mountain ranges and internal rivers running through Island
            drawArc(
                color = Color(0xFF0288D1),
                startAngle = -20f,
                sweepAngle = 140f,
                useCenter = false,
                topLeft = Offset(w * 0.25f, h * 0.35f),
                size = Size(w * 0.5f, h * 0.4f)
            )
        }

        // Map Content Layer
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            // Map Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onBackToMenu,
                    modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Main Menu",
                        tint = Color.White
                    )
                }

                // Balance display badge
                Row(
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
                        .padding(horizontal = 14.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = "Nature Points",
                        tint = Color(0xFFFFF176),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${playerState.naturePoints} NP",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            // Mission Node Scroll List
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = "Evergreen Island Map",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        fontFamily = FontFamily.Serif,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Rescue habitats and answer the eco-guardianship mission path sequentially!",
                        fontSize = 12.sp,
                        color = Color(0xFFA5D6A7),
                        lineHeight = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                }

                items(AllMissionsList.size) { index ->
                    val mission = AllMissionsList[index]
                    val unlocked = isUnlocked(mission.id)
                    val stars = getStars(mission.id)

                    MissionNodeItem(
                        missionId = mission.id,
                        title = mission.title,
                        story = mission.story,
                        mechanic = mission.mechanic,
                        unlocked = unlocked,
                        stars = stars,
                        onClick = {
                            if (unlocked) {
                                onSelectMission(mission.id)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MissionNodeItem(
    missionId: Int,
    title: String,
    story: String,
    mechanic: String,
    unlocked: Boolean,
    stars: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = unlocked, onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (unlocked) Color(0xFF142E17).copy(alpha = 0.9f) else Color.DarkGray.copy(alpha = 0.85f),
            contentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Node Level Identifier circle
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(54.dp)
                    .background(
                        color = if (unlocked) Color(0xFF4CAF50) else Color.Gray,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                if (unlocked) {
                    Text(
                        text = "M$missionId",
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Locked",
                        tint = Color.White.copy(alpha = 0.8f)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Body info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (unlocked) title else "Locked Mission $missionId",
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp,
                    color = if (unlocked) Color(0xFFFFD54F) else Color.LightGray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (unlocked) story else "Complete previous environmental tasks to unlock this sector.",
                    fontSize = 12.sp,
                    color = if (unlocked) Color.White.copy(alpha = 0.85f) else Color.Gray,
                    lineHeight = 16.sp,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "🔧 $mechanic",
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = if (unlocked) Color(0xFFA5D6A7) else Color.Gray
                )
                
                // Star ratings indicators block
                if (unlocked) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        repeat(3) { starIndex ->
                            Icon(
                                imageVector = if (starIndex < stars) Icons.Default.Star else Icons.Default.StarBorder,
                                contentDescription = "Star",
                                tint = Color(0xFFFFF176),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
