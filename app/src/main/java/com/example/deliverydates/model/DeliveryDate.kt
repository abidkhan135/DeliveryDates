package com.example.deliverydates.model

import java.time.LocalDate

data class DeliveryDate (
    val deliveryDate: LocalDate?,
    val isGreenDelivery: Boolean
)