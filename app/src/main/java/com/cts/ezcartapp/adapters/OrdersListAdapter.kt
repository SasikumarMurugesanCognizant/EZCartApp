package com.cts.ezcartapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cts.ezcartapp.data.entities.CartData
import com.cts.ezcartapp.data.entities.OrdersData
import com.cts.ezcartapp.databinding.ShoppingItemBinding

class OrdersListAdapter :
    RecyclerView.Adapter<OrdersListAdapter.OrdersListVH>() {
    private var ordersList: List<OrdersData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersListVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ShoppingItemBinding.inflate(inflater, parent, false)
        return OrdersListVH(binding)
    }

    override fun onBindViewHolder(holder: OrdersListVH, position: Int) {
        val cartItem = ordersList[position]
        holder.bind(cartItem, position)

    }

    override fun getItemCount(): Int {
        return ordersList.size
    }

    fun updateList(list: List<OrdersData>) {
        this.ordersList = list
        notifyDataSetChanged()
    }

    fun clearList() {
        this.ordersList = emptyList()
        notifyDataSetChanged()
    }

    class OrdersListVH(val binding: ShoppingItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ordersData: OrdersData, position: Int) {
            val productInfo = "Order Date: ${ordersData.orderDate}"
            binding.tvProductInfo.text = productInfo
            binding.tvProductAmount.text = "$".plus(ordersData.orderTotal.toString())
        }

    }
}