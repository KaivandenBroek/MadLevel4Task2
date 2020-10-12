package com.example.madlevel4task2.database

import android.content.Context
import com.example.madlevel4task2.model.GameResult

class ResultRepository(context: Context) {

    private var resultDao: ResultDao?

    init {
        val resultRoomDatabase = ResultRoomDatabase.getDatabase(context)
        resultDao = resultRoomDatabase?.resultDao()
    }

    suspend fun getAllResults(): List<GameResult> = resultDao?.getAllResults() ?: emptyList()

    suspend fun getWins(): Int? = resultDao?.getWins()

    suspend fun getLoses(): Int? = resultDao?.getLoses()

    suspend fun getDraws(): Int? = resultDao?.getDraws()

    suspend fun insertResult(gameResult: GameResult) = resultDao?.insertResult(gameResult)

    suspend fun deleteAllResults() = resultDao?.deleteAllResults()
}