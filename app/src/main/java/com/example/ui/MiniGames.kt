package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import kotlinx.coroutines.isActive
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun MiniGamesScreen(
    missionId: Int,
    onBack: () -> Unit,
    onMissionSuccess: (stars: Int, points: Int) -> Unit
) {
    val mission = AllMissionsList.firstOrNull { it.id == missionId } ?: AllMissionsList[0]
    var gameStarted by remember { mutableStateOf(false) }
    var showResultScreen by remember { mutableStateOf(false) }
    
    // Result details
    var finalStars by remember { mutableStateOf(1) }
    var finalPoints by remember { mutableStateOf(50) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C1D0E)) // Deep rich velvet green backdrop
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        if (!gameStarted) {
            // ------------------ MISSION BREIFING HEADER ------------------
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Header details
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Map",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Sector Protection M$missionId",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFD54F),
                        fontFamily = FontFamily.Monospace
                    )
                }

                // Mission info card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(24.dp)),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF142E17))
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "MISSION ${mission.id}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFFFFD54F)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = mission.title,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            fontFamily = FontFamily.Serif,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "Story Outline:",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF81C784),
                            fontSize = 14.sp
                        )
                        Text(
                            text = mission.story,
                            fontSize = 15.sp,
                            color = Color.LightGray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 4.dp),
                            lineHeight = 20.sp
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Protective Mechanic:",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF81C784),
                            fontSize = 14.sp
                        )
                        Text(
                            text = "This sector requires a ${mission.mechanic}. Take action below!",
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.9f),
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily.SansSerif
                        )
                    }
                }

                // Launch button
                Button(
                    onClick = { gameStarted = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.PlayArrow, contentDescription = "Start")
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("START FIELD MISSION", fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else if (showResultScreen) {
            // ------------------ MISSION REWARD OUTCOME ------------------
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "SECTOR SECURED!",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFFFFD54F),
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Congratulations, Guardian! Evergreen Island is restoring.",
                    fontSize = 14.sp,
                    color = Color.LightGray,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Stars Display
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    repeat(3) { index ->
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star",
                            tint = if (index < finalStars) Color(0xFFFFD54F) else Color.DarkGray,
                            modifier = Modifier.size(54.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Points earned Display
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF142E17)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.EmojiEvents, contentDescription = "Reward", tint = Color(0xFFFFD54F))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "+$finalPoints NATURE POINTS",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.White,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))

                Button(
                    onClick = {
                        onMissionSuccess(finalStars, finalPoints)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                ) {
                    Text("CLAIM REWARDS & GO MAP", fontWeight = FontWeight.Bold)
                }
            }
        } else {
            // ------------------ DIRECT INTERACTIVE GAME SELECTION ------------------
            Box(modifier = Modifier.fillMaxSize()) {
                when (missionId) {
                    1 -> ForestRunnerGame(onFinished = { s, p -> finalStars = s; finalPoints = p; showResultScreen = true })
                    2 -> BabyAnimalMazeGame(onFinished = { s, p -> finalStars = s; finalPoints = p; showResultScreen = true })
                    3 -> NatureCollectionQuestGame(onFinished = { s, p -> finalStars = s; finalPoints = p; showResultScreen = true })
                    4 -> ProtectPlantGame(onFinished = { s, p -> finalStars = s; finalPoints = p; showResultScreen = true })
                    5 -> FireExtinguisherGame(onFinished = { s, p -> finalStars = s; finalPoints = p; showResultScreen = true })
                    6 -> StopTheLoggersGame(onFinished = { s, p -> finalStars = s; finalPoints = p; showResultScreen = true })
                    7 -> HabitatMatchGame(onFinished = { s, p -> finalStars = s; finalPoints = p; showResultScreen = true })
                    8 -> RiverCleanupGame(onFinished = { s, p -> finalStars = s; finalPoints = p; showResultScreen = true })
                    9 -> WildlifePhotographerGame(onFinished = { s, p -> finalStars = s; finalPoints = p; showResultScreen = true })
                    10 -> FinalShowdownGame(onFinished = { s, p -> finalStars = s; finalPoints = p; showResultScreen = true })
                    else -> {
                        Button(onClick = { showResultScreen = true }) { Text("Simulate Success") }
                    }
                }
            }
        }
    }
}

// ==========================================
// MISSION 1: FOREST RUNNER GAME
// ==========================================
@Composable
fun ForestRunnerGame(onFinished: (stars: Int, points: Int) -> Unit) {
    var deerY by remember { mutableStateOf(0f) }
    var deerVelocityY by remember { mutableStateOf(0f) }
    var distanceTravelled by remember { mutableStateOf(0f) }
    var lives by remember { mutableStateOf(3) }
    var leavesCollected by remember { mutableStateOf(0) }
    
    // Obscuring log drifting offset
    var logX by remember { mutableStateOf(800f) }
    var logPassed by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (isActive && distanceTravelled < 100f && lives > 0) {
            delay(30)
            distanceTravelled += 0.5f

            // Drifting log from right
            logX -= 12f
            if (logX < -100f) {
                logX = 1000f
                logPassed = false
            }

            // Gravity simulation for Jump
            if (deerY < 0f) {
                deerVelocityY += 1.8f
                deerY += deerVelocityY
                if (deerY >= 0f) {
                    deerY = 0f
                    deerVelocityY = 0f
                }
            }

            // Simple collision check: if log overlaps deer position
            if (logX in 120f..180f && deerY > -80f && !logPassed) {
                logPassed = true
                lives--
            }
        }

        // Handle end
        if (lives <= 0) {
            // Re-simulate pass but penalty
            onFinished(1, 30)
        } else {
            val starsCalculated = if (lives == 3) 3 else if (lives == 2) 2 else 1
            onFinished(starsCalculated, 100 + leavesCollected * 10)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                if (deerY == 0f) {
                    // Trigger Jump upward
                    deerVelocityY = -24f
                    deerY -= 5f
                }
            }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Status Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                repeat(3) { idx ->
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Live",
                        tint = if (idx < lives) Color.Red else Color.DarkGray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Text("Distance: ${distanceTravelled.toInt()}% / 100%", color = Color.White, fontWeight = FontWeight.Bold)
        }

        // Active runner canvas frame
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF33691E))
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height

                // Base level line
                val groundY = h * 0.7f

                // Render ground
                drawRect(
                    color = Color(0xFF1B5E20),
                    topLeft = Offset(0f, groundY),
                    size = Size(w, h - groundY)
                )

                // Render Jumping deer helper
                val finalDeerY = groundY + deerY
                drawRect(
                    color = Color(0xFFFFCC80), // Deer orange body
                    topLeft = Offset(130f, finalDeerY - 60f),
                    size = Size(60f, 60f)
                )
                // Head
                drawCircle(Color(0xFFE0F7FA), 12f, Offset(160f, finalDeerY - 72f))

                // Obstacle log
                drawRect(
                    color = Color(0xFF8D6E63), // Brown logs
                    topLeft = Offset(logX, groundY - 45f),
                    size = Size(50f, 45f)
                )
            }
        }

        // Controls hint text
        Text(
            text = "TAP SCREEN TO JUMP OVER LOGS!",
            color = Color(0xFFFFD54F),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(12.dp),
            textAlign = TextAlign.Center
        )
    }
}

// ==========================================
// MISSION 2: BABY ANIMAL MAZE GAME
// ==========================================
@Composable
fun BabyAnimalMazeGame(onFinished: (stars: Int, points: Int) -> Unit) {
    // 0 = path, 1 = Wall (bush)
    val mazeLayout = listOf(
        listOf(0, 0, 1, 0, 0, 0),
        listOf(1, 0, 1, 0, 1, 0),
        listOf(0, 0, 0, 0, 1, 0),
        listOf(0, 1, 1, 1, 1, 0),
        listOf(0, 0, 1, 0, 0, 0),
        listOf(1, 0, 0, 0, 1, 0)
    )

    var pX by remember { mutableStateOf(0) }
    var pY by remember { mutableStateOf(0) }
    val endX = 5
    val endY = 5
    var movesTaken by remember { mutableStateOf(0) }

    fun tryMove(dx: Int, dy: Int) {
        val nextX = pX + dx
        val nextY = pY + dy
        if (nextX in 0..5 && nextY in 0..5) {
            if (mazeLayout[nextY][nextX] == 0) {
                pX = nextX
                pY = nextY
                movesTaken++

                // Game finish check
                if (pX == endX && pY == endY) {
                    val stars = if (movesTaken < 15) 3 else if (movesTaken < 22) 2 else 1
                    onFinished(stars, 120)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Steps Taken: $movesTaken", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)

        // Render Maze Grid Frame
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF003300))
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Column {
                for (y in 0..5) {
                    Row {
                        for (x in 0..5) {
                            val isWall = mazeLayout[y][x] == 1
                            val isPlayer = pX == x && pY == y
                            val isGoal = endX == x && endY == y

                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .padding(2.dp)
                                    .background(
                                        color = when {
                                            isPlayer -> Color(0xFFFF8A65)
                                            isGoal -> Color(0xFF81C784)
                                            isWall -> Color(0xFF33691E)
                                            else -> Color(0xFF1B5E20)
                                        },
                                        shape = RoundedCornerShape(8.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = when {
                                        isPlayer -> "🐻"
                                        isGoal -> "🏡"
                                        isWall -> "🌳"
                                        else -> ""
                                    },
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Direct D-Pad navigation controls
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            Button(onClick = { tryMove(0, -1) }, modifier = Modifier.size(54.dp)) {
                Text("▲", fontWeight = FontWeight.Black)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(vertical = 4.dp)) {
                Button(onClick = { tryMove(-1, 0) }, modifier = Modifier.size(54.dp)) {
                    Text("◀", fontWeight = FontWeight.Black)
                }
                Spacer(modifier = Modifier.width(36.dp))
                Button(onClick = { tryMove(1, 0) }, modifier = Modifier.size(54.dp)) {
                    Text("▶", fontWeight = FontWeight.Black)
                }
            }
            Button(onClick = { tryMove(0, 1) }, modifier = Modifier.size(54.dp)) {
                Text("▼", fontWeight = FontWeight.Black)
            }
        }
    }
}

// ==========================================
// MISSION 3: NATURE COLLECTION QUEST GAME
// ==========================================
@Composable
fun NatureCollectionQuestGame(onFinished: (stars: Int, points: Int) -> Unit) {
    var basketX by remember { mutableStateOf(300f) }
    var score by remember { mutableStateOf(0) }
    var badTrashPenalty by remember { mutableStateOf(0) }
    
    // Falling elements: x speed, x coordinate, y coordinate, category (0=good, 1=bad)
    var f1X by remember { mutableStateOf(200f) }
    var f1Y by remember { mutableStateOf(0f) }
    var f1Symbol by remember { mutableStateOf("🌱") }
    var f1Category by remember { mutableStateOf(0) } // Good

    var f2X by remember { mutableStateOf(450f) }
    var f2Y by remember { mutableStateOf(-200f) }
    var f2Symbol by remember { mutableStateOf("🗑️") }
    var f2Category by remember { mutableStateOf(1) } // Bad (trash)

    LaunchedEffect(Unit) {
        while (isActive && score < 100 && badTrashPenalty < 3) {
            delay(30)
            
            // Increment falling offset
            f1Y += 10f
            if (f1Y > 900f) {
                // Check catch by basket
                if (kotlin.math.abs(f1X - basketX) < 130f) {
                    score += 20
                }
                // Recycle item
                f1Y = -50f
                f1X = Random.nextFloat() * 600f
                f1Symbol = listOf("🌱", "🍎", "💧", "🌸").random()
            }

            f2Y += 12f
            if (f2Y > 900f) {
                // Check penalty hit
                if (kotlin.math.abs(f2X - basketX) < 130f) {
                    badTrashPenalty++
                }
                f2Y = -150f
                f2X = Random.nextFloat() * 600f
            }
        }

        // Complete checks
        val stars = if (badTrashPenalty == 0) 3 else if (badTrashPenalty == 1) 2 else 1
        onFinished(stars, score + 40)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Score: $score / 100", color = Color.White, fontWeight = FontWeight.Bold)
            Text("Damage: $badTrashPenalty / 3", color = Color.Red, fontWeight = FontWeight.Bold)
        }

        // Quest game area canvas
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF81C784))
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val h = size.height

                // Draw falling element 1
                drawCircle(Color.White.copy(alpha = 0.5f), 15f, Offset(f1X, f1Y))
                // Render text for visual fallback symbol check
                
                // Draw falling trash element 2
                drawCircle(Color(0xFFE53935).copy(alpha = 0.4f), 20f, Offset(f2X, f2Y))

                // Render basket
                drawRect(
                    color = Color(0xFF5D4037), // Basket
                    topLeft = Offset(basketX - 60f, h * 0.8f),
                    size = Size(120f, 30f)
                )
            }
            // Overlay text characters
            Text(f1Symbol, fontSize = 28.sp, modifier = Modifier.offset(f1X.dp * 0.6f, f1Y.dp * 0.6f))
            Text("🗑️", fontSize = 28.sp, modifier = Modifier.offset(f2X.dp * 0.6f, f2Y.dp * 0.6f))
        }

        // Steer left right controls
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { if (basketX > 100f) basketX -= 60f }) {
                Icon(Icons.Default.ChevronLeft, contentDescription = "Left")
                Text("MOVE LEFT")
            }
            Button(onClick = { if (basketX < 600f) basketX += 60f }) {
                Text("MOVE RIGHT")
                Icon(Icons.Default.ChevronRight, contentDescription = "Right")
            }
        }
    }
}

// ==========================================
// MISSION 4: PROTECT THE PLANT GAME
// ==========================================
@Composable
fun ProtectPlantGame(onFinished: (stars: Int, points: Int) -> Unit) {
    var plantHealth by remember { mutableStateOf(100) }
    var bugExterminations by remember { mutableStateOf(0) }

    // Bugs offset coordinates
    var bug1X by remember { mutableStateOf(100f) }
    var bug1Y by remember { mutableStateOf(100f) }
    var bug2X by remember { mutableStateOf(500f) }
    var bug2Y by remember { mutableStateOf(600f) }

    LaunchedEffect(Unit) {
        while (isActive && bugExterminations < 10 && plantHealth > 0) {
            delay(100)

            // Bug 1 moves towards center (300, 400)
            val dx1 = 300f - bug1X
            val dy1 = 400f - bug1Y
            val d1 = kotlin.math.sqrt(dx1*dx1 + dy1*dy1)
            if (d1 > 10f) {
                bug1X += (dx1 / d1) * 8f
                bug1Y += (dy1 / d1) * 8f
            } else {
                // Bug bites plant!
                plantHealth -= 15
                bug1X = Random.nextFloat() * 600f
                bug1Y = Random.nextFloat() * 200f
            }

            // Bug 2 moves
            val dx2 = 300f - bug2X
            val dy2 = 400f - bug2Y
            val d2 = kotlin.math.sqrt(dx2*dx2 + dy2*dy2)
            if (d2 > 10f) {
                bug2X += (dx2 / d2) * 9f
                bug2Y += (dy2 / d2) * 9f
            } else {
                plantHealth -= 15
                bug2X = Random.nextFloat() * 600f
                bug2Y = Random.nextFloat() * 800f
            }
        }

        // Outcome logic
        val stars = if (plantHealth > 75) 3 else if (plantHealth > 40) 2 else 1
        onFinished(stars, 140)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Bugs Squashed: $bugExterminations / 10", color = Color.White, fontWeight = FontWeight.Bold)
            Text("Plant Health: $plantHealth%", color = if (plantHealth > 50) Color.Green else Color.Red, fontWeight = FontWeight.Bold)
        }

        // Active tap squashing arena
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFFFECB3))
        ) {
            // Main Sprout in Center coordinates 300, 400
            Text("🌱", fontSize = 64.sp, modifier = Modifier.align(Alignment.Center))

            // The clickable bugs overlays
            Text(
                text = "🐛",
                fontSize = 32.sp,
                modifier = Modifier
                    .offset(bug1X.dp * 0.6f, bug1Y.dp * 0.6f)
                    .clickable {
                        bugExterminations++
                        bug1X = Random.nextFloat() * 600f
                        bug1Y = Random.nextFloat() * 200f
                    }
            )

            Text(
                text = "🕷️",
                fontSize = 32.sp,
                modifier = Modifier
                    .offset(bug2X.dp * 0.6f, bug2Y.dp * 0.6f)
                    .clickable {
                        bugExterminations++
                        bug2X = Random.nextFloat() * 600f
                        bug2Y = Random.nextFloat() * 800f
                    }
            )
        }

        Text(
            text = "TAP ON REPTILE INSECTS TO DEFEND THE SPROUT!",
            color = Color(0xFFFFD54F),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(12.dp),
            textAlign = TextAlign.Center
        )
    }
}

// ==========================================
// MISSION 5: FIRE EXTINGUISHER GAME
// ==========================================
@Composable
fun FireExtinguisherGame(onFinished: (stars: Int, points: Int) -> Unit) {
    var waterSupply by remember { mutableStateOf(100) }
    var activeFiresCount by remember { mutableStateOf(5) }
    
    // Fires coordinates list
    val firesList = remember {
        mutableStateListOf(
            FirePoint(x = 120f, y = 150f, size = 1f, id = 1),
            FirePoint(x = 420f, y = 250f, size = 1.2f, id = 2),
            FirePoint(x = 220f, y = 450f, size = 0.9f, id = 3),
            FirePoint(x = 350f, y = 600f, size = 1.1f, id = 4),
            FirePoint(x = 100f, y = 700f, size = 1.3f, id = 5)
        )
    }

    LaunchedEffect(firesList.size) {
        if (firesList.isEmpty()) {
            val stars = if (waterSupply > 50) 3 else 2
            onFinished(stars, 150)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Active Fires: ${firesList.size}", color = Color.White, fontWeight = FontWeight.Bold)
            Text("Water Pressure: $waterSupply%", color = Color(0xFF29B6F6), fontWeight = FontWeight.Bold)
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF3E2723)) // Devastated forest
        ) {
            Text("🌲", fontSize = 48.sp, modifier = Modifier.align(Alignment.CenterStart).offset(30.dp, (-50).dp))
            Text("🌳", fontSize = 48.sp, modifier = Modifier.align(Alignment.CenterEnd).offset((-30).dp, 50.dp))

            // Spray fire items
            firesList.forEach { fire ->
                Text(
                    text = "🔥",
                    fontSize = (32 * fire.size).sp,
                    modifier = Modifier
                        .offset(fire.x.dp * 0.6f, fire.y.dp * 0.6f)
                        .clickable {
                            waterSupply -= 4
                            firesList.remove(fire)
                        }
                )
            }
        }

        Text(
            text = "TAP FLAMES TO SPRAY WATER & SAVE SECTOR!",
            color = Color(0xFFFFD54F),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(12.dp)
        )
    }
}
data class FirePoint(val x: Float, val y: Float, val size: Float, val id: Int)

// ==========================================
// MISSION 6: STOP THE LOGGERS GAME
// ==========================================
@Composable
fun StopTheLoggersGame(onFinished: (stars: Int, points: Int) -> Unit) {
    var loggersCaptured by remember { mutableStateOf(0) }
    var timeSecondsLeft by remember { mutableStateOf(15) }

    // Floating logger coordinates
    var lx by remember { mutableStateOf(200f) }
    var ly by remember { mutableStateOf(300f) }

    LaunchedEffect(Unit) {
        while (isActive && timeSecondsLeft > 0 && loggersCaptured < 5) {
            delay(1000)
            timeSecondsLeft--
        }
        
        if (loggersCaptured >= 5) {
            onFinished(3, 160)
        } else {
            onFinished(1, 60)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Captures: $loggersCaptured / 5", color = Color.White, fontWeight = FontWeight.Bold)
            Text("Defending Timer: ${timeSecondsLeft}s", color = Color(0xFFFFD54F), fontWeight = FontWeight.Bold)
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF558B2F)) // Wooden logging area
        ) {
            Text("🪵", fontSize = 42.sp, modifier = Modifier.offset(100.dp, 100.dp))
            Text("🪓", fontSize = 42.sp, modifier = Modifier.offset(400.dp, 600.dp))

            // The moving outlaw logger
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1B5E20)),
                modifier = Modifier
                    .offset(lx.dp * 0.6f, ly.dp * 0.6f)
                    .clickable {
                        loggersCaptured++
                        lx = Random.nextFloat() * 500f + 50f
                        ly = Random.nextFloat() * 600f + 50f
                    },
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("🧔🪚", fontSize = 28.sp)
                }
            }
        }

        Text(
            text = "TAP ILLEGAL TIMBER LOGGERS TO CRACKDOWN!",
            color = Color(0xFFFFD54F),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(12.dp)
        )
    }
}

// ==========================================
// MISSION 7: HABITAT MATCH GAME
// ==========================================
@Composable
fun HabitatMatchGame(onFinished: (stars: Int, points: Int) -> Unit) {
    val animalCard = remember { "🐟" } // Fish
    val habitats = listOf("🌊 River Stream", "🏜️ Desert Dune", "🌲 Deep Spruce Forest")
    var feedbackMessage by remember { mutableStateOf("Match the Trout fish to its proper habitat!") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Habitat Identification Match",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontFamily = FontFamily.Serif
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Large animal emoji card
        Card(
            modifier = Modifier.size(120.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF142E17)),
            shape = RoundedCornerShape(24.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(animalCard, fontSize = 64.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = feedbackMessage,
            color = Color(0xFFFFD54F),
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Selection buttons
        Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            habitats.forEach { habitat ->
                Button(
                    onClick = {
                        if (habitat.contains("River")) {
                            onFinished(3, 170)
                        } else {
                            feedbackMessage = "Incorrect Match. Try again!"
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20))
                ) {
                    Text(habitat, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

// ==========================================
// MISSION 8: RIVER CLEANUP GAME
// ==========================================
@Composable
fun RiverCleanupGame(onFinished: (stars: Int, points: Int) -> Unit) {
    var trashRecoveredCount by remember { mutableStateOf(0) }
    var scorePoints by remember { mutableStateOf(0) }

    // Floating trash and fish offsets
    var trash1X by remember { mutableStateOf(100f) }
    var trash2X by remember { mutableStateOf(400f) }
    var fishX by remember { mutableStateOf(200f) }

    LaunchedEffect(trashRecoveredCount) {
        if (trashRecoveredCount >= 8) {
            onFinished(3, 180)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Purified Debris: $trashRecoveredCount / 8", color = Color.White, fontWeight = FontWeight.Bold)
            Text("Biodiversity Score: $scorePoints", color = Color(0xFF81C784), fontWeight = FontWeight.Bold)
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF0288D1)) // Immersive flowing Blue River
        ) {
            // Floating plastic debris card clickable
            Text(
                text = "🍼",
                fontSize = 32.sp,
                modifier = Modifier
                    .offset(trash1X.dp * 0.6f, 150.dp)
                    .clickable {
                        trashRecoveredCount++
                        scorePoints += 10
                        trash1X = Random.nextFloat() * 500f + 20f
                    }
            )

            Text(
                text = "🥫",
                fontSize = 32.sp,
                modifier = Modifier
                    .offset(trash2X.dp * 0.6f, 400.dp)
                    .clickable {
                        trashRecoveredCount++
                        scorePoints += 10
                        trash2X = Random.nextFloat() * 500f + 20f
                    }
            )

            // Dynamic swimming fish (DON'T TAP PENALTY!)
            Text(
                text = "🐟",
                fontSize = 32.sp,
                modifier = Modifier
                    .offset(fishX.dp * 0.6f, 280.dp)
                    .clickable {
                        scorePoints -= 15
                        fishX = Random.nextFloat() * 500f + 20f
                    }
            )
        }

        Text(
            text = "COLLECT BOTTLES & CANS. DO NOT CLICK ON FISH!",
            color = Color(0xFFFFD54F),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(12.dp)
        )
    }
}

// ==========================================
// MISSION 9: WILDLIFE PHOTOGRAPHER GAME
// ==========================================
@Composable
fun WildlifePhotographerGame(onFinished: (stars: Int, points: Int) -> Unit) {
    var snapCount by remember { mutableStateOf(0) }
    var forestMovement by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (isActive && snapCount < 3) {
            delay(40)
            forestMovement += 4f
            if (forestMovement > 700f) {
                forestMovement = -100f
            }
        }
        onFinished(3, 190)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Documentation: $snapCount / 3 snaps", color = Color.White, fontWeight = FontWeight.Bold)
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF33691E))
        ) {
            // Target Camera Focus Zone in Center
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.Center)
                    .shadow(1.dp, RoundedCornerShape(100))
                    .background(Color.White.copy(alpha = 0.15f))
            ) {
                Icon(
                    imageVector = Icons.Default.Block,
                    contentDescription = "Target Target",
                    tint = Color.Red,
                    modifier = Modifier.size(24.dp).align(Alignment.Center)
                )
            }

            // Flying bird drifting along X lines
            Text(
                text = "🦅",
                fontSize = 42.sp,
                modifier = Modifier
                    .offset(forestMovement.dp * 0.6f, 250.dp)
            )
        }

        Button(
            onClick = {
                // If bird is currently inside shutter zone (aligned around center 200..400)
                if (forestMovement in 200f..400f) {
                    snapCount++
                    forestMovement = -150f // Reset
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
            shape = RoundedCornerShape(50),
            modifier = Modifier.size(72.dp)
        ) {
            Text("📷", fontSize = 24.sp)
        }
    }
}

// ==========================================
// MISSION 10: FINAL SHOWDOWN GAME (BOSS BATTLE)
// ==========================================
@Composable
fun FinalShowdownGame(onFinished: (stars: Int, points: Int) -> Unit) {
    var bossHP by remember { mutableStateOf(100) }
    var shieldHealth by remember { mutableStateOf(100) }

    var sludgeX by remember { mutableStateOf(200f) }
    var sludgeY by remember { mutableStateOf(50f) }

    LaunchedEffect(Unit) {
        while (isActive && bossHP > 0 && shieldHealth > 0) {
            delay(80)
            sludgeY += 14f
            if (sludgeY > 800f) {
                shieldHealth -= 10
                sludgeY = 50f
                sludgeX = Random.nextFloat() * 500f + 20f
            }
        }

        if (bossHP <= 0) {
            onFinished(3, 250)
        } else {
            onFinished(1, 80)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Boss HP: $bossHP%", color = Color.Red, fontWeight = FontWeight.Bold)
            Text("Your Shield: $shieldHealth%", color = Color.Green, fontWeight = FontWeight.Bold)
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF212121)) // Destroyed smoky backdrop
        ) {
            // Giant Boss Monster floats at top
            Column(
                modifier = Modifier.align(Alignment.TopCenter).padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("👾", fontSize = 72.sp)
                Text("POLLUTION SPIRIT", fontWeight = FontWeight.Black, color = Color.White, fontSize = 12.sp)
            }

            // Dripping sludge drop
            Text("☣️", fontSize = 32.sp, modifier = Modifier.offset(sludgeX.dp * 0.6f, sludgeY.dp * 0.6f))
        }

        Button(
            onClick = {
                bossHP -= 8
                // Vaporize sludge occasionally
                if (sludgeY > 400f) {
                    sludgeY = 50f
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8F00)),
            modifier = Modifier.fillMaxWidth().height(54.dp)
        ) {
            Text("RELEASE NATURE SPARKS ☄️", fontWeight = FontWeight.Black, fontSize = 16.sp)
        }
    }
}
