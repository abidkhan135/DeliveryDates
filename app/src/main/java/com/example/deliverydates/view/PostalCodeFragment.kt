package com.example.deliverydates.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.example.deliverydates.R
import kotlinx.android.synthetic.main.fragment_postal_code.*


class PostalCodeFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_postal_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        buttonDeliveryDetail.setOnClickListener{

            val action = PostalCodeFragmentDirections.actionGoToDeliveryDates(1234)
            Navigation.findNavController(it).navigate(action)
        }
    }


}