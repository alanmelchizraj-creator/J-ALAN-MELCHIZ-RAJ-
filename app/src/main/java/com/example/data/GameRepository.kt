package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GameRepository(private val dao: PlayerStateDao) {

    val playerState: Flow<PlayerState> = dao.getPlayerStateFlow().map { it ?: PlayerState() }

    suspend fun getCurrentState(): PlayerState {
        return dao.getPlayerState() ?: PlayerState()
    }

    suspend fun saveState(state: PlayerState) {
        dao.insertOrUpdateState(state)
    }

    suspend fun resetProgress() {
        dao.clearAll()
        dao.insertOrUpdateState(PlayerState())
    }

    // Unlocks a selected animal with nature points
    suspend fun unlockAnimal(animalId: String, cost: Int): Boolean {
        val current = getCurrentState()
        if (current.naturePoints >= cost) {
            val updated = when (animalId.lowercase()) {
                "tiger" -> current.copy(naturePoints = current.naturePoints - cost, unlockedTiger = true)
                "elephant" -> current.copy(naturePoints = current.naturePoints - cost, unlockedElephant = true)
                "deer" -> current.copy(naturePoints = current.naturePoints - cost, unlockedDeer = true)
                "bear" -> current.copy(naturePoints = current.naturePoints - cost, unlockedBear = true)
                "fox" -> current.copy(naturePoints = current.naturePoints - cost, unlockedFox = true)
                "panda" -> current.copy(naturePoints = current.naturePoints - cost, unlockedPanda = true)
                "wolf" -> current.copy(naturePoints = current.naturePoints - cost, unlockedWolf = true)
                "rabbit" -> current.copy(naturePoints = current.naturePoints - cost, unlockedRabbit = true)
                "eagle" -> current.copy(naturePoints = current.naturePoints - cost, unlockedEagle = true)
                "owl" -> current.copy(naturePoints = current.naturePoints - cost, unlockedOwl = true)
                else -> current
            }
            saveState(updated)
            return true
        }
        return false
    }

    // Handles mission success: adds points + stars, unlocks next mission, checks achievements
    suspend fun completeMission(missionId: Int, starsEarned: Int, pointsEarned: Int) {
        val current = getCurrentState()
        
        // 1. Calculate new stars (only keep max stars achieved)
        val m1Stars = if (missionId == 1) maxOf(starsEarned, current.starsM1) else current.starsM1
        val m2Stars = if (missionId == 2) maxOf(starsEarned, current.starsM2) else current.starsM2
        val m3Stars = if (missionId == 3) maxOf(starsEarned, current.starsM3) else current.starsM3
        val m4Stars = if (missionId == 4) maxOf(starsEarned, current.starsM4) else current.starsM4
        val m5Stars = if (missionId == 5) maxOf(starsEarned, current.starsM5) else current.starsM5
        val m6Stars = if (missionId == 6) maxOf(starsEarned, current.starsM6) else current.starsM6
        val m7Stars = if (missionId == 7) maxOf(starsEarned, current.starsM7) else current.starsM7
        val m8Stars = if (missionId == 8) maxOf(starsEarned, current.starsM8) else current.starsM8
        val m9Stars = if (missionId == 9) maxOf(starsEarned, current.starsM9) else current.starsM9
        val m10Stars = if (missionId == 10) maxOf(starsEarned, current.starsM10) else current.starsM10

        // 2. Mark completed
        val m1Completed = current.completedM1 || (missionId == 1)
        val m2Completed = current.completedM2 || (missionId == 2)
        val m3Completed = current.completedM3 || (missionId == 3)
        val m4Completed = current.completedM4 || (missionId == 4)
        val m5Completed = current.completedM5 || (missionId == 5)
        val m6Completed = current.completedM6 || (missionId == 6)
        val m7Completed = current.completedM7 || (missionId == 7)
        val m8Completed = current.completedM8 || (missionId == 8)
        val m9Completed = current.completedM9 || (missionId == 9)
        val m10Completed = current.completedM10 || (missionId == 10)

        // 3. Increment nature points
        val newPoints = current.naturePoints + pointsEarned

        // 4. Update individual animal unlocks based on specific missions
        val updatedTiger = current.unlockedTiger || (missionId == 7)
        val updatedElephant = current.unlockedElephant || (missionId == 9)
        val updatedBear = current.unlockedBear || (missionId == 2)
        val updatedFox = current.unlockedFox || (missionId == 1)
        val updatedPanda = current.unlockedPanda || (missionId == 3)
        val updatedWolf = current.unlockedWolf || (missionId == 10)
        val updatedEagle = current.unlockedEagle || (missionId == 4)
        val updatedOwl = current.unlockedOwl || (missionId == 8)

        // 5. Evaluate Achievements
        val firstComplete = true // since they completed some mission
        val forestSaver = current.achForestSaver || (missionId == 1 && starsEarned == 3) || (m1Completed && m6Completed)
        val animalFriend = current.achAnimalFriend || m2Completed || m7Completed
        val riverProtector = current.achRiverProtector || m8Completed
        val fireFighter = current.achFireFighter || m5Completed
        val natureHero = current.achNatureHero || (m10Completed)
        val masterGuardian = current.achMasterGuardian || (newPoints >= 1000)
        
        val allCompleted = m1Completed && m2Completed && m3Completed && m4Completed && m5Completed &&
                m6Completed && m7Completed && m8Completed && m9Completed && m10Completed
        val fullCompletion = allCompleted && (m1Stars + m2Stars + m3Stars + m4Stars + m5Stars + m6Stars + m7Stars + m8Stars + m9Stars + m10Stars >= 25)

        val updatedState = current.copy(
            naturePoints = newPoints,
            starsM1 = m1Stars, starsM2 = m2Stars, starsM3 = m3Stars, starsM4 = m4Stars, starsM5 = m5Stars,
            starsM6 = m6Stars, starsM7 = m7Stars, starsM8 = m8Stars, starsM9 = m9Stars, starsM10 = m10Stars,
            completedM1 = m1Completed, completedM2 = m2Completed, completedM3 = m3Completed, completedM4 = m4Completed, completedM5 = m5Completed,
            completedM6 = m6Completed, completedM7 = m7Completed, completedM8 = m8Completed, completedM9 = m9Completed, completedM10 = m10Completed,
            achFirstComplete = firstComplete,
            achForestSaver = forestSaver,
            achAnimalFriend = animalFriend,
            achRiverProtector = riverProtector,
            achFireFighter = fireFighter,
            achNatureHero = natureHero,
            achMasterGuardian = masterGuardian,
            achFullCompletion = fullCompletion,
            unlockedTiger = updatedTiger,
            unlockedElephant = updatedElephant,
            unlockedBear = updatedBear,
            unlockedFox = updatedFox,
            unlockedPanda = updatedPanda,
            unlockedWolf = updatedWolf,
            unlockedEagle = updatedEagle,
            unlockedOwl = updatedOwl
        )

        saveState(updatedState)
    }

    suspend fun updateSettings(musicOn: Boolean, soundOn: Boolean, language: String) {
        val current = getCurrentState()
        val updated = current.copy(
            musicOn = musicOn,
            soundOn = soundOn,
            currentLanguage = language
        )
        saveState(updated)
    }
}
