package com.example.deliverydates.model

import java.time.DayOfWeek

data class Product(
    val productId: String?,
    val name: String?,
    val postalCode: List<String>?,
    val deliveryDays: List<DayOfWeek>?,
    val productType: ProductType?,
    val daysInAdvance: Int?
)
enum class ProductType {
    normal,
    external,
    temporary
}