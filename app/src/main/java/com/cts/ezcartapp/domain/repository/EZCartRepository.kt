package com.cts.ezcartapp.domain.repository

import com.cts.ezcartapp.domain.model.LoginUser
import com.cts.ezcartapp.domain.model.OrdersList
import com.cts.ezcartapp.domain.model.ShoppingListData
import io.reactivex.Observable

interface EZCartRepository {
    fun getShoppingList(loginUser: LoginUser) : Observable<ShoppingListData>
    fun getOrdersListFromCloud() : Observable<OrdersList>
}