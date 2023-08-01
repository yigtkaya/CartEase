package com.example.dgpays.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dgpays.data.models.Order
import com.example.dgpays.databinding.OrderItemBinding
import com.example.dgpays.ui.transactions.TransactionsViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OrderAdapter(
    private var orders: List<Order>,
    private val viewModel: TransactionsViewModel
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(val binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val currentItem = orders[position]

        holder.binding.apply {
            tvOrderId.text = currentItem.orderId.toString()
            if (currentItem.paid) {
                tvStatus.text = "Paid"
                tvStatus.setBackgroundColor(Color.GREEN)
            } else {
                tvStatus.text = "Cancelled"
                tvStatus.setBackgroundColor(Color.RED)
            }
            tvAmount.text = currentItem.totalPrice.toString()
            tvCardNumber.text = currentItem.maskedCardNumber
            tvTime.text = currentItem.orderTime
        }

        holder.binding.cancelButton.setOnClickListener {
            if (currentItem.paid) {
                GlobalScope.launch {
                    viewModel.updatePaidStatus(currentItem.orderId, false)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return orders.size
    }
}