package com.cts.ezcartapp.views.ui.home

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.cts.ezcartapp.R
import com.cts.ezcartapp.data.entities.ShoppingData
import com.cts.ezcartapp.databinding.ProductDetailFragmentBinding
import com.cts.ezcartapp.di.viewmodelfactory.ViewModelProviderFactory
import com.cts.ezcartapp.views.HomeActivity
import com.google.gson.Gson
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ProductDetailFragment : Fragment() {

    private lateinit var viewModel: ProductDetailViewModel

    private lateinit var binding: ProductDetailFragmentBinding

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private var selectedProductInfo: ShoppingData? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProductDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory)[ProductDetailViewModel::class.java]
        selectedProductInfo =Gson().fromJson(arguments?.getString("productInfo"),ShoppingData::class.java)
        selectedProductInfo?.let {
            setProductInfo(it)
        }

        binding.btnAddToCart.setOnClickListener {
            selectedProductInfo?.let {
                viewModel.addToCart(it)
            }

        }
        activity?.let {
            viewModel.addToCartStatus.observe(it, Observer { Status ->
                if(Status){
                    displayMessage()
                }
            })
        }


    }

    private fun displayMessage() {
         Toast.makeText(requireContext(),"Product Added to cart",Toast.LENGTH_SHORT).show()

    }

    private fun setProductInfo(selectedProductInfo: ShoppingData?) {
        binding.tvPrice.text = "$".plus(selectedProductInfo?.price)
       /* Navigation.findNavController(binding.root)
            .currentDestination?.label = selectedProductInfo?.itemName?:"Product Details";*/

        (requireActivity() as HomeActivity).supportActionBar?.title =selectedProductInfo?.itemName?:"Product Details";

    }

}