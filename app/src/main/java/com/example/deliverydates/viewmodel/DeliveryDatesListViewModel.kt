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
    val deliveryDatesList = arrayListOf<DeliveryDate>()
    private val finalDeliveryDates = mutableListOf<LocalDate>()
    private val isGreenDeliveryDates = mutableListOf<LocalDate>()
    lateinit var productList: List<Product>
    var greenDateCheck=true

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
            listOf(DayOfWeek.SUNDAY),
            ProductType.temporary,
            3
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
                    abs(LocalDate.now().dayOfMonth - (deliveryDate.with(DayOfWeek.SUNDAY)).dayOfMonth)
                for (dayOfWeek in product.deliveryDays!!) {
                    if (product.productType.toString().equals(ProductType.external.toString()) && advanceDaysCheck > 5) {

                        getFinalDays(deliveryDate, dayOfWeek, advanceDaysCheck, product,code)
                    } else if (((product.productType.toString().equals(ProductType.temporary.toString())) && currentWeekDeliveryCheck <= 7)) {

                        getFinalDays(deliveryDate, dayOfWeek, advanceDaysCheck, product, code)
                    } else if (product.productType.toString().equals(ProductType.normal.toString())) {

                        getFinalDays(deliveryDate, dayOfWeek, advanceDaysCheck, product, code)
                    } else {
                         //Discard this Date
                    }
                }
            }
        }
        for (deliveryDate in deliveryDatesMap) {

            if (deliveryDate.value) {
                finalDeliveryDates.add(deliveryDate.key)
            }
        }
        if (finalDeliveryDates.isNotEmpty()) {
            for (finalDate in finalDeliveryDates) {

                if (greenDateCheck) {
                    for (greenDate in isGreenDeliveryDates) {
                        deliveryDatesList.add(DeliveryDate(greenDate, true))
                    }
                    greenDateCheck = false
                }
                deliveryDatesList.add(DeliveryDate(finalDate, false))
            }
            deliveryDates.value = deliveryDatesList
            loadError.value = false
            loading.value = false
        }else{
            deliveryDates.value = null
            loadError.value = true
        }
    }

    private fun getFinalDays(
        deliveryDate: LocalDate,
        dayOfWeek: DayOfWeek,
        advanceDaysCheck: Int,
        product: Product,
        code: String?
    ) {
        if (deliveryDate.dayOfWeek == dayOfWeek && advanceDaysCheck > product.daysInAdvance!!) {
            if (product.postalCode.toString().contains(code.toString()) && advanceDaysCheck <=3){
               isGreenDeliveryDates.add(deliveryDate)
            }else if (product.postalCode.toString().contains(code.toString())){
                deliveryDatesMap[deliveryDate] = true
            }
        } else {
            // Discard the date
        }
    }
}
