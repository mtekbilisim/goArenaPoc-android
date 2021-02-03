package com.mtek.goarenopoc.data.model

data class DashboardDataModel(
    val id: Int,
    val product: String,
    val product_group: String,
    val quantity: Int,
    val date_time: String,
    val amount: Int,
    val type: String,
    val shop: Shop?,
    val user: User?,
)

data class Shop(
    val id: Int,
    val name: String,
)

data class User(
    val id: Int,
    val last_name: String,
    val first_name: String,
    val email: String,
    val avatar: String,
    val password: String,
    val username: String,
    val employee_type: String
)