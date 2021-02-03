package com.mtek.goarenopoc.ui.fragment.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtek.goarenopoc.base.BaseViewModel
import com.mtek.goarenopoc.data.model.TokenModel
import com.mtek.goarenopoc.data.network.request.LoginRequestModel
import com.mtek.goarenopoc.data.network.response.FeedResponseModel
import com.mtek.goarenopoc.data.network.response.UserResponseModel
import com.mtek.goarenopoc.data.repository.LoginRepository

class LoginViewModel : BaseViewModel<LoginRepository>(LoginRepository::class) {

    private val _mutableToken: MutableLiveData<TokenModel> = MutableLiveData()
    val responseToken: LiveData<TokenModel> = _mutableToken

    private val _mutableUserInformation: MutableLiveData<UserResponseModel> = MutableLiveData()
    val responseUserInformation: LiveData<UserResponseModel> = _mutableUserInformation

    private val _mutableFeed: MutableLiveData<FeedResponseModel> = MutableLiveData()
    val feedResponse : LiveData<FeedResponseModel> = _mutableFeed


    fun senRequestToken(request: LoginRequestModel) {
        sendRequest {
            repository.getAuthentication(request).run {
                _mutableToken.postValue(this)
                return@run
            }
            errMsg?.let {
                it.postValue(errMsg.value)
            }
        }
    }

    fun senRequestInformation() {
        sendRequest {
            repository.getUserInformation().run {
                _mutableUserInformation.postValue(this)
                return@run
            }
            errMsg?.let {
                it.postValue(errMsg.value)
            }
        }
    }

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