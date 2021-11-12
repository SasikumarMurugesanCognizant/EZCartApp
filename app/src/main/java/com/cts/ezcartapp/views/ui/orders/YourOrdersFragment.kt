package com.cts.ezcartapp.views.ui.orders

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cts.ezcartapp.adapters.CartListAdapter
import com.cts.ezcartapp.adapters.OrdersListAdapter
import com.cts.ezcartapp.adapters.ShoppingListAdapter
import com.cts.ezcartapp.databinding.FragmentOrdersBinding
import com.cts.ezcartapp.di.viewmodelfactory.ViewModelProviderFactory
import com.cts.ezcartapp.domain.model.DataHolder
import com.cts.ezcartapp.utils.Utils
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class YourOrdersFragment : Fragment() {

    private lateinit var yourOrdersViewModel: YourOrdersViewModel
    private var _binding: FragmentOrdersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private val ordersListAdapter: OrdersListAdapter by lazy {
        OrdersListAdapter()
    }
    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        yourOrdersViewModel =
            ViewModelProvider(this,viewModelProviderFactory)[YourOrdersViewModel::class.java]

        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rvOrdersList.adapter = ordersListAdapter
        fetchDataFromCloud()
        activity?.let { it ->
            yourOrdersViewModel.ordersList.observe(it, Observer { data ->
                ordersListAdapter.updateList(data)
            })
            yourOrdersViewModel.dataHolder.observe(it, Observer { response ->
                when (response) {
                    is DataHolder.Loading -> {
                        displayProgressBar(response.isLoading)
                    }
                    is DataHolder.Success -> {
                        yourOrdersViewModel.insertOrdersListToDatabase(response.data.data)
                    }
                    is DataHolder.Fail -> {
                        displayErrorMessage(response.error?.message)
                    }
                }
            })
        }
        return root
    }

    private fun fetchDataFromCloud() {
        if (Utils.isNetworkAvailable(requireContext())) {
            yourOrdersViewModel.fetchOrdersDataFromCloud()
        } else {
            Toast.makeText(
                activity,
                " Network not available, Turn on the Internet please",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun displayErrorMessage(message: String?) {
        Toast.makeText(requireContext(),message?:"API error", Toast.LENGTH_SHORT).show()

    }
    private fun displayProgressBar(isLoading: Boolean) {
        if (isLoading) {
            ordersListAdapter.clearList()
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}