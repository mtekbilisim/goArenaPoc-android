package com.mtek.goarenopoc.ui.fragment.postfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtek.goarenopoc.base.BaseViewModel
import com.mtek.goarenopoc.data.model.FeedPlainModel
import com.mtek.goarenopoc.data.model.MediaModel
import com.mtek.goarenopoc.data.network.response.FileResponseModel
import com.mtek.goarenopoc.data.network.response.MediaModelResponseModel
import com.mtek.goarenopoc.data.network.response.PostResponseModel
import com.mtek.goarenopoc.data.repository.PostRepository
import okhttp3.MultipartBody

class PostViewModel : BaseViewModel<PostRepository>(PostRepository::class) {

    private val _mutableFeed: MutableLiveData<PostResponseModel> = MutableLiveData()
    val responseFeed: LiveData<PostResponseModel> = _mutableFeed

    private val _mutableFile: MutableLiveData<FileResponseModel> = MutableLiveData()
    val responseFile: LiveData<FileResponseModel> = _mutableFile

    private val _mutableCompletedFeed: MutableLiveData<MediaModelResponseModel> = MutableLiveData()
    val responseCompleted: LiveData<MediaModelResponseModel> = _mutableCompletedFeed


    fun senRequestFeed(request: FeedPlainModel) {
        sendRequest {
            repository.feedRequest(request).run {
                _mutableFeed.postValue(this)
            }
            errMsg?.let {
                it.postValue(errMsg.value)
            }
        }
    }


    fun sendFeedImageUpload(image: ArrayList<MultipartBody.Part>) {
        sendRequest {
            repository.feedImageUpload(image).run {
                _mutableFile.postValue(this)
            }
            errMsg?.let {
                it.postValue(errMsg.value)
            }
        }
    }

    fun sendCompletedFeed(feedId: String, image: ArrayList<MediaModel>) {
        sendRequest {
            repository.feedCompleted(feedId, image).run {
                _mutableCompletedFeed .postValue(this)
            }
            errMsg?.let {
                it.postValue(errMsg.value)
            }
        }
    }


}