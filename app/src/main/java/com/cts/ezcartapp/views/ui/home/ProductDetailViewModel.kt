package com.cts.ezcartapp.views.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cts.ezcartapp.data.entities.CartData
import com.cts.ezcartapp.data.entities.ShoppingData
import com.cts.ezcartapp.data.repository.RemoteDataSourceRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductDetailViewModel @Inject constructor(val remoteDataSourceRepository: RemoteDataSourceRepository) :
    ViewModel() {
    val gson: Gson = Gson()
    val addToCartStatus:MutableLiveData<Boolean> = MutableLiveData()

    fun addToCart(shoppingData: ShoppingData) {
        val cartData: CartData = gson.fromJson(gson.toJson(shoppingData),CartData::class.java)
        viewModelScope.launch {
            remoteDataSourceRepository.addCartItem(cartData)
            addToCartStatus.value=true
        }
    }
}