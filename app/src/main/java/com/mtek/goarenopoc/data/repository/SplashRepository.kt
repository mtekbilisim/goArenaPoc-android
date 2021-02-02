package com.mtek.goarenopoc.data.repository

import com.mtek.goarenopoc.base.BaseRepository
import com.mtek.goarenopoc.data.network.api.ApiService

class SplashRepository(private val service : ApiService)  : BaseRepository(){
   // suspend fun versionControl() = service.versionControl()
}