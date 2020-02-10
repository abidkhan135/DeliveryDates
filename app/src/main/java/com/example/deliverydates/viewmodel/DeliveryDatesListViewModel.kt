package com.example.deliverydates.viewmodel

import android.app.Application
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.deliverydates.model.*
import java.lang.Math.abs
import java.time.DayOfWeek
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
            listOf("13756","16456"), listOf(DayOfWeek.MONDAY,DayOfWeek.THURSDAY),ProductType.normal,1)
        val p2 = Product("2","Beef",
            listOf("13756","16456"), listOf(DayOfWeek.TUESDAY,DayOfWeek.WEDNESDAY),ProductType.external,5)
        val p3 = Product("3","Vegetable", listOf("13756","16456"), listOf(DayOfWeek.THURSDAY),ProductType.temporary,4)
        val p4 = Product("4","Drinks", listOf("13756"), listOf(DayOfWeek.MONDAY),ProductType.normal,2)
        val p5 = Product("5","Eggs", listOf("16456"), listOf(DayOfWeek.FRIDAY),ProductType.normal,1)

        productList = arrayListOf<Product>(p1,p2,p3,p4,p5)
        loadError.value = false
        loading.value = false

    }


    private  fun getDeliveryDates(code:Int,productList: List<Product>){



        val map = mutableMapOf<LocalDate, Boolean>()

        for (i in 0 .. 14) {
            map[LocalDate.now().plusDays(1)] = false

        }


        val now = LocalDate.now()
        println("The date is ------>"+now.with(DayOfWeek.SUNDAY))

        for (product in productList) {


            for (d in map.keys) {

                // product.days wed
                // d is 12 feb wed
                // advance days is 3
                val difference = abs(LocalDate.now().dayOfMonth - d.dayOfMonth)
                val difference2 = abs(d.dayOfMonth - (d.with(DayOfWeek.SUNDAY)).dayOfMonth)
                for (day in product.deliveryDays!!) {

                    if (product.productType?.equals("external")!! && difference>5 || ((product.productType?.equals("temporary")) && difference2 <=7) || product.productType.equals("normal"))
                    {
                        // 1st condition is satisfied
                        if (d.dayOfWeek == day && difference > product.daysInAdvance!!) {


                            // all conditions satisified
                            map[d] = true
                        } else{
                            println("Second IFProduct type is "+product.productType +product.deliveryDays+ "difference is $difference and $difference2")
                        }

                    }else{
                        println("First IF Product type is "+product.productId+product.productType + product.deliveryDays+"difference is $difference and $difference2")
                    }
                }
            }
        }



        val list  = mutableListOf<LocalDate>()


        for (pair in map) {
            if (pair.value) {
                list.add(pair.key)
               println("The Final date is --------------->"+pair.key)

            }

        }

        val d1= DeliveryDate("123", LocalDate.now(),true)
        val d2= DeliveryDate("123",LocalDate.now(),true)
        val d3= DeliveryDate("123",LocalDate.now(),true)
        val d4= DeliveryDate("123",LocalDate.now(),true)


       val deliveryDatesList = arrayListOf(d1,d2,d3,d4)


        deliveryDates.value = deliveryDatesList
        loadError.value = false
        loading.value = false

    }
}
