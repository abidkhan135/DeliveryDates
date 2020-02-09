package com.example.deliverydates.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.deliverydates.R
import com.example.deliverydates.model.DeliveryDate
import com.example.deliverydates.viewmodel.DeliveryDatesListViewModel
import kotlinx.android.synthetic.main.fragment_delivery_dates.*


class DeliveryDatesFragment : Fragment() {

    private lateinit var viewModel: DeliveryDatesListViewModel
    private val listAdapter = DeliveryDatesListAdapter(arrayListOf())

    private var postalCode = 0

    private val deliveryDatesListObserver = Observer<List<DeliveryDate>> { list ->
        list?.let {
            deliveryDatesList.visibility = View.VISIBLE
            listAdapter.updateDeliveryDatesList(it)
        }

    }
    private val loadingLiveDataObserver = Observer<Boolean> { isLoading ->
        loadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
        if (isLoading) {
            listError.visibility = View.GONE
            deliveryDatesList.visibility = View.GONE
        }

    }
    private val errorLiveDataObserver = Observer<Boolean> { isError ->
        listError.visibility = if (isError) View.VISIBLE else View.GONE

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delivery_dates, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            postalCode = DeliveryDatesFragmentArgs.fromBundle(it).postalcode

        }

        viewModel = ViewModelProviders.of(this).get(DeliveryDatesListViewModel::class.java)
        viewModel.deliveryDates.observe(this, deliveryDatesListObserver)
        viewModel.loadError.observe(this, errorLiveDataObserver)
        viewModel.loading.observe(this, loadingLiveDataObserver)
        viewModel.refresh(postalCode)

        deliveryDatesList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = listAdapter
        }

        refershLayout.setOnRefreshListener {
            deliveryDatesList.visibility = View.GONE
            listError.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            viewModel.refresh(postalCode)
            refershLayout.isRefreshing = false
        }
    }


}
