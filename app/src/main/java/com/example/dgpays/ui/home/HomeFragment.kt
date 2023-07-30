package com.example.dgpays.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dgpays.data.models.MarketItem
import com.example.dgpays.databinding.FragmentHomeBinding
import com.example.dgpays.ui.adapters.MarketItemAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val marketItems = listOf(
            MarketItem("Kalem", 9.58, 1),
            MarketItem("Kağıt", 0.61, 2),
            MarketItem("Silgi", 40.0, 3),
            MarketItem("Defter", 5.58, 4),
            MarketItem("Kitap", 50.82, 5),
        )

        viewModel.upsertMarketItem(marketItems)

        viewModel.getAllMarketItems().observe(viewLifecycleOwner) { marketItems ->
            viewModel.getAllBasketItems().observe(viewLifecycleOwner) { basketItems ->
                binding.recyclerView.adapter =
                    MarketItemAdapter(marketItems, basketItems, viewModel)
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            }
        }

        return binding.root
    }
}