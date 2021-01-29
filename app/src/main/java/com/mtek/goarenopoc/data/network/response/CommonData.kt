package com.mtek.goarenopoc.data.network.response

open class CommonData<MODEL>(
    val data: MODEL? = null
) : BaseResponse()