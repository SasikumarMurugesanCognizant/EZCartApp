package com.cts.ezcartapp.views.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cts.ezcartapp.domain.model.LoginUser
import com.cts.ezcartapp.domain.model.ShoppingListData
import com.cts.ezcartapp.data.entities.ShoppingData
import com.cts.ezcartapp.data.repository.RemoteDataSourceRepository
import com.cts.ezcartapp.domain.model.DataHolder
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(val remoteDataSourceRepository: RemoteDataSourceRepository) :
    ViewModel() {

    var shoppingList: LiveData<List<ShoppingData>>
    var dataHolder: MutableLiveData<DataHolder<ShoppingListData>> = MutableLiveData()

    init {
        shoppingList = fetchDataFromDatabase()
    }

    fun fetchShoppingDataFromCloud(loginRequest: LoginUser) {
        dataHolder.value = DataHolder.Loading(true)
        remoteDataSourceRepository.getShoppingList(loginRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ShoppingListData> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(shoppingList: ShoppingListData) {
                    if (shoppingList.data.isNullOrEmpty().not()) {
                        dataHolder.value = DataHolder.Success(shoppingList)
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
    fun insertShoppingListToDatabase(shoppingListData: ShoppingListData){
        viewModelScope.launch {
            remoteDataSourceRepository.insertShoppingListIntoLocalStorage(
                shoppingListData.data
            )
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text


    private fun fetchDataFromDatabase(): LiveData<List<ShoppingData>> {
        return remoteDataSourceRepository.getAllShoppingData()
    }

}


