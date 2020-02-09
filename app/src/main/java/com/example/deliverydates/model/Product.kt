package com.example.deliverydates.model

data class Product (
    val productId: String?,
    val name: String?,
    val postalCode: List<String>?,
    val deliveryDays: List<Day>?,
    val productType: ProductType?,
    val daysInAdvance: Int?

)
enum class Day {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY
}
enum class  ProductType{
    normal,
    external,
    temporary
}