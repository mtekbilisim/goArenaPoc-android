package com.mtek.goarenopoc.data.network.api

import com.mtek.goarenopoc.data.network.response.DashboardResponseModel
import com.mtek.goarenopoc.data.network.response.ExpectionResponseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("dashboard")
    suspend fun getMonthlySales(): DashboardResponseModel

    @GET("dashboard/expectations")
    suspend fun getExpectation(): ExpectionResponseModel

    @GET("dashboard/expectations/{id}")
    suspend fun getExpectationById(@Path("id") id: Int): ExpectionResponseModel
}