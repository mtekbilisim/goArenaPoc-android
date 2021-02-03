package com.mtek.goarenopoc.ui.fragment.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtek.goarenopoc.base.BaseViewModel
import com.mtek.goarenopoc.data.network.response.DashboardResponseModel
import com.mtek.goarenopoc.data.network.response.ExpectionResponseModel
import com.mtek.goarenopoc.data.repository.DashboardRepository

class DashboardViewModel : BaseViewModel<DashboardRepository>(DashboardRepository::class) {
    private val mutableMonthlySales: MutableLiveData<DashboardResponseModel> =
        MutableLiveData()
    val monthlySales: LiveData<DashboardResponseModel> = mutableMonthlySales

    private val mutableTarget: MutableLiveData<ExpectionResponseModel> =
        MutableLiveData()
    val responseTarget: LiveData<ExpectionResponseModel> = mutableTarget

    private val mutableShopQuailty: MutableLiveData<DashboardResponseModel> =
        MutableLiveData()
    val responseShopQuailty: LiveData<DashboardResponseModel> = mutableShopQuailty

    fun getMontlySales() {
        sendRequest {
            repository.getMonthlySales().run {
                mutableMonthlySales.postValue(this)
                errMsg?.postValue(errMsg.value)
                return@run
            }
        }
    }

    fun getMontlyTarget() {
        sendRequest {
            repository.getExpectation().run {
                mutableTarget.postValue(this)
                errMsg?.postValue(errMsg.value)
                return@run
            }
        }
    }

    fun getMontlyTargetById(id: Int) {
        sendRequest {
            repository.getExpectationById(id).run {
                mutableTarget.postValue(this)
                errMsg?.postValue(errMsg.value)
                return@run
            }
        }
    }

    fun getFilterByShopPersonel(id: Int) {
        sendRequest {
            repository.getDashboardFilterById(id).run {
                mutableShopQuailty.postValue(this)
                errMsg?.postValue(errMsg.value)
            }
        }
    }
}