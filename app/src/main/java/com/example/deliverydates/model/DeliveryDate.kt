package com.example.deliverydates.model

import java.time.LocalDate

data class DeliveryDate (
    val postalCode: String?,
    val deliveryDate: LocalDate?,
    val isGreenDelivery: Boolean
)