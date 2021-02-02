package com.mtek.goarenopoc.ui.fragment.splash

import androidx.lifecycle.ViewModel
import com.mtek.goarenopoc.base.BaseViewModel
import com.mtek.goarenopoc.data.repository.SplashRepository
import com.mtek.goarenopoc.utils.Constants
import com.mtek.goarenopoc.utils.emptyString

class SplashViewModel  : BaseViewModel<SplashRepository>(SplashRepository::class){




//    fun senRequestVersionControl(){
//        sendRequest {
//            repository.versionControl().run {
//
//            }
//            errMsg?.let {
//                it.postValue(errMsg.value)
//            }
//        }
//    }

}


class TestViewModel : ViewModel(){
    var text : String = ""
}