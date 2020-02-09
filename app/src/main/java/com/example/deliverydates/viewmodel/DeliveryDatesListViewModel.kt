package com.example.deliverydates.viewmodel

import android.app.Application
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.deliverydates.model.*
import java.time.LocalDate
import java.time.LocalDateTime

class DeliveryDatesListViewModel (application: Application): AndroidViewModel(application){

    val deliveryDates by lazy { MutableLiveData<List<DeliveryDate>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy {MutableLiveData<Boolean>()}
    lateinit var productList :List<Product>

    fun refresh(postalCode: Int){
        getProductList()
        getDeliveryDates(postalCode,productList)

    }
    private fun getProductList() {


        val p1 = Product("1","Chicken",
            listOf("13756","16456"), listOf(Day.MONDAY,Day.THURSDAY),ProductType.normal,1)
        val p2 = Product("2","Beef",
            listOf("13756","16456"), listOf(Day.TUESDAY,Day.WEDNESDAY),ProductType.external,5)
        val p3 = Product("3","Vegetable", listOf("13756","16456"), listOf(Day.THURSDAY),ProductType.temporary,4)
        val p4 = Product("4","Drinks", listOf("13756"), listOf(Day.MONDAY),ProductType.normal,2)
        val p5 = Product("5","Eggs", listOf("16456"), listOf(Day.FRIDAY),ProductType.normal,1)

        productList = arrayListOf<Product>(p1,p2,p3,p4,p5)
        loadError.value = false
        loading.value = false

    }


    private  fun getDeliveryDates(code:Int,productList: List<Product>){

        var date= LocalDate.now()
        println("Day of Week is ${date.dayOfWeek}")
        var date2=date.plusDays(14)
        println("The next date is ${date2.dayOfWeek} and also $date2")
        println("Start of the day ${date2.atStartOfDay()} ")
        println("the date is $date")


        println("The code is $code ")
        println("THe product ID is ${productList[0].productId}")
        println("The prodyct Type is ${productList[0].productType}")

        val d1= DeliveryDate("123","2-2-20",true)
        val d2= DeliveryDate("456","2-3-20",false)
        val d3= DeliveryDate("789","2-4-20",true)
        val d4= DeliveryDate("258","2-5-20",false)
        val d5= DeliveryDate("741","2-6-20",true)

        val deliveryDatesList = arrayListOf<DeliveryDate>(d1,d2,d3,d4,d5)

        deliveryDates.value = deliveryDatesList
        loadError.value = false
        loading.value = false

    }
}
