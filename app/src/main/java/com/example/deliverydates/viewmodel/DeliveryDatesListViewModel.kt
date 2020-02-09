package com.example.deliverydates.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.deliverydates.model.DeliveryDates
import com.example.deliverydates.model.Product
import com.example.deliverydates.model.ProductType
import com.example.deliverydates.model.WorkingDays

class DeliveryDatesListViewModel (application: Application): AndroidViewModel(application){

    val deliveryDates by lazy { MutableLiveData<List<DeliveryDates>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy {MutableLiveData<Boolean>()}
    lateinit var productList :List<Product>

    fun refresh(postalCode: Int){
        getProductList()
        getDeliveryDates(postalCode,productList)

    }
    private fun getProductList() {
        val p1 = Product("1","Chicken",WorkingDays.MONDAY,ProductType.normal,1)
        val p2 = Product("2","Beef",WorkingDays.TUESDAY,ProductType.external,3)
        val p3 = Product("3","Vegetable",WorkingDays.WEDNESDAY,ProductType.temporary,4)
        val p4 = Product("4","Drinks",WorkingDays.THURSDAY,ProductType.normal,2)
        val p5 = Product("5","Eggs",WorkingDays.FRIDAY,ProductType.normal,1)

        productList = arrayListOf<Product>(p1,p2,p3,p4,p5)
        loadError.value = false
        loading.value = false

    }



    private  fun getDeliveryDates(code:Int,productList: List<Product>){
        println("The code is $code ")
        println("THe product ID is ${productList[0].productId}")
        println("The prodyct Type is ${productList[0].productType}")
        val d1= DeliveryDates("123","2-2-20",true)
        val d2= DeliveryDates("456","2-3-20",false)
        val d3= DeliveryDates("789","2-4-20",true)
        val d4= DeliveryDates("258","2-5-20",false)
        val d5= DeliveryDates("741","2-6-20",true)

        val deliveryDatesList = arrayListOf<DeliveryDates>(d1,d2,d3,d4,d5)

        deliveryDates.value = deliveryDatesList
        loadError.value = false
        loading.value = false

    }
}
