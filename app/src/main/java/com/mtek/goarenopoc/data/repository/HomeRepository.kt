package com.mtek.goarenopoc.data.repository

import com.mtek.goarenopoc.base.BaseRepository
import com.mtek.goarenopoc.data.network.api.ApiService

class HomeRepository(private val service : ApiService)  : BaseRepository(){
    suspend fun feedRequest() = service.feedAllRequest()

    suspend fun deleteFeed(feedId : String) = service.deleteFeed(feedId)
}