package com.mtek.goarenopoc.ui.fragment.splash

import com.mtek.goarenopoc.base.BaseViewModel
import com.mtek.goarenopoc.data.repository.SplashRepository

class SplashViewModel  : BaseViewModel<SplashRepository>(SplashRepository::class){



    fun senRequestVersionControl(){
        sendRequest {
            repository.versionControl().run {

            }
            errMsg?.let {
                it.postValue(errMsg.value)
            }
        }
    }

}