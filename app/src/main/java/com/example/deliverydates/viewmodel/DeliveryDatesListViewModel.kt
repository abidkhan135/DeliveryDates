package com.example.deliverydates.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.deliverydates.model.*
import java.lang.Math.abs
import java.time.DayOfWeek
import java.time.LocalDate

class DeliveryDatesListViewModel(application: Application) : AndroidViewModel(application) {

    val deliveryDates by lazy { MutableLiveData<List<DeliveryDate>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }
    private val deliveryDatesMap = mutableMapOf<LocalDate, Boolean>()
    private val finalDeliveryDates = mutableListOf<LocalDate>()
    lateinit var productList: List<Product>

    fun refresh(postalCode: String?) {
        getProductList()
        getDeliveryDates(postalCode, productList)

    }

    private fun getProductList() {
        val p1 = Product(
            "1",
            "Chicken",
            listOf("13756", "16456"),
            listOf(DayOfWeek.MONDAY, DayOfWeek.THURSDAY),
            ProductType.normal,
            1
        )
        val p2 = Product(
            "2",
            "Beef",
            listOf("13756", "16456"),
            listOf(DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY),
            ProductType.external,
            5
        )
        val p3 = Product(
            "3",
            "Vegetable",
            listOf("13756", "16456"),
            listOf(DayOfWeek.THURSDAY),
            ProductType.temporary,
            4
        )
        val p4 = Product(
            "4",
            "Drinks",
            listOf("13756"),
            listOf(DayOfWeek.SATURDAY),
            ProductType.normal,
            2
        )
        val p5 =
            Product(
                "5",
                "Eggs",
                listOf("16456", "13756"),
                listOf(DayOfWeek.FRIDAY),
                ProductType.normal,
                1
            )
        productList = arrayListOf<Product>(p1, p2, p3, p4, p5)
        loadError.value = false
        loading.value = false
    }

    private fun getDeliveryDates(code: String?, productList: List<Product>) {

        for (i in 0..13) {
            deliveryDatesMap[LocalDate.now().plusDays(i.toLong())] = false
        }
        for (product in productList) {
            for (deliveryDate in deliveryDatesMap.keys) {
                val advanceDaysCheck = abs(LocalDate.now().dayOfMonth - deliveryDate.dayOfMonth)
                val currentWeekDeliveryCheck =
                    abs(deliveryDate.dayOfMonth - (deliveryDate.with(DayOfWeek.SUNDAY)).dayOfMonth)
                for (dayOfWeek in product.deliveryDays!!) {
                    // if (product.productType?.equals("external")!! && difference>5 || ((product.productType?.equals("temporary")) && difference2 <=7) || product.productType.equals("normal"))
                    if (product.productType.toString().equals(ProductType.external.toString()) && advanceDaysCheck > 5) {

                        getFinalDays(deliveryDate, dayOfWeek, advanceDaysCheck, product)
                    } else if (((product.productType.toString().equals(ProductType.temporary.toString())) && currentWeekDeliveryCheck <= 7)) {

                        getFinalDays(deliveryDate, dayOfWeek, advanceDaysCheck, product)
                    } else if (product.productType.toString().equals(ProductType.normal.toString())) {

                        getFinalDays(deliveryDate, dayOfWeek, advanceDaysCheck, product)
                    } else {
                        println("No Delivery on Coming 14 Days " + product.productType)
                    }
                }
            }
        }
        for (deliveryDate in deliveryDatesMap) {
            if (deliveryDate.value) {
                finalDeliveryDates.add(deliveryDate.key)
                println("The Final date is --------------->" + deliveryDate.key)
            }
        }
        val d1 = DeliveryDate("123", LocalDate.now(), true)
        val d2 = DeliveryDate("123", LocalDate.now(), true)
        val d3 = DeliveryDate("123", LocalDate.now(), true)
        val d4 = DeliveryDate("123", LocalDate.now(), true)

        val deliveryDatesList = arrayListOf(d1, d2, d3, d4)
        deliveryDates.value = deliveryDatesList
        loadError.value = false
        loading.value = false
    }

    private fun getFinalDays(
        deliveryDate: LocalDate,
        dayOfWeek: DayOfWeek,
        advanceDaysCheck: Int,
        product: Product
    ) {
        if (deliveryDate.dayOfWeek == dayOfWeek && advanceDaysCheck > product.daysInAdvance!!) {
            deliveryDatesMap[deliveryDate] = true
        } else {
            // Discard the date
        }
    }
}
