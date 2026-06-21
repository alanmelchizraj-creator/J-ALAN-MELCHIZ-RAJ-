package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.GameRepository
import com.example.data.PlayerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface Screen {
    object Intro : Screen
    object MainMenu : Screen
    object WorldMap : Screen
    object Achievements : Screen
    object Encyclopedia : Screen
    object Settings : Screen
    object Credits : Screen
    data class MiniGame(val missionId: Int) : Screen
    data class Ending(val endText: String) : Screen
}

class GameViewModel(private val repository: GameRepository) : ViewModel() {

    // Reactive state from database
    val playerState: StateFlow<PlayerState> = repository.playerState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PlayerState()
        )

    // UI screen routing state
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Intro)
    val currentScreen: StateFlow<Screen> = _currentScreen

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
    }

    fun unlockAnimal(animalId: String, cost: Int, onSuccess: () -> Unit = {}, onFailure: () -> Unit = {}) {
        viewModelScope.launch {
            val success = repository.unlockAnimal(animalId, cost)
            if (success) {
                onSuccess()
            } else {
                onFailure()
            }
        }
    }

    fun completeMission(missionId: Int, stars: Int, points: Int, onComplete: () -> Unit = {}) {
        viewModelScope.launch {
            repository.completeMission(missionId, stars, points)
            onComplete()
        }
    }

    fun updateSettings(musicOn: Boolean, soundOn: Boolean, language: String) {
        viewModelScope.launch {
            repository.updateSettings(musicOn, soundOn, language)
        }
    }

    fun resetProgress() {
        viewModelScope.launch {
            repository.resetProgress()
            _currentScreen.value = Screen.MainMenu
        }
    }
}
