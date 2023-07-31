package com.example.dgpays.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.dgpays.data.models.IyziReq
import com.example.dgpays.databinding.FragmentBagBinding
import com.example.dgpays.databinding.FragmentPaymentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment: Fragment() {

    private val viewModel: PaymentViewModel by viewModels()

    private var _binding : FragmentPaymentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)

        viewModel.getAllBasketItems().observe(viewLifecycleOwner, Observer {
            var sum = 0.0
              for (i in it) {
                 sum += i.amount * i.price
              }

            binding.tvAmount.text = sum.toString()
        })

        // check the cardType after the user enters the card number
        binding.creditCardView.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val cardNumber = binding.creditCardView.getCardNumber()
                if (cardNumber != null) {

                }
            }
        }


        binding.payButton.setOnClickListener {
            // check if the card is valid, expiry date is not in the past, cvv length is correct etc.
        }

        return binding.root
    }
}