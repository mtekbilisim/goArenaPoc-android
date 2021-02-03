package com.mtek.goarenopoc.data.repository

import com.mtek.goarenopoc.base.BaseRepository
import com.mtek.goarenopoc.data.network.api.ApiService
import com.mtek.goarenopoc.data.network.request.LoginRequestModel

class LoginRepository (private val service : ApiService) : BaseRepository(){

    suspend fun getAuthentication(request : LoginRequestModel) = service.authentication(request)

    suspend fun getUserInformation() = service.getUser()
}