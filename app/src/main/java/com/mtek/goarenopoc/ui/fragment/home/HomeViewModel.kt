package com.mtek.goarenopoc.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtek.goarenopoc.base.BaseViewModel
import com.mtek.goarenopoc.data.network.response.FeedResponseModel
import com.mtek.goarenopoc.data.repository.HomeRepository

class HomeViewModel : BaseViewModel<HomeRepository>(HomeRepository::class){


    private val _mutableFeed: MutableLiveData<FeedResponseModel> = MutableLiveData()
    val feedResponse : LiveData<FeedResponseModel> = _mutableFeed


    fun senRequestVFeed(){
        sendRequest {
            repository.feedRequest().run {
                _mutableFeed.postValue(this)
            }
            errMsg?.let {
                it.postValue(errMsg.value)
            }
        }
    }
}