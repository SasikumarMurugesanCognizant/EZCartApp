package com.cts.ezcartapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class UserData(
    @ColumnInfo(name="firstName")
    val firstName: String,
    @ColumnInfo(name="lastName")
    val lastName: String,
    @ColumnInfo(name="userId")
    val userId: String,
    @ColumnInfo(name="password")
    val password: String,
    @ColumnInfo(name = "mobileNumber")
    val mobileNumber: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)
