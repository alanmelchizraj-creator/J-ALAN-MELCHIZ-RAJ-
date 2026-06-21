package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_state")
data class PlayerState(
    @PrimaryKey val id: Int = 1,
    val naturePoints: Int = 0,
    
    // Stars earned per mission (0 to 3)
    val starsM1: Int = 0,
    val starsM2: Int = 0,
    val starsM3: Int = 0,
    val starsM4: Int = 0,
    val starsM5: Int = 0,
    val starsM6: Int = 0,
    val starsM7: Int = 0,
    val starsM8: Int = 0,
    val starsM9: Int = 0,
    val starsM10: Int = 0,
    
    // Completed status for story progression
    val completedM1: Boolean = false,
    val completedM2: Boolean = false,
    val completedM3: Boolean = false,
    val completedM4: Boolean = false,
    val completedM5: Boolean = false,
    val completedM6: Boolean = false,
    val completedM7: Boolean = false,
    val completedM8: Boolean = false,
    val completedM9: Boolean = false,
    val completedM10: Boolean = false,
    
    // Achievement statuses
    val achFirstComplete: Boolean = false,
    val achForestSaver: Boolean = false,
    val achAnimalFriend: Boolean = false,
    val achRiverProtector: Boolean = false,
    val achFireFighter: Boolean = false,
    val achNatureHero: Boolean = false,
    val achMasterGuardian: Boolean = false,
    val achFullCompletion: Boolean = false,
    
    // Unlocked encyclopedia animal flags (Buyable with Nature Points or unlocked via missions)
    val unlockedTiger: Boolean = false,
    val unlockedElephant: Boolean = false,
    val unlockedDeer: Boolean = true, // Free starting animal!
    val unlockedBear: Boolean = false,
    val unlockedFox: Boolean = false,
    val unlockedPanda: Boolean = false,
    val unlockedWolf: Boolean = false,
    val unlockedRabbit: Boolean = true, // Free starting animal!
    val unlockedEagle: Boolean = false,
    val unlockedOwl: Boolean = false,
    
    // Wallpaper & Skins unlocked with Nature Points
    val unlockedForestTheme: Boolean = true,
    val unlockedSeaTheme: Boolean = false,
    val unlockedSpaceTheme: Boolean = false,
    
    // Save-settings
    val musicOn: Boolean = true,
    val soundOn: Boolean = true,
    val currentLanguage: String = "English"
)
