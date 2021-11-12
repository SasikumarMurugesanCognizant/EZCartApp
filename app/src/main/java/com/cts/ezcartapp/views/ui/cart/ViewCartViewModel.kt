package com.cts.ezcartapp.views.ui.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cts.ezcartapp.data.entities.CartData
import com.cts.ezcartapp.data.repository.RemoteDataSourceRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewCartViewModel @Inject constructor(val remoteDataSourceRepository: RemoteDataSourceRepository) :
    ViewModel() {
    var cartLiveData= remoteDataSourceRepository.getCartList()

    fun removeCartItem(cartItem: CartData) {
        viewModelScope.launch {
            remoteDataSourceRepository.deleteCartItem(cartItem)
        }
    }

}