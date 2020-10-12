package com.example.madlevel4task2

import android.content.Context

public class ResultRepository(context: Context) {

    private var resultDao: ResultDao

    init {
        val resultRoomDatabase = ResultRoomDatabase.getDatabase(context)
        resultDao = resultRoomDatabase!!.resultDao()
    }

    suspend fun getAllResults(): List<GameResult> {
        return resultDao.getAllResults()
    }

    suspend fun insertResult(gameResult: GameResult) {
        resultDao.insertResult(gameResult)
    }

    suspend fun deleteAllResults() {
        resultDao.deleteAllResults()
    }
}