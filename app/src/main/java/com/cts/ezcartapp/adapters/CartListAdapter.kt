package com.cts.ezcartapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.cts.ezcartapp.data.entities.CartData
import com.cts.ezcartapp.data.entities.ShoppingData
import com.cts.ezcartapp.databinding.ShoppingItemBinding

class CartListAdapter(private val removeCartItemClickListener: RemoveCartItemClickListener) :
    RecyclerView.Adapter<CartListAdapter.CartVH>() {
    var cartList: List<CartData> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ShoppingItemBinding.inflate(inflater, parent, false)
        return CartVH(binding)

    }

    override fun onBindViewHolder(holder: CartVH, position: Int) {
        holder.binding.ivDelete.visibility = View.VISIBLE
        val cartItem = cartList[position]
        holder.bind(cartItem, position)
        holder.binding.ivDelete.setOnClickListener {
            removeCartItemClickListener.onItemClick(cartItem)
        }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    fun updateCartList(it: List<CartData>?) {
        it?.let {
            cartList = it
            notifyDataSetChanged()
        }

    }

    class CartVH(val binding: ShoppingItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cartItem: CartData, position: Int) {
            binding.tvProductInfo.text = cartItem.itemName
            binding.tvProductAmount.text = cartItem.currency
        }

    }

    interface RemoveCartItemClickListener {
        fun onItemClick(cartItem: CartData)
    }
}