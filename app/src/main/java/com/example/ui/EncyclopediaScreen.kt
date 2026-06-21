package com.example.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.example.data.PlayerState

// Animal card detail structure
data class AnimalEntry(
    val id: String,
    val name: String,
    val iconEmoji: String,
    val color: Color,
    val habitat: String,
    val diet: String,
    val funFact: String,
    val unlockSector: String
)

val AllAnimalsList = listOf(
    AnimalEntry("tiger", "Bengal Tiger", "🐯", Color(0xFFFF9800), "Dense Tropical Jungle", "Carnivore (Deer, Boar)", "Tigers have striped skin, not just striped fur! No two tigers have the same exact stripes.", "Unlock via Mission 7"),
    AnimalEntry("elephant", "Asian Elephant", "🐘", Color(0xFF90A4AE), "Dense Grasslands & Forests", "Herbivore (Grasses, Roots)", "Elephants communicate through deep sub-sonic rumbles that travel miles through ground.", "Unlock via Mission 9"),
    AnimalEntry("deer", "Spotted Fawn", "🦌", Color(0xFF8D6E63), "Forests & Meadows", "Herbivore (Twigs, Leaves)", "Newly born deer fawns have white spots which camouflage them on forest floors.", "Unlock: Play for Free!"),
    AnimalEntry("bear", "Brown Bear", "🐻", Color(0xFF5D4037), "River Valleys & Spruce Forests", "Omnivore (Salmon, Berries)", "Bears have an incredible sense of smell, 7 times stronger than a bloodhound!", "Unlock via Mission 2"),
    AnimalEntry("fox", "Red Fox", "🦊", Color(0xFFFF5722), "Woodlands & Forest Edges", "Omnivore (Berries, Insects)", "Red foxes have whiskers on their legs as well as their face to help navigate in dark.", "Unlock via Mission 1"),
    AnimalEntry("panda", "Giant Panda", "🐼", Color(0xFF37474F), "Bamboo Rainforests", "Herbivore (99% Bamboo Shoot)", "A giant panda must consume up to 38kg of bamboo shoots daily to meet energy needs.", "Unlock via Mission 3"),
    AnimalEntry("wolf", "Grey Timberwolf", "🐺", Color(0xFF78909C), "Taiga & Boreal Woods", "Carnivore (Elk, Bison)", "Wolves are highly social pack animals and communicate through complex harmonic howling.", "Unlock via Mission 10"),
    AnimalEntry("rabbit", "Grass Cottontail", "🐰", Color(0xFFECEFF1), "Meadow Glades & Fields", "Herbivore (Clover, Flora)", "A rabbit's big ears help monitor surroundings and dissipate body heat in summers.", "Unlock: Play for Free!"),
    AnimalEntry("eagle", "Golden Eagle", "🦅", Color(0xFFFFD54F), "High Cliffs & Mountain Crags", "Carnivore (Marmots, Hares)", "Eagles can see tiny prey movement up to 2 miles away with high-resolution vision.", "Unlock via Mission 4"),
    AnimalEntry("owl", "Tundra Snowy Owl", "🦉", Color(0xFFE0F7FA), "Taiga Canopy & Pine Trees", "Carnivore (Lemmings, Rodents)", "Owls cannot roll their eyeballs, but they can turn their neck up to 270 degrees!", "Unlock via Mission 8")
)

@Composable
fun EncyclopediaScreen(
    playerState: PlayerState,
    onBack: () -> Unit,
    onUnlockRequest: (String, Int) -> Unit
) {
    var selectedEntry by remember { mutableStateOf<AnimalEntry?>(null) }
    var operationMessage by remember { mutableStateOf("") }

    // Check animal unlocked status from state
    val isUnlocked = { id: String ->
        when (id.lowercase()) {
            "tiger" -> playerState.unlockedTiger
            "elephant" -> playerState.unlockedElephant
            "deer" -> playerState.unlockedDeer
            "bear" -> playerState.unlockedBear
            "fox" -> playerState.unlockedFox
            "panda" -> playerState.unlockedPanda
            "wolf" -> playerState.unlockedWolf
            "rabbit" -> playerState.unlockedRabbit
            "eagle" -> playerState.unlockedEagle
            "owl" -> playerState.unlockedOwl
            else -> false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C1D0E)) // Deep rich velvet green backdrop
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
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
                    text = "Eco-Library",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    fontFamily = FontFamily.Serif
                )
            }

            // Currency badge
            Row(
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = "Points",
                    tint = Color(0xFFFFD54F),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${playerState.naturePoints} NP",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }

        // Subtitle Tip
        Text(
            text = "Unlock details automatically by playing sectors or redeem animal cards with Nature Points!",
            fontSize = 12.sp,
            color = Color(0xFFA5D6A7),
            modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 12.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(AllAnimalsList.size) { index ->
                val entry = AllAnimalsList[index]
                val unlocked = isUnlocked(entry.id)

                AnimalGridCard(
                    entry = entry,
                    unlocked = unlocked,
                    onClick = {
                        if (unlocked) {
                            selectedEntry = entry
                        }
                    },
                    onBuyCard = {
                        if (!unlocked) {
                            if (playerState.naturePoints >= 100) {
                                onUnlockRequest(entry.id, 100)
                                operationMessage = "Successfully unlocked ${entry.name}!"
                            } else {
                                operationMessage = "Insufficient Nature Points (Requires 100 NP)!"
                            }
                        }
                    }
                )
            }
        }
    }

    // Detail Dialog Overlay for open animal card
    selectedEntry?.let { entry ->
        AlertDialog(
            onDismissRequest = { selectedEntry = null },
            confirmButton = {
                TextButton(onClick = { selectedEntry = null }) {
                    Text("CLOSE", color = Color(0xFF81C784), fontWeight = FontWeight.Bold)
                }
            },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(entry.iconEmoji, fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = entry.name,
                        fontWeight = FontWeight.Black,
                        fontSize = 20.sp,
                        color = Color.White,
                        fontFamily = FontFamily.Serif
                    )
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row {
                        Text("🏡 Habitat: ", fontWeight = FontWeight.Bold, color = Color(0xFFFFD54F), fontSize = 14.sp)
                        Text(entry.habitat, color = Color.White, fontSize = 14.sp)
                    }
                    Row {
                        Text("🍎 Diet: ", fontWeight = FontWeight.Bold, color = Color(0xFFFFD54F), fontSize = 14.sp)
                        Text(entry.diet, color = Color.White, fontSize = 14.sp)
                    }
                    Divider(color = Color.DarkGray)
                    Text("🎓 Fun Fact: ", fontWeight = FontWeight.Bold, color = Color(0xFF81C784), fontSize = 14.sp)
                    Text(
                        text = entry.funFact,
                        color = Color.LightGray,
                        lineHeight = 18.sp,
                        fontSize = 13.sp
                    )
                }
            },
            containerColor = Color(0xFF142E17),
            shape = RoundedCornerShape(20.dp)
        )
    }

    // Alert toast for operation updates
    if (operationMessage.isNotEmpty()) {
        LaunchedEffect(operationMessage) {
            delay(2500)
            operationMessage = ""
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Snackbar(
                action = {
                    TextButton(onClick = { operationMessage = "" }) {
                        Text("OK", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                },
                modifier = Modifier.padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                containerColor = Color(0xFF1B5E20)
            ) {
                Text(operationMessage, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun AnimalGridCard(
    entry: AnimalEntry,
    unlocked: Boolean,
    onClick: () -> Unit,
    onBuyCard: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .shadow(2.dp, RoundedCornerShape(18.dp))
            .clickable(onClick = if (unlocked) onClick else ({})),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (unlocked) Color(0xFF142E17) else Color.DarkGray.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Visual circle representing unlocked card details
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(54.dp)
                    .background(
                        color = if (unlocked) entry.color.copy(alpha = 0.2f) else Color.Gray.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                if (unlocked) {
                    Text(entry.iconEmoji, fontSize = 28.sp)
                } else {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Locked Card",
                        tint = Color.White.copy(alpha = 0.3f),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Headline Text info
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = if (unlocked) entry.name else "Mysterious Specie",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = if (unlocked) Color.White else Color.Gray,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = if (unlocked) "Tap to learn details!" else entry.unlockSector,
                    fontSize = 10.sp,
                    color = if (unlocked) Color(0xFFA5D6A7) else Color.LightGray,
                    textAlign = TextAlign.Center
                )
            }

            // Unlock Redeem button action
            if (unlocked) {
                Text(
                    text = "UNLOCKED 📖",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    color = Color(0xFF00E676),
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                )
            } else {
                Button(
                    onClick = onBuyCard,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFB300),
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                    modifier = Modifier.height(26.dp)
                ) {
                    Text(
                        text = "REDEEM: 100 NP",
                        fontWeight = FontWeight.Black,
                        fontSize = 8.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}
