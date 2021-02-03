package com.mtek.goarenopoc.data.repository

import com.mtek.goarenopoc.base.BaseRepository
import com.mtek.goarenopoc.data.model.FeedPlainModel
import com.mtek.goarenopoc.data.model.MediaModel
import com.mtek.goarenopoc.data.network.api.ApiService
import okhttp3.MultipartBody

class PostRepository(private val service: ApiService) : BaseRepository() {
    suspend fun feedRequest(request: FeedPlainModel) = service.requestFeed(request)

    suspend fun feedUpdateRequest(feedId: String,request: FeedPlainModel) = service.requestUpdateFeed(feedId,request)

    suspend fun feedImageUpload(image: ArrayList<MultipartBody.Part>) = service.requestMedia(image)

    suspend fun feedCompleted(feedId: String, mediaList: ArrayList<MediaModel>) =
        service.feedCompleted(feedId, mediaList)
}