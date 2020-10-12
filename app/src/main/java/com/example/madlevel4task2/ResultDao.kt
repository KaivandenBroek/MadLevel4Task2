package com.example.madlevel4task2

import androidx.room.*

@Dao
interface ResultDao {

    @Query("SELECT * FROM resultTable")
    suspend fun getAllResults(): List<GameResult>

    @Query("SELECT count(*) FROM resultTable WHERE outcome = 0")
    suspend fun getWins(): Int

    @Query("SELECT count(*) FROM resultTable WHERE outcome = 1")
    suspend fun getLoses(): Int

    @Query("SELECT count(*) FROM resultTable WHERE outcome = 2")
    suspend fun getDraws(): Int

    @Insert
    suspend fun insertResult(gameResult: GameResult)

    @Query("DELETE FROM resultTable")
    suspend fun deleteAllResults()
}