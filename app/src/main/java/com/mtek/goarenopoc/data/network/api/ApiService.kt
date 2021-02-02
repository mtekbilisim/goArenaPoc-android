package com.mtek.goarenopoc.data.network.api

import com.mtek.goarenopoc.data.network.response.DashboardResponseModel
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("dashboard")
    suspend fun getMonthlySales(): DashboardResponseModel

    @GET("dashboard/")
    suspend fun getShop(@Query("shop") shopId: Int): DashboardResponseModel
}