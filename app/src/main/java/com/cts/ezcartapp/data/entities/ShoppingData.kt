package com.cts.ezcartapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ShoppingData(
    @PrimaryKey
    @ColumnInfo(name="itemName")
    @SerializedName("item name") val itemName : String="",
    @ColumnInfo(name="desc")
    @SerializedName("desc") val desc: String?="",
    @ColumnInfo(name="price")
    @SerializedName("price") val price: Int?=0,
    @ColumnInfo(name="currency")
    @SerializedName("currency") val currency: String?="",
    @ColumnInfo(name="expiry date")
    @SerializedName("expiry date") val expiryDate : String?="",
    @ColumnInfo(name="quantity")
    @SerializedName("quantity") val quantity: String?=""
)
