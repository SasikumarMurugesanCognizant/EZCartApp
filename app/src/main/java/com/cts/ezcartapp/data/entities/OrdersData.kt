package com.cts.ezcartapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity
data class OrdersData(
    @PrimaryKey
    @ColumnInfo(name = "order id")
    @SerializedName("order id") val orderId : Int? = 0,
    @ColumnInfo(name = "userr id")
    @SerializedName("userr id") val userId : Int,
    @ColumnInfo(name = "order date")
    @SerializedName("order date") val orderDate : String,
    @ColumnInfo(name = "order total")
    @SerializedName("order total") val orderTotal : Int,
    @ColumnInfo(name = "data")
    @SerializedName("data") val data: List<ShoppingData>
)
class ShoppingDataConverter{
    @TypeConverter
    fun fromString(jsonStr:String):List<ShoppingData>{
        val listType=object: TypeToken<List<ShoppingData>>(){}.type
        return Gson().fromJson(jsonStr,listType)
    }
    @TypeConverter
    fun fromShoppingList(list:List<ShoppingData>):String{
        return  Gson().toJson(list)
    }

}
