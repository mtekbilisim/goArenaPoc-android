package com.mtek.goarenopoc.ui.fragment.home

import com.mtek.goarenopoc.base.BaseViewModel
import com.mtek.goarenopoc.data.repository.HomeRepository

class HomeViewModel : BaseViewModel<HomeRepository>(HomeRepository::class){

//    fun senRequestVersionControl(){
//        sendRequest {
//            repository.home().run {
//
//            }
//            errMsg?.let {
//                it.postValue(errMsg.value)
//            }
//        }
//    }
}