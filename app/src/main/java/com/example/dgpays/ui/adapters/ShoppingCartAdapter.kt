package com.example.dgpays.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dgpays.data.models.Basket
import com.example.dgpays.databinding.ShoppingBagItemBinding
import com.example.dgpays.ui.bag.ShoppingBagViewModel

class ShoppingCartAdapter(
    private val basket: List<Basket>,
    private val viewModel: ShoppingBagViewModel
) : RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartViewHolder>(){

    inner class ShoppingCartViewHolder(val binding: ShoppingBagItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartViewHolder {
        val binding = ShoppingBagItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingCartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShoppingCartViewHolder, position: Int) {
        val currentItem = basket[position]
        holder.binding.apply {
            tvTitle.text = currentItem.name
            tvPrice.text = (currentItem.price * currentItem.amount).toString()
            tvAmount.text = currentItem.amount.toString()
        }
    }

    override fun getItemCount(): Int {
        return basket.size
    }
}