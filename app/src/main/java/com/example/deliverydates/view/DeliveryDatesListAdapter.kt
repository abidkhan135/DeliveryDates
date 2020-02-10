package com.example.deliverydates.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deliverydates.R
import com.example.deliverydates.model.DeliveryDate
import kotlinx.android.synthetic.main.item_deliverydate.view.*

class DeliveryDatesListAdapter (private val deliveryDateList: ArrayList<DeliveryDate>):
    RecyclerView.Adapter<DeliveryDatesListAdapter.DeliveryDatesViewHolder>() {

    fun updateDeliveryDatesList(newdeliveryDateList: List<DeliveryDate>){
       deliveryDateList.clear()
       deliveryDateList.addAll(newdeliveryDateList)
       notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryDatesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var view = inflater.inflate(R.layout.item_deliverydate,parent,false)
        return DeliveryDatesViewHolder(view)

    }

    override fun getItemCount() = deliveryDateList.size

    override fun onBindViewHolder(holder: DeliveryDatesViewHolder, position: Int) {
        holder.view.deliveryDate.text = deliveryDateList[position].deliveryDate.toString()
        if (deliveryDateList[position].isGreenDelivery){
        holder.view.isGreenDelivery.text= "TRUE"
        }
        else holder.view.isGreenDelivery.text = "FALSE"


    }

    class DeliveryDatesViewHolder( var view: View): RecyclerView.ViewHolder(view){

    }
}