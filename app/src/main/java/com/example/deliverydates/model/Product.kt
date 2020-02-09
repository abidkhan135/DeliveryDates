package com.example.deliverydates.model

data class Product (
    val productId: String?,
    val name: String?,
    val deliveryDays: WorkingDays?,
    val productType: ProductType?,
    val daysInAdvance: Int?

)
enum class WorkingDays {
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