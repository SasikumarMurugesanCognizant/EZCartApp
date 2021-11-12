package com.cts.ezcartapp.views.ui.cart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cts.ezcartapp.adapters.CartListAdapter
import com.cts.ezcartapp.adapters.ShoppingListAdapter
import com.cts.ezcartapp.data.entities.CartData
import com.cts.ezcartapp.data.entities.ShoppingData
import com.cts.ezcartapp.databinding.FragmentViewCartBinding
import com.cts.ezcartapp.di.viewmodelfactory.ViewModelProviderFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ViewCartFragment : Fragment(), CartListAdapter.RemoveCartItemClickListener {

    private lateinit var viewCartViewModel: ViewCartViewModel
    private var _binding: FragmentViewCartBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private val cartListAdapter: CartListAdapter by lazy {
        CartListAdapter(this)
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
        _binding = FragmentViewCartBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCartViewModel =
            ViewModelProvider(this, viewModelProviderFactory)[ViewCartViewModel::class.java]
        binding.rvCartList.adapter = cartListAdapter
        initObserver()

    }
    private fun initObserver() {
        activity?.let {
            viewCartViewModel.cartLiveData.observe(it, Observer { list ->
                val totalAmount = list.sumOf { item -> item.price ?: 0 }
                _binding?.tvTotalAmount?.text = "$ $totalAmount"
                cartListAdapter.updateCartList(list)
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(cartItem: CartData) {
        viewCartViewModel.removeCartItem(cartItem)
    }


}