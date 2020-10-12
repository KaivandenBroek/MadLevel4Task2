package com.example.madlevel4task2

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "resultTable")
data class GameResult (

    @ColumnInfo(name = "outcome")
    var outcome: String,

    @ColumnInfo(name = "date")
    var date: String,

    @ColumnInfo(name = "handPC")
    var handPC: String,

    @ColumnInfo(name = "handUser")
    var handUser: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
) : Parcelable