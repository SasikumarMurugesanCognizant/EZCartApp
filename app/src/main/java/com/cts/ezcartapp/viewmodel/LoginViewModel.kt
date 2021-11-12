package com.cts.ezcartapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cts.ezcartapp.domain.model.LoginUser
import com.cts.ezcartapp.data.entities.UserData
import com.cts.ezcartapp.data.repository.RemoteDataSourceRepository
import com.cts.ezcartapp.utils.Extensions.encrypt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(val remoteDataSourceRepository: RemoteDataSourceRepository) : ViewModel() {

    val userId: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val userLiveData: MutableLiveData<LoginUser> = MutableLiveData()
    val loginStatus: MutableLiveData<Boolean> = MutableLiveData()
    val username: MutableLiveData<String> = MutableLiveData()



    init {
        userId.value = "sasi@gmail.com"
        password.value = "Sasi@123"
    }

    fun onLoginClick() {
        val loginUser = LoginUser(userId.value, password.value)
        userLiveData.value = loginUser
    }

    fun doLogin(it: LoginUser?) {
        viewModelScope.launch {
            val response = async(Dispatchers.IO) {
                remoteDataSourceRepository.getUserData(it?.EmailAddress ?: "", it?.Password?.encrypt() ?: "")
            }
            val result: UserData? = response.await() as UserData?
            username.value =result?.firstName + " " + result?.lastName
            loginStatus.value = result?.userId?.isNotEmpty() ?: false

        }
    }

}