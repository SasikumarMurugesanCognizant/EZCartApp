package com.cts.ezcartapp.data.api

import com.cts.ezcartapp.data.entities.OrdersData
import com.cts.ezcartapp.domain.model.CartData
import com.cts.ezcartapp.domain.model.LoginUser
import com.cts.ezcartapp.domain.model.OrdersList
import com.cts.ezcartapp.domain.model.ShoppingListData
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface EZCartApi {
    @POST("getorders")
    fun getOrders(): Observable<OrdersList>

    @POST("login")
    fun getShoppingList(@Body loginUser: LoginUser): Observable<ShoppingListData>
}