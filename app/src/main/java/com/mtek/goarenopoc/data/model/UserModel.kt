package com.mtek.goarenopoc.data.model

data class UserModelWithAuth(
    val first_name : String?=null,
    val id : Int?=null,
    val last_name : String?=null,
    val avatar : String?=null,
    val email : String?=null,
    val password : String?=null,
    val username : String?=null,
    val employee_type : String?=null,
    val shopId : Int?=null,
)