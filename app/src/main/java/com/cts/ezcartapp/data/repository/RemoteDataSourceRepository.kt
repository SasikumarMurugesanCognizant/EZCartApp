package com.cts.ezcartapp.data.repository

import androidx.lifecycle.LiveData
import com.cts.ezcartapp.data.api.EZCartApi
import com.cts.ezcartapp.data.database.EZDao
import com.cts.ezcartapp.data.entities.*
import com.cts.ezcartapp.domain.model.LoginUser
import com.cts.ezcartapp.domain.model.OrdersList
import com.cts.ezcartapp.domain.model.ShoppingListData
import com.cts.ezcartapp.domain.repository.EZCartRepository
import com.cts.ezcartapp.domain.repository.LocalStorageRepository
import io.reactivex.Observable
import javax.inject.Inject

class RemoteDataSourceRepository @Inject constructor(
    private val apiService: EZCartApi,
    private val localDAO: EZDao
) :
    LocalStorageRepository, EZCartRepository {

    override fun getShoppingList(loginUser: LoginUser): Observable<ShoppingListData> {
        return apiService.getShoppingList(loginUser)
    }

    override fun getOrdersListFromCloud(): Observable<OrdersList> {
        return apiService.getOrders()
    }

    override fun getAllShoppingData(): LiveData<List<ShoppingData>> {
        return localDAO.getShoppingList()
    }

    override suspend fun getUserData(userId: String, password: String): UserData {
        return localDAO.login(userId, password)
    }

    override suspend fun insertUserDataIntoLocalStorage(model: UserData) {
        return localDAO.insert(model)
    }

    override suspend fun insertShoppingListIntoLocalStorage(shoppingData: List<ShoppingData>) {
        return localDAO.insertShoppingList(shoppingData)
    }

    override suspend fun clearShoppingListFromLocalStorage() {
        return localDAO.clearShoppingList()
    }

    override suspend fun addCartItem(cartData: CartData) {
        return localDAO.insertCartItem(cartData)
    }

    override suspend fun addFeedbackToLocalDB(feedBackData: FeedBackData) {
        return localDAO.addFeedbackToLocalDB(feedBackData)
    }

    override fun getCartList(): LiveData<List<CartData>> {
        return localDAO.getCartList()
    }

    override suspend fun deleteCartItem(cartData: CartData) {
        return localDAO.deleteCartItem(cartData)
    }

    override suspend fun addOrdersToLocalDB(ordersList:List<OrdersData> ) {
        return localDAO.addOrdersToLocalDB(ordersList)
    }

    override fun getOrdersList(): LiveData<List<OrdersData>> {
        return localDAO.getOrdersList()
    }


}