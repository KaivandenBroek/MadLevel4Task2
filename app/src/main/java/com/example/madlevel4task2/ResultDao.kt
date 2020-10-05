package com.example.madlevel4task2

import androidx.room.*

@Dao
interface ResultDao {

    @Query("SELECT * FROM resultTable")
    suspend fun getAllResults(): List<Result>

    @Insert
    suspend fun insertResult(result: Result)

    @Query("DELETE FROM resultTable")
    suspend fun deleteAllResults()

    @Update
    suspend fun updateResult(result: Result)

}