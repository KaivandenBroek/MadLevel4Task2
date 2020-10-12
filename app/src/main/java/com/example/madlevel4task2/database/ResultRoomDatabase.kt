package com.example.madlevel4task2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.madlevel4task2.converters.DateConverter
import com.example.madlevel4task2.converters.MoveConverter
import com.example.madlevel4task2.converters.OutcomeConverter
import com.example.madlevel4task2.model.GameResult

@Database(entities = [GameResult::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, OutcomeConverter::class, MoveConverter::class)
abstract class ResultRoomDatabase : RoomDatabase() {

    abstract fun resultDao(): ResultDao

    companion object {
        private const val DATABASE_NAME = "REMINDER_DATABASE"

        @Volatile
        private var resultRoomDatabaseInstance: ResultRoomDatabase? = null

        fun getDatabase(context: Context): ResultRoomDatabase? {
            if (resultRoomDatabaseInstance == null) {
                synchronized(ResultRoomDatabase::class.java) {
                    if (resultRoomDatabaseInstance == null) {
                        resultRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            ResultRoomDatabase::class.java, DATABASE_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return resultRoomDatabaseInstance
        }
    }

}
