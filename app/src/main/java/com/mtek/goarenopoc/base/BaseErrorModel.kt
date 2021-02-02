package com.mtek.goarenopoc.base

import com.google.gson.annotations.SerializedName

data class BaseErrorModel(
    var timestamp: Int? = null,
    var path: String? = null,
    var status: Int? = null,
    var error: String? = null,
    var message: Int? = null,
    var requestId : String?=null
) {

    override fun toString(): String {
        return "ErrorBody(error=$error)"
    }


}

data class Errors(
    var code: Int? = null,
    var message: String? = null,
    @SerializedName("class")
    var classes: String? = null,
    var file: String? = null,
    var line: Int? = null
) {


    override fun toString(): String {
        return "Errors(code=$code, message=$message, classes=$classes, file=$file, line=$line)"
    }


}