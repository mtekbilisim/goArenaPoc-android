package com.mtek.goarenopoc.ui.fragment

import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import com.mtek.goarenopoc.data.model.Feed
import com.mtek.goarenopoc.data.model.FeedModel
import com.mtek.goarenopoc.utils.emptyString


class SharedViewModel : ViewModel() {
    private val postValue = MutableLiveData<String>()
    private var filteredImage = MutableLiveData<String>()
    private var feedUpdateId = MutableLiveData<FeedModel>()
    private var responseFeedList = MutableLiveData<Feed>()

    fun setFeedList(list : Feed){
        this.responseFeedList.value = list
    }

    fun getFeedList() : LiveData<Feed>{
        return responseFeedList
    }

    fun setFilteredImage(imageUriStr: String) {
        this.filteredImage.value = imageUriStr
    }

    fun getFilteredImage(): LiveData<String> {
        return filteredImage
    }

    fun setUpdateFeedModel(updateFeedId: FeedModel) {
        this.feedUpdateId.value = updateFeedId
    }

    fun getFeedUpdateIdModel(): LiveData<FeedModel> {
        return feedUpdateId
    }

    fun setPostValue(postValue: String) {
        this.postValue.value = postValue
    }

    fun getPostValue(): LiveData<String> {
        return postValue
    }

    fun cleanDataImage() {
        this.filteredImage.value = emptyString()

    }

    fun cleanAllData(){
        this.postValue.value = emptyString()
        this.filteredImage.value = emptyString()
        this.feedUpdateId.value = null
    }
}