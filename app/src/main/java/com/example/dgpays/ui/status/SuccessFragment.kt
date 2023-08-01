package com.example.dgpays.ui.status

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.example.dgpays.R
import com.example.dgpays.data.models.IyzicoResponse
import com.example.dgpays.data.models.Order
import com.example.dgpays.databinding.FragmentSuccesBinding
import com.example.dgpays.ui.bag.ShoppingBagFragment
import com.example.dgpays.ui.payment.PaymentFragment
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response

@AndroidEntryPoint
class SuccessFragment(
    private val order: Order,
) : Fragment() {

    private val viewModel: SuccesViewModel by viewModels()

    private var _binding : FragmentSuccesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuccesBinding.inflate(inflater, container, false)

        binding.maskedCardNoTextView.text = order.maskedCardNumber
        binding.amountTextView.text = order.totalPrice.toString()
        binding.orderIdTextView.text = order.orderTime
        binding.orderIdTextView.text = order.orderId.toString()

        binding.backButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, ShoppingBagFragment())
                commit()
            }
        }

        return binding.root
    }
}