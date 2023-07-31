package com.example.dgpays.ui.bag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dgpays.MainActivity
import com.example.dgpays.R
import com.example.dgpays.databinding.FragmentBagBinding
import com.example.dgpays.ui.adapters.ShoppingCartAdapter
import com.example.dgpays.ui.payment.PaymentFragment
import com.example.dgpays.ui.status.SuccessFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingBagFragment : Fragment() {

    private val viewModel: ShoppingBagViewModel by viewModels()

    private var _binding : FragmentBagBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBagBinding.inflate(inflater, container, false)

        viewModel.getAllBasketItems().observe(viewLifecycleOwner) { basketItems ->
            if (basketItems.isEmpty()){
                binding.recyclerView.visibility = View.GONE
                binding.tvEmpty.visibility = View.VISIBLE
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                binding.tvEmpty.visibility = View.GONE
                binding.recyclerView.adapter = ShoppingCartAdapter(basketItems, viewModel)
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            }
        }

        binding.cleanButton.setOnClickListener {
            viewModel.deleteAllBasketItems()
        }

        binding.peyButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, PaymentFragment())
                commit()
            }
        }
        return binding.root
    }
}