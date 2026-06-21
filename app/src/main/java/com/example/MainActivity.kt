package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.data.AppDatabase
import com.example.data.GameRepository
import com.example.ui.AchievementsScreen
import com.example.ui.CreditsScreen
import com.example.ui.EncyclopediaScreen
import com.example.ui.EndingScreen
import com.example.ui.GameViewModel
import com.example.ui.IntroScreen
import com.example.ui.MainMenuScreen
import com.example.ui.MiniGamesScreen
import com.example.ui.Screen
import com.example.ui.SettingsScreen
import com.example.ui.WorldMapScreen
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. Setup Room database singleton & repository
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = GameRepository(database.playerStateDao())

        setContent {
            // Force dynamicColor to false to maintain Evergreen custom nature aesthetic
            MyApplicationTheme(dynamicColor = false) {
                // 2. Initialize Game ViewModel instance
                val viewModel = remember { GameViewModel(repository) }
                val currentScreen by viewModel.currentScreen.collectAsState()
                val playerState by viewModel.playerState.collectAsState()

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    // Animated Routing Switcher
                    AnimatedContent(
                        targetState = currentScreen,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(400)) togetherWith fadeOut(animationSpec = tween(400))
                        },
                        label = "router"
                    ) { screen ->
                        when (screen) {
                            is Screen.Intro -> {
                                IntroScreen(
                                    onSkipIntro = {
                                        viewModel.navigateTo(Screen.MainMenu)
                                    }
                                )
                            }
                            is Screen.MainMenu -> {
                                MainMenuScreen(
                                    onPlayClick = {
                                        viewModel.navigateTo(Screen.WorldMap)
                                    },
                                    onSettingsClick = {
                                        viewModel.navigateTo(Screen.Settings)
                                    },
                                    onAchievementsClick = {
                                        viewModel.navigateTo(Screen.Achievements)
                                    },
                                    onEncyclopediaClick = {
                                        viewModel.navigateTo(Screen.Encyclopedia)
                                    },
                                    onCreditsClick = {
                                        viewModel.navigateTo(Screen.Credits)
                                    },
                                    onStoryIntroClick = {
                                        viewModel.navigateTo(Screen.Intro)
                                    }
                                )
                            }
                            is Screen.WorldMap -> {
                                WorldMapScreen(
                                    playerState = playerState,
                                    onBackToMenu = {
                                        viewModel.navigateTo(Screen.MainMenu)
                                    },
                                    onSelectMission = { id ->
                                        viewModel.navigateTo(Screen.MiniGame(id))
                                    }
                                )
                            }
                            is Screen.Achievements -> {
                                AchievementsScreen(
                                    playerState = playerState,
                                    onBack = {
                                        viewModel.navigateTo(Screen.MainMenu)
                                    }
                                )
                            }
                            is Screen.Encyclopedia -> {
                                EncyclopediaScreen(
                                    playerState = playerState,
                                    onBack = {
                                        viewModel.navigateTo(Screen.MainMenu)
                                    },
                                    onUnlockRequest = { animalId, cost ->
                                        viewModel.unlockAnimal(animalId, cost)
                                    }
                                )
                            }
                            is Screen.Settings -> {
                                SettingsScreen(
                                    playerState = playerState,
                                    onBack = {
                                        viewModel.navigateTo(Screen.MainMenu)
                                    },
                                    onUpdateSettings = { music, sound, lang ->
                                        viewModel.updateSettings(music, sound, lang)
                                    },
                                    onResetProgress = {
                                        viewModel.resetProgress()
                                    }
                                )
                            }
                            is Screen.Credits -> {
                                CreditsScreen(
                                    onBack = {
                                        viewModel.navigateTo(Screen.MainMenu)
                                    }
                                )
                            }
                            is Screen.MiniGame -> {
                                MiniGamesScreen(
                                    missionId = screen.missionId,
                                    onBack = {
                                        viewModel.navigateTo(Screen.WorldMap)
                                    },
                                    onMissionSuccess = { stars, points ->
                                        viewModel.completeMission(screen.missionId, stars, points) {
                                            if (screen.missionId == 10) {
                                                viewModel.navigateTo(Screen.Ending("Completed All!"))
                                            } else {
                                                viewModel.navigateTo(Screen.WorldMap)
                                            }
                                        }
                                    }
                                )
                            }
                            is Screen.Ending -> {
                                EndingScreen(
                                    onContinue = {
                                        viewModel.navigateTo(Screen.MainMenu)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
