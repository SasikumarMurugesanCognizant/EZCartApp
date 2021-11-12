package com.cts.ezcartapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cts.ezcartapp.data.entities.ShoppingData
import com.cts.ezcartapp.databinding.ShoppingItemBinding
import java.lang.reflect.Constructor

class ShoppingListAdapter(private val shoppingListItemClickListener: ShoppingListItemClickListener) :
    RecyclerView.Adapter<ShoppingListAdapter.ShoppingListVH>() {
    private var shoppingList: List<ShoppingData> = mutableListOf()
  /*  private var isFromCartScreen: Boolean = false

    constructor(
        shoppingListItemClickListener: ShoppingListItemClickListener,
        isCartScreen: Boolean
    ) : this(shoppingListItemClickListener) {
        this.isFromCartScreen = isCartScreen
    }
*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ShoppingItemBinding.inflate(inflater, parent, false)
        return ShoppingListVH(binding)
    }

    override fun onBindViewHolder(holder: ShoppingListVH, position: Int) {
        val shoppingItem = shoppingList[position]
        holder.bind(shoppingItem, position)
        holder.binding.root.setOnClickListener {
            shoppingListItemClickListener.onItemClick(shoppingItem,position)
        }
    }

    override fun getItemCount(): Int {

        return shoppingList.size
    }

    fun updateList(list: List<ShoppingData>) {
        this.shoppingList = list
        notifyDataSetChanged()
    }

    fun clearList() {
        this.shoppingList = emptyList()
        notifyDataSetChanged()
    }

    class ShoppingListVH(val binding: ShoppingItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(shoppingItem: ShoppingData, position: Int) {
            val productInfo = shoppingItem.itemName + " - " + shoppingItem.desc
            binding.tvProductInfo.text = productInfo
            binding.tvProductAmount.text = "$".plus(shoppingItem.price)
        }

    }

    public interface ShoppingListItemClickListener {
        fun onItemClick(shoppingData: ShoppingData,position:Int)
    }
}