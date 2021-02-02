package com.mtek.goarenopoc.ui.fragment

import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import com.mtek.goarenopoc.utils.emptyString


class SharedViewModel : ViewModel() {
    private val postValue = MutableLiveData<String>()
    private var filteredImage = MutableLiveData<String>()

    fun setFilteredImage(imageUriStr : String){
        this.filteredImage.value = imageUriStr
    }

    fun getFilteredImage() : LiveData<String>{
        return filteredImage
    }

    fun setPostValue(postValue: String) {
        this.postValue.value = postValue
    }

    fun getPostValue(): LiveData<String> {
        return postValue
    }

    fun cleanDataImage(){
        this.filteredImage.value = emptyString()

    }
}