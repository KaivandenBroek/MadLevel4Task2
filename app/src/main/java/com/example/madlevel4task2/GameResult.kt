package com.example.madlevel4task2

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.madlevel4task2.enums.Outcome
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "resultTable")
data class GameResult (

    @ColumnInfo(name = "outcome")
    var outcome: Outcome?,

    @ColumnInfo(name = "date")
    var date: Date,

    @ColumnInfo(name = "handPC")
    var handPC: String,

    @ColumnInfo(name = "handUser")
    var handUser: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
) : Parcelable