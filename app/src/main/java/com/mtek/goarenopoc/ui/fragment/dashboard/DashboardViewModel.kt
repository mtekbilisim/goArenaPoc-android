package com.mtek.goarenopoc.ui.fragment.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtek.goarenopoc.base.BaseViewModel
import com.mtek.goarenopoc.data.network.response.DashboardResponseModel
import com.mtek.goarenopoc.data.network.response.ExpectionResponseModel
import com.mtek.goarenopoc.data.network.response.SalesResponseModel
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

    private val mutableSales: MutableLiveData<SalesResponseModel> = MutableLiveData()
    val responseSales: LiveData<SalesResponseModel> = mutableSales

    private val mutablePersonelStatistic: MutableLiveData<SalesResponseModel> = MutableLiveData()
    val responsePersonelStatistic: LiveData<SalesResponseModel> = mutablePersonelStatistic

    fun getMontlySales() {
        sendRequest {
            repository.getMonthlySales().run {
                mutableMonthlySales.postValue(this)
                errMsg?.postValue(errMsg.value)
                return@run
            }
        }
    }

    fun getSalesAndTarget(id: Int) {
        sendRequest {
            repository.getSalesAndTarget(id).run {
                mutableSales.postValue(this)
                errMsg?.postValue(errMsg.value)
            }
        }
    }

    fun getPersonSalesAndTarget(id: Int) {
        sendRequest {
            repository.getPersonSalesAndTarget(id).run {
                mutablePersonelStatistic.postValue(this)
                errMsg?.postValue(errMsg.value)
            }
        }
    }
}