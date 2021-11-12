package com.cts.ezcartapp.domain.repository

import androidx.lifecycle.LiveData
import com.cts.ezcartapp.data.entities.*

interface LocalStorageRepository {
    fun getAllShoppingData() : LiveData<List<ShoppingData>>
    suspend fun getUserData(userId:String,password:String) : UserData
    suspend fun insertUserDataIntoLocalStorage(model : UserData)
    suspend fun insertShoppingListIntoLocalStorage(shoppingData: List<ShoppingData>)
    suspend fun clearShoppingListFromLocalStorage()
    suspend fun addCartItem(cartData: CartData)
    suspend fun addFeedbackToLocalDB(feedBackData: FeedBackData)
    fun getCartList():LiveData<List<CartData>>
    suspend fun deleteCartItem(cartData: CartData)
    suspend fun addOrdersToLocalDB(ordersData: List<OrdersData>)
    fun getOrdersList():LiveData<List<OrdersData>>
}