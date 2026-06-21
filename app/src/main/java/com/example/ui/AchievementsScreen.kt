package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PlayerState

data class AchievementInfo(
    val id: String,
    val title: String,
    val description: String,
    val condition: String,
    val unlocked: Boolean,
    val color: Color
)

@Composable
fun AchievementsScreen(
    playerState: PlayerState,
    onBack: () -> Unit
) {
    val list = listOf(
        AchievementInfo(
            "first", "First Mission Complete", "Take your very first brave step as Guardian of Evergreen Island.", "Complete any mission",
            playerState.achFirstComplete, Color(0xFF4CAF50)
        ),
        AchievementInfo(
            "forest", "Forest Saver", "Bravely protect the ancient forest from runaways and loggers.", "Complete Mission 1 and 6",
            playerState.achForestSaver, Color(0xFF00C853)
        ),
        AchievementInfo(
            "animal", "Animal Friend", "Help reunite separated animal families and match them to proper homes.", "Reunite or Match baby animals",
            playerState.achAnimalFriend, Color(0xFFFF9100)
        ),
        AchievementInfo(
            "river", "River Protector", "Exhaust plastic debris from flowing streams to purify the ecosystem.", "Complete Mission 8 River Cleanup",
            playerState.achRiverProtector, Color(0xFF29B6F6)
        ),
        AchievementInfo(
            "fire", "Fire Fighter", "Suppress forest blazes to preserve lives of local flora and creatures.", "Complete Mission 5",
            playerState.achFireFighter, Color(0xFFFF3D00)
        ),
        AchievementInfo(
            "hero", "Nature Hero", "Put down the toxic Dark Pollution Spirit's siege of Evergreen.", "Defeat Final Boss in Mission 10",
            playerState.achNatureHero, Color(0xFFE040FB)
        ),
        AchievementInfo(
            "master", "Master Guardian", "Gain recognition as an ultimate legendary eco-guardian.", "Earn 1000 Nature Points",
            playerState.achMasterGuardian, Color(0xFFFFD54F)
        ),
        AchievementInfo(
            "complete", "100% Completion", "Perfect all environmental sectors on Evergreen with legendary skill.", "Clear all missions with 25+ Stars",
            playerState.achFullCompletion, Color(0xFF00E676)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C1D0E)) // Deep rich velvet green backdrop
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Menu",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Guardian Medals",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
                fontFamily = FontFamily.Serif
            )
        }

        // Stats card summary
        val unlockedCount = list.count { it.unlocked }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF142E17))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Achievements Progress",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFD54F),
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$unlockedCount / ${list.size}",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Medals Active",
                        fontSize = 14.sp,
                        color = Color.LightGray
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                LinearProgressIndicator(
                    progress = { unlockedCount.toFloat() / list.size.toFloat() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp),
                    color = Color(0xFF81C784),
                    trackColor = Color.DarkGray
                )
            }
        }

        // Vertical custom Grid list
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(list.size) { index ->
                val ach = list[index]
                AchievementGridCard(ach)
            }
        }
    }
}

@Composable
fun AchievementGridCard(ach: AchievementInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .shadow(2.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (ach.unlocked) Color(0xFF1B5E20).copy(alpha = 0.9f) else Color.DarkGray.copy(alpha = 0.6f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Badge icon circular
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(54.dp)
                    .background(
                        brush = if (ach.unlocked) Brush.radialGradient(colors = listOf(ach.color, ach.color.copy(alpha = 0.5f))) else Brush.linearGradient(colors = listOf(Color.Gray, Color.DarkGray)),
                        shape = RoundedCornerShape(50)
                    )
            ) {
                if (ach.unlocked) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = "Unlocked Badge",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Locked Badge",
                        tint = Color.White.copy(alpha = 0.5f),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Description block
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = ach.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = if (ach.unlocked) Color(0xFFFFD54F) else Color.Gray,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    lineHeight = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (ach.unlocked) ach.description else ach.condition,
                    fontSize = 10.sp,
                    color = Color.LightGray,
                    textAlign = TextAlign.Center,
                    lineHeight = 12.sp,
                    maxLines = 3
                )
            }

            // Status Badge
            Text(
                text = if (ach.unlocked) "UNLOCKED" else "LOCKED",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                color = if (ach.unlocked) Color(0xFF00E676) else Color.Gray,
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            )
        }
    }
}
