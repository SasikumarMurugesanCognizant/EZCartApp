package com.cts.ezcartapp.views.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.cts.ezcartapp.utils.Constants
import com.cts.ezcartapp.utils.Constants.userPassword
import com.cts.ezcartapp.R
import com.cts.ezcartapp.adapters.ShoppingListAdapter
import com.cts.ezcartapp.adapters.ViewPagerAdapter
import com.cts.ezcartapp.domain.model.LoginUser
import com.cts.ezcartapp.data.entities.ShoppingData
import com.cts.ezcartapp.databinding.FragmentHomeBinding
import com.cts.ezcartapp.di.viewmodelfactory.ViewModelProviderFactory
import com.cts.ezcartapp.domain.model.DataHolder
import com.cts.ezcartapp.utils.SharedPreference
import com.cts.ezcartapp.utils.Utils
import com.google.gson.Gson
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class HomeFragment : Fragment(), ShoppingListAdapter.ShoppingListItemClickListener {
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sharedPreference: SharedPreference

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private val shoppingListAdapter: ShoppingListAdapter by lazy {
        ShoppingListAdapter(this)
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
        homeViewModel =
            ViewModelProvider(this, viewModelProviderFactory)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerview.adapter = shoppingListAdapter

        val loginRequest = LoginUser(
            sharedPreference.getString(Constants.userId), sharedPreference.getString(
                userPassword
            )
        )
        fetchDataFromCloud(loginRequest)


        activity?.let { it ->
            homeViewModel.shoppingList.observe(it, Observer { data ->
                Log.v("ShoppingData", data.size.toString())
                shoppingListAdapter.updateList(data)
            })
            homeViewModel.dataHolder.observe(it, Observer { response ->
                when (response) {
                    is DataHolder.Loading -> {
                          displayProgressBar(response.isLoading)
                    }
                    is DataHolder.Success -> {
                        homeViewModel.insertShoppingListToDatabase(response.data)
                    }
                    is DataHolder.Fail -> {
                        displayErrorMessage(response.error?.message)
                    }
                }
            })
        }
        val viewPagerAdapter = ViewPagerAdapter()
        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.offscreenPageLimit = 2
        binding.dots.attachViewPager(binding.viewPager)
        return root
    }

    private fun displayErrorMessage(message: String?) {
        Toast.makeText(requireContext(),message?:"API error",Toast.LENGTH_SHORT).show()

    }

    private fun displayProgressBar(isLoading: Boolean) {
        if (isLoading) {
            shoppingListAdapter.clearList()
            _binding?.progressBar?.visibility = View.VISIBLE
        } else {
            _binding?.progressBar?.visibility = View.GONE
        }
    }

    private fun fetchDataFromCloud(loginRequest: LoginUser) {
        if (Utils.isNetworkAvailable(requireContext())) {
            homeViewModel.fetchShoppingDataFromCloud(loginRequest)
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
        homeViewModel.dataHolder.removeObservers(this)
        homeViewModel.shoppingList.removeObservers(this)
        _binding = null
    }

    override fun onItemClick(shoppingData: ShoppingData,pos:Int) {
        val bundle = bundleOf("productInfo" to Gson().toJson(shoppingData).toString())
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_nav_home_to_productDetailFragment,bundle)
    }

}