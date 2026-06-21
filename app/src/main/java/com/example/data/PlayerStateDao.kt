package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerStateDao {
    @Query("SELECT * FROM player_state WHERE id = 1 LIMIT 1")
    fun getPlayerStateFlow(): Flow<PlayerState?>

    @Query("SELECT * FROM player_state WHERE id = 1 LIMIT 1")
    suspend fun getPlayerState(): PlayerState?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateState(state: PlayerState)

    @Query("DELETE FROM player_state")
    suspend fun clearAll()
}
