package com.example.dgpays.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dgpays.databinding.FragmentTransactionsBinding
import com.example.dgpays.ui.adapters.OrderAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionsFragment : Fragment() {

    private val viewModel: TransactionsViewModel by viewModels()

    private var _binding : FragmentTransactionsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionsBinding.inflate(inflater, container, false)

        viewModel.getAllOrders().observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                binding.tvNoOrders.visibility = View.VISIBLE
                binding.rvOrders.visibility = View.GONE
            } else {
                binding.tvNoOrders.visibility = View.GONE
                binding.rvOrders.visibility = View.VISIBLE
                binding.rvOrders.adapter = OrderAdapter(it, viewModel)
                binding.rvOrders.layoutManager = LinearLayoutManager(requireContext())
            }
        })

        return binding.root
    }
}