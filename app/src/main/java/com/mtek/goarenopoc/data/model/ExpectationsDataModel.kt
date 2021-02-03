package com.mtek.goarenopoc.data.model

data class ExpectationsDataModel(
    val id: Int,
    val product: String,
    val product_group: String,
    val quantity: Int,
    val shop: ExpectationsShop?,
    val user: ExpectationsUser?,
)

data class ExpectationsShop(
    val id: Int,
    val name: String,
)

data class ExpectationsUser(
    val id: Int,
    val last_name: String,
    val first_name: String,
    val email: String,
    val avatar: String,
    val password: String,
    val username: String,
    val employee_type: String
)