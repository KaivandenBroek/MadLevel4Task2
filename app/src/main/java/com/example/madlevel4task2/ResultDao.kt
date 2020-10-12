package com.example.madlevel4task2

import androidx.room.*

@Dao
interface ResultDao {

    @Query("SELECT * FROM resultTable")
    suspend fun getAllResults(): List<GameResult>

    @Insert
    suspend fun insertResult(gameResult: GameResult)

    @Query("DELETE FROM resultTable")
    suspend fun deleteAllResults()

    @Update
    suspend fun updateResult(gameResult: GameResult)

}