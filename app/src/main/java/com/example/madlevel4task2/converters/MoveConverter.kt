package com.example.madlevel4task2.converters

import androidx.room.TypeConverter
import com.example.madlevel4task2.enums.Move

class MoveConverter {
    @TypeConverter
    fun moveToInt(value: Move): Int = value.ordinal

    @TypeConverter
    fun intToMove(value: Int) = value.toEnum<Move>()

    // cast Int to enum
    private inline fun <reified T : Enum<T>> Int.toEnum(): T = enumValues<T>()[this]
}