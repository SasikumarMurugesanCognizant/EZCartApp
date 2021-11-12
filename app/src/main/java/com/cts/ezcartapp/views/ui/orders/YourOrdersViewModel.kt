package com.cts.ezcartapp.views.ui.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cts.ezcartapp.data.entities.OrdersData
import com.cts.ezcartapp.data.repository.RemoteDataSourceRepository
import com.cts.ezcartapp.domain.model.DataHolder
import com.cts.ezcartapp.domain.model.LoginUser
import com.cts.ezcartapp.domain.model.OrdersList
import com.cts.ezcartapp.domain.model.ShoppingListData
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

class YourOrdersViewModel @Inject constructor(val remoteDataSourceRepository: RemoteDataSourceRepository) :
    ViewModel() {
    var ordersList: LiveData<List<OrdersData>>
    var dataHolder: MutableLiveData<DataHolder<OrdersList>> = MutableLiveData()

    init {
        ordersList = fetchDataFromDatabase()
    }

    private fun fetchDataFromDatabase(): LiveData<List<OrdersData>> {
        return remoteDataSourceRepository.getOrdersList()
    }

    fun insertOrdersListToDatabase(ordersList: List<OrdersData>) {
        viewModelScope.launch {
            remoteDataSourceRepository.addOrdersToLocalDB(
                ordersList
            )
        }
    }
    fun fetchOrdersDataFromCloud() {
        dataHolder.value = DataHolder.Loading(true)
        remoteDataSourceRepository.getOrdersListFromCloud()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<OrdersList> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(ordersList: OrdersList) {
                    if (ordersList.data.isNullOrEmpty().not()) {
                        dataHolder.value = DataHolder.Success(ordersList)
                    }
                }

                override fun onError(e: Throwable) {
                    dataHolder.value = DataHolder.Fail(e)
                }

                override fun onComplete() {
                    dataHolder.value = DataHolder.Loading(false)
                }

            })
    }
}