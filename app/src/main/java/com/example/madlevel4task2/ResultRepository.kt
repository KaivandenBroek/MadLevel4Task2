package com.example.madlevel4task2

import android.content.Context

class ResultRepository(context: Context) {

    private var resultDao: ResultDao?

    init {
        val resultRoomDatabase = ResultRoomDatabase.getDatabase(context)
        resultDao = resultRoomDatabase?.resultDao()
    }

    suspend fun getAllResults(): List<GameResult> = resultDao?.getAllResults() ?: emptyList()

    suspend fun getWins(): Int? = resultDao?.getWins()

    suspend fun getLoses(): Int? = resultDao?.getWins()

    suspend fun getDraws(): Int? = resultDao?.getWins()


    suspend fun insertResult(gameResult: GameResult) {
        resultDao?.insertResult(gameResult)
    }

    suspend fun deleteAllResults() {
        resultDao?.deleteAllResults()
    }
}