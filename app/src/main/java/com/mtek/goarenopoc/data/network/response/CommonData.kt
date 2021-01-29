package com.mtek.goarenopoc.data.network.response

import com.mtek.goarenopoc.base.BaseResponse

open class CommonData<MODEL>(
    val data: MODEL? = null
) : BaseResponse()