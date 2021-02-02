package com.mtek.goarenopoc.ui.fragment.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtek.goarenopoc.base.BaseViewModel
import com.mtek.goarenopoc.data.network.response.DashboardResponseModel
import com.mtek.goarenopoc.data.repository.DashboardRepository

class DashboardViewModel : BaseViewModel<DashboardRepository>(DashboardRepository::class) {
    private val mutableMonthlySales: MutableLiveData<DashboardResponseModel> =
        MutableLiveData()
    val monthlySales: LiveData<DashboardResponseModel> = mutableMonthlySales

    private val mutableShop: MutableLiveData<DashboardResponseModel> =
        MutableLiveData()
    val responseShop: LiveData<DashboardResponseModel> = mutableShop

    fun getMontlySales() {
        sendRequest {
            repository.getMonthlySales().run {
                mutableMonthlySales.postValue(this)
                errMsg?.postValue(errMsg.value)
                return@run
            }
        }
    }

    fun getShop(shopid: Int) {
        sendRequest {
            repository.getShop(shopid).run {
                mutableShop.postValue(this)
                errMsg?.postValue(errMsg.value)
            }
        }
    }
}