package com.example.madlevel4task2.converters

import androidx.room.TypeConverter
import com.example.madlevel4task2.enums.Outcome

class OutcomeConverter {
    @TypeConverter
    fun outcomeToInt(value: Outcome): Int = value.ordinal

    @TypeConverter
    fun intToOutcome(value: Int) = value.toEnum<Outcome>()

    // cast Int to enum
    private inline fun <reified T : Enum<T>> Int.toEnum(): T = enumValues<T>()[this]
}