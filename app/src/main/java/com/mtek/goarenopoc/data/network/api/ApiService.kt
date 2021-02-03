package com.mtek.goarenopoc.data.network.api

import com.mtek.goarenopoc.data.network.response.DashboardResponseModel
import com.mtek.goarenopoc.data.network.response.ExpectionResponseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.mtek.goarenopoc.data.model.FeedPlainModel
import com.mtek.goarenopoc.data.model.MediaModel
import com.mtek.goarenopoc.data.network.response.*
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

    @GET("dashboard")
    suspend fun getMonthlySales(): DashboardResponseModel
    //FeedTextUpdate
    @PUT("feeds/{feedId}")
    suspend fun requestUpdateFeed(
        @Path("feedId") feedId: String,
        @Body requestFeed: FeedPlainModel
    ): FeedUpdateResponseModel

    //FeedCreate
    @POST("feeds/{feedId}/medias/multi")
    suspend fun feedCompleted(
        @Path("feedId") feedId: String,
        @Body requestFeed: ArrayList<MediaModel>
    ): MediaModelResponseModel

    //SendMedia
    @Multipart
    @POST("files/uploadMultipleFiles")
    suspend fun requestMedia(
        @Part image: ArrayList<MultipartBody.Part>,
    ): FileResponseModel

    @DELETE("feeds/{feedId}")
    suspend fun deleteFeed(@Path("feedId") feedId: String)


    @GET("dashboard/expectations")
    suspend fun getExpectation(): ExpectionResponseModel

    @GET("dashboard/expectations/{id}")
    suspend fun getExpectationById(@Path("id") id: Int): ExpectionResponseModel
}