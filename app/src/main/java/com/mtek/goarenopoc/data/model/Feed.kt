package com.mtek.goarenopoc.data.model

data class FeedModel(
    val id : Int?,
    val title : String?,
    val postType : String?,
    val likes :Int?,
    val comments: ArrayList<CommentModel>?,
    val postDate : String?,
    val medias : ArrayList<MediaModel>?,
    val user : UserModel?,
    val status : String?
){

    override fun toString(): String {
        return "FeedModel(id=$id, title=$title, postType=$postType, likes=$likes, comments=$comments, postDate=$postDate, medias=$medias, user=$user, status=$status)"
    }
}

data class FeedPlainModel(
    val id : Int?,
    val title : String?,
    val postType : String?,
    val likes :Int?,
    val postDate : String?,
    val userId : Int?,
    val status : String?
)



data class LikeModel(
    val id : Int?,
    val postDate : String?,
    val feedId : Int?,
    val user : UserModel?

)

data class CommentModel(
    val id : Int?,
    val comment:String?,
    val postDate : String?,
    val feedId : Int?,
    val user : UserModel?

)


data class LikePlainModel(
    val id : Int?,
    val comment:String?,
    val postDate : String?,
    val userId : Int?,
    val feedId : Int?
)

data class MediaModel(
    val id : Int?,
    val uri : String?,
    val mimeType : String?,
    val feedId :Int?,
    val userId : Int?
)

data class UserModel(
    val username : String?,
    val avatar : String?,
    val id : Int?
)