package com.cts.ezcartapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FeedBackData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Int=0,
    @ColumnInfo(name = "FeedbackComments")
    val feedback:String,
    @ColumnInfo(name = "Rating")
    val rating:String)
