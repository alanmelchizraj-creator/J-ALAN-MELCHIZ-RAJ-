package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PlayerState

@Composable
fun SettingsScreen(
    playerState: PlayerState,
    onBack: () -> Unit,
    onUpdateSettings: (Boolean, Boolean, String) -> Unit,
    onResetProgress: () -> Unit
) {
    var musicEnabled by remember { mutableStateOf(playerState.musicOn) }
    var soundEnabled by remember { mutableStateOf(playerState.soundOn) }
    var selectedLanguage by remember { mutableStateOf(playerState.currentLanguage) }
    var showResetDialog by remember { mutableStateOf(false) }

    LaunchedEffect(playerState) {
        musicEnabled = playerState.musicOn
        soundEnabled = playerState.soundOn
        selectedLanguage = playerState.currentLanguage
    }

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
                text = "Guardian Settings",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
                fontFamily = FontFamily.Serif
            )
        }

        // Settings Content
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Card 1: Sound Adjusters
            Card(
                modifier = Modifier.fillMaxWidth().shadow(2.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF142E17))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Audio Configurations",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFD54F),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Music setting row
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.MusicNote, contentDescription = "Music", tint = Color.White)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Atmosphere Music", color = Color.White, fontSize = 14.sp)
                        }
                        Switch(
                            checked = musicEnabled,
                            onCheckedChange = {
                                musicEnabled = it
                                onUpdateSettings(it, soundEnabled, selectedLanguage)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color(0xFF81C784),
                                checkedTrackColor = Color(0xFF1B5E20)
                            )
                        )
                    }

                    Divider(color = Color.DarkGray.copy(alpha = 0.5f))

                    // Sound setting row
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.VolumeUp, contentDescription = "Sound Effects", tint = Color.White)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Survival Sound FX", color = Color.White, fontSize = 14.sp)
                        }
                        Switch(
                            checked = soundEnabled,
                            onCheckedChange = {
                                soundEnabled = it
                                onUpdateSettings(musicEnabled, it, selectedLanguage)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color(0xFF81C784),
                                checkedTrackColor = Color(0xFF1B5E20)
                            )
                        )
                    }
                }
            }

            // Card 2: Language Selector
            Card(
                modifier = Modifier.fillMaxWidth().shadow(2.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF142E17))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Story Language",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFD54F),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Language, contentDescription = "Language", tint = Color.White)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Current: $selectedLanguage", color = Color.White, fontSize = 14.sp)
                        }
                        
                        // Small language toggle row
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf("English", "Spanish", "French").forEach { lang ->
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = if (selectedLanguage == lang) Color(0xFF4CAF50) else Color.DarkGray,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .clickable {
                                            selectedLanguage = lang
                                            onUpdateSettings(musicEnabled, soundEnabled, lang)
                                        }
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = lang.substring(0, 3).uppercase(),
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Card 3: Danger Zone (Reset progress)
            Card(
                modifier = Modifier.fillMaxWidth().shadow(2.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF142E17))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Guardian Danger Zone",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE53935),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Text(
                        text = "Resetting the game deletes all points, stars, achievements, unlocked animals, and reverts island back to its polluted state. This cannot be undone.",
                        fontSize = 12.sp,
                        color = Color.LightGray,
                        modifier = Modifier.padding(bottom = 16.dp),
                        lineHeight = 16.sp
                    )

                    Button(
                        onClick = { showResetDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935), contentColor = Color.White),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Refresh, contentDescription = "Reset Progress")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("ERASE & RESET DATA", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // About block
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Default.Info, contentDescription = "Game version", tint = Color.Gray, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Call Of Nature v1.0.0 Stable Build", fontSize = 11.sp, color = Color.Gray)
        }
    }

    // Confirmation resetting progress dialog
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        showResetDialog = false
                        onResetProgress()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))
                ) {
                    Text("YES, WIPE ALL DATA", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("NO, GO BACK", color = Color.Gray)
                }
            },
            title = {
                Text("Confirm Wipeout?", fontWeight = FontWeight.Black, color = Color.White, fontFamily = FontFamily.Serif)
            },
            text = {
                Text(
                    "Are you absolutely sure you want to delete your progress? Your Nature Points and unlocked animal cards will be permanently erased.",
                    color = Color.LightGray
                )
            },
            containerColor = Color(0xFF142E17),
            shape = RoundedCornerShape(20.dp)
        )
    }
}
