package com.mtek.goarenopoc.data.network.api

import com.mtek.goarenopoc.data.model.FeedPlainModel
import com.mtek.goarenopoc.data.model.MediaModel
import com.mtek.goarenopoc.data.network.response.FeedResponseModel
import com.mtek.goarenopoc.data.network.response.FileResponseModel
import com.mtek.goarenopoc.data.network.response.MediaModelResponseModel
import com.mtek.goarenopoc.data.network.response.PostResponseModel
import okhttp3.MultipartBody
import retrofit2.http.*


interface ApiService {
    //GetFeed
    @GET("feeds")
    suspend fun feedAllRequest(): FeedResponseModel

    //FeedCreate
    @POST("feeds")
    suspend fun requestFeed(
        @Body requestFeed: FeedPlainModel
    ): PostResponseModel

 //FeedCreate
    @POST("feeds/{feedId}/medias/multi")
    suspend fun feedCompleted(
     @Path("feedId") feedId : String,
        @Body requestFeed: ArrayList<MediaModel>
    ): MediaModelResponseModel

    //SendMedia
    @Multipart
    @POST("files/uploadMultipleFiles")
    suspend fun requestMedia(
        @Part image: ArrayList<MultipartBody.Part>,
    ): FileResponseModel


}