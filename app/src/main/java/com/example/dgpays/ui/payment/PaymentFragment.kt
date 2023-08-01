package com.example.dgpays.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.dgpays.R
import com.example.dgpays.data.models.IyziReq
import com.example.dgpays.data.models.Order
import com.example.dgpays.databinding.FragmentBagBinding
import com.example.dgpays.databinding.FragmentPaymentBinding
import com.example.dgpays.ui.status.FailureFragment
import com.example.dgpays.ui.status.SuccessFragment
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
        binding.creditCardView.setBankName("DGPays")
        binding.payButton.setOnClickListener {
            val cardNumber = binding.creditCardView.getCardNumber()
            val cardHolderName = binding.creditCardView.getNameOnCard()
            val expiryDate = binding.creditCardView.getExpiryDate()
            val cvv = binding.creditCardView.getCvv()

            // check if the card is valid
            if (viewModel.luhnAlgorithm(cardNumber) && !viewModel.isExpired(expiryDate?.month ?: 12,
                    expiryDate?.year ?: 30
                ) && viewModel.isCvvValid(cvv)) {
                var body = IyziReq(
                    cardHolderName,
                    cardHolderName.toString().split(" ")[1],
                    email = "hasanyigtkaya@gmail.com",
                    address = "Nidakule Göztepe, Merdivenköy Mah. Bora Sok. No:1",
                    city = "Istanbul",
                    country = "Turkey",
                    price = binding.tvAmount.text.toString().toDouble(),
                    cardHolderName,
                    cardNumber,
                    (expiryDate?.month ?: 12).toString(),
                    (expiryDate?.year ?: 30).toString(),
                    cvv,
                )

                viewModel.pay(body)

            }

            viewModel.myResponse.observe(viewLifecycleOwner, Observer { response ->
                if (response.body()?.status == "success") {
                    response.body()?.let {
                        val order = Order(
                            maskedCardNumber = cardNumber.toString().take(6) + "******" + cardNumber.toString().takeLast(4),
                            paid = true,
                            orderTime = it.systemTime.toLong(),
                            totalPrice = it.price ?: 0.0,
                            )
                        viewModel.saveOrder(order)
                    }

                    // replace fragment to success fragment after waiting a second
                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, SuccessFragment())
                        commit()
                    }
                }
            })

        }

        return binding.root
    }

}