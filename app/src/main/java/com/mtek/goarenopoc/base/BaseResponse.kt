package com.mtek.goarenopoc.base

import com.mtek.goarenopoc.utils.ErrorType
import com.mtek.goarenopoc.utils.emptyBoolean
import com.mtek.goarenopoc.utils.emptyString
import com.mtek.goarenopoc.utils.nullObject


open class BaseResponse(
    val isSuccess: Boolean = emptyBoolean(),
    val message: ErrorType? = nullObject(),
    val warningMessage:String =  emptyString()
)
