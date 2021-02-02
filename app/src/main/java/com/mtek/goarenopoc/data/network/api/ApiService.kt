package com.mtek.goarenopoc.data.network.api

import com.mtek.goarenopoc.data.network.response.FeedResponseModel
import retrofit2.http.GET


interface ApiService {

    @GET("feeds/")
    suspend fun feedAllRequest() : FeedResponseModel

}