package com.cts.ezcartapp.views.ui.feedback

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cts.ezcartapp.data.entities.FeedBackData
import com.cts.ezcartapp.data.repository.RemoteDataSourceRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedbackViewModel @Inject constructor(val remoteDataSourceRepository: RemoteDataSourceRepository) : ViewModel() {
    val feedBackUpdatedStatus:MutableLiveData<Boolean> = MutableLiveData()
     fun addFeedBackToDB(feedBackData: FeedBackData){
         viewModelScope.launch {
             remoteDataSourceRepository.addFeedbackToLocalDB(feedBackData)
             feedBackUpdatedStatus.value=true
         }
     }
}