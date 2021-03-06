package com.mtek.goarenopoc.data.model

data class TokenModel(
    val token_type : String?,
    val expires_in : Int?,
    val access_token : String?,
    val scope : String?,
    val error: String?,
)