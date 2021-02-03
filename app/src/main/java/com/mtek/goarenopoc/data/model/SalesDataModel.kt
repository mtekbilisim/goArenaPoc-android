package com.mtek.goarenopoc.data.model

data class SalesDataModel(
    val expectation: Int,
    val sales: Int,
    val product_group: String,
    val employee: String? = null
)