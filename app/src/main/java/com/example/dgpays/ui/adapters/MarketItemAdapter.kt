package com.example.dgpays.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dgpays.data.models.Basket
import com.example.dgpays.data.models.MarketItem
import com.example.dgpays.databinding.MarketItemBinding
import com.example.dgpays.ui.home.HomeViewModel

class MarketItemAdapter(
    private var marketItems: List<MarketItem>,
    private var basketItems: List<Basket>,
    private val viewModel: HomeViewModel

) : RecyclerView.Adapter<MarketItemAdapter.MarketItemViewHolder>(){

    inner class MarketItemViewHolder(val binding: MarketItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketItemViewHolder {
        val binding = MarketItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarketItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarketItemViewHolder, position: Int) {
        val currentItem = marketItems[position]
        val amount = basketItems.find { it.id == currentItem.id }?.amount
        holder.binding.apply {
            tvTitle.text = currentItem.name
            tvPrice.text = currentItem.price.toString()
            tvAmount.text = amount?.toString() ?: "0"
        }

        holder.binding.minusButton.setOnClickListener {
            if (holder.binding.tvAmount.text.toString().toInt() == 1) {
                // delete item from basket
                holder.binding.tvAmount.text = (holder.binding.tvAmount.text.toString().toInt() - 1).toString()
                viewModel.deleteBasketItem(currentItem.id)
            }
            if (holder.binding.tvAmount.text.toString().toInt() > 1) {
                holder.binding.tvAmount.text = (holder.binding.tvAmount.text.toString().toInt() - 1).toString()
                viewModel.upsertBasketItem(Basket(currentItem.name, currentItem.price,
                                                        holder.binding.tvAmount.text.toString().toInt(), currentItem.id))
            }
        }
        holder.binding.plusButton.setOnClickListener {
            if (holder.binding.tvAmount.text.toString().toInt() == 0) {

                holder.binding.tvAmount.text = (holder.binding.tvAmount.text.toString().toInt() + 1).toString()
                viewModel.upsertBasketItem(Basket(currentItem.name, currentItem.price,
                                                        holder.binding.tvAmount.text.toString().toInt(), currentItem.id))
            } else {
                if(holder.binding.tvAmount.text.toString().toInt() != 5) {

                    holder.binding.tvAmount.text = (holder.binding.tvAmount.text.toString().toInt() + 1).toString()
                    viewModel.upsertBasketItem(Basket(currentItem.name, currentItem.price,
                                                                holder.binding.tvAmount.text.toString().toInt(), currentItem.id))
                }
            }

        }

    }

    override fun getItemCount(): Int {
        return marketItems.size
    }

}