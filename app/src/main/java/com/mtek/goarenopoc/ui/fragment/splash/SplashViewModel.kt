package com.mtek.goarenopoc.ui.fragment.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mtek.goarenopoc.base.BaseViewModel
import com.mtek.goarenopoc.data.model.TokenModel
import com.mtek.goarenopoc.data.network.request.LoginRequestModel
import com.mtek.goarenopoc.data.network.response.UserResponseModel
import com.mtek.goarenopoc.data.repository.SplashRepository
import com.mtek.goarenopoc.utils.Constants
import com.mtek.goarenopoc.utils.emptyString

class SplashViewModel  : BaseViewModel<SplashRepository>(SplashRepository::class){

    private val _mutableToken: MutableLiveData<TokenModel> = MutableLiveData()
    val responseToken: LiveData<TokenModel> = _mutableToken

    private val _mutableUserInformation: MutableLiveData<UserResponseModel> = MutableLiveData()
    val responseUserInformation: LiveData<UserResponseModel> = _mutableUserInformation

    fun senRequestToken(request: LoginRequestModel) {
        sendRequest {
            repository.getAuthentication(request).run {
                _mutableToken.postValue(this)
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
            }
            errMsg?.let {
                it.postValue(errMsg.value)
            }
        }
    }
}

