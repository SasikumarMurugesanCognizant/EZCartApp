package com.cts.ezcartapp.data.database


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.cts.ezcartapp.data.entities.*

@Dao
interface EZDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userData: UserData)

    @Query("SELECT * FROM UserData WHERE userId= :userId AND password=:password")
    suspend fun login(userId: String, password: String): UserData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShoppingList(shoppingList: List<ShoppingData>)

    @Query("SELECT * FROM ShoppingData")
    fun getShoppingList(): LiveData<List<ShoppingData>>

    @Query("DELETE FROM ShoppingData")
    suspend fun clearShoppingList()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartData: CartData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFeedbackToLocalDB(feedBackData: FeedBackData)

    @Query("SELECT * FROM CartData")
    fun getCartList(): LiveData<List<CartData>>

    @Delete
    suspend fun deleteCartItem(cartItem:CartData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrdersToLocalDB(ordersList: List<OrdersData>)

    @Query("SELECT * FROM OrdersData" )
    fun getOrdersList():LiveData<List<OrdersData>>
}