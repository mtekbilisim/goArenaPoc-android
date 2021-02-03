package com.mtek.goarenopoc.data.network.response

import com.mtek.goarenopoc.base.BaseResponse

open class CommonDataList<T>(
    var data: List<T>? = null
) : BaseResponse()