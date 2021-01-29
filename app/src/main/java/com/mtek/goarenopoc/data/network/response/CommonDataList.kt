package com.mtek.goarenopoc.data.network.response

import com.mtek.goarenopoc.base.BaseResponse

open class CommonDataList<T>(
    val data: List<T>? = null
) : BaseResponse()