package com.example.dgpays.ui.payment

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.dgpays.R
import com.example.dgpays.data.models.IyziReq
import com.example.dgpays.data.models.Order
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
        // set Image drawable with viewmodel func and set it to creditCardView

        binding.fragmentPayment.setOnClickListener {
            if (viewModel.luhnAlgorithm(binding.creditCardView.getCardNumber())) {
                binding.imageView.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(), viewModel.getCardProviderDrawable(binding.creditCardView.getCardNumber()))
                )
            }
        }

        binding.payButton.setOnClickListener {
            val cardNumber = binding.creditCardView.getCardNumber()
            val cardHolderName = binding.creditCardView.getNameOnCard()
            val expiryDate = binding.creditCardView.getExpiryDate()
            val cvv = binding.creditCardView.getCvv()
            val email = binding.emailTextInputLayout.editText?.text.toString()
            val address = binding.addressTextInputLayout.editText?.text.toString()
            val city = binding.cityTextInputLayout.editText?.text.toString()
            val country = binding.countryTextInputLayout.editText?.text.toString()

            // check if the card is valid
            if (viewModel.luhnAlgorithm(cardNumber) && !viewModel.isExpired(expiryDate?.month ?: 12,
                    expiryDate?.year ?: 30
                ) && viewModel.isEmailValid(email) && city.isNotEmpty() && country.isNotEmpty() && address.isNotEmpty() && viewModel.isCvvValid(cvv)) {
                var body = IyziReq(
                    cardHolderName,
                    cardHolderName.toString().split(" ")[1],
                    email = email,
                    address = address,
                    city = city,
                    country = country,
                    price = binding.tvAmount.text.toString().toDouble(),
                    cardHolderName,
                    cardNumber,
                    (expiryDate?.month ?: 12).toString(),
                    (expiryDate?.year ?: 30).toString(),
                    cvv,
                )
                ContextCompat.getDrawable(requireContext(), viewModel.getCardProviderDrawable(binding.creditCardView.getCardNumber()))
                viewModel.pay(body)
            }

            viewModel.myResponse.observe(viewLifecycleOwner, Observer { response ->
                if (response.body()?.status == "success") {
                    response.body()?.let {
                        val order = Order(
                            maskedCardNumber = cardNumber.toString().take(6) + "******" + cardNumber.toString().takeLast(4),
                            paid = true,
                            orderTime = viewModel.convertLongToTime(it.systemTime.toLong()),
                            totalPrice = it.price ?: 0.0,
                            orderId = it.paymentId!!.toInt(),
                            )
                        viewModel.saveOrder(order)
                        viewModel.clearBasket()

                        parentFragmentManager.beginTransaction().apply {
                            replace(R.id.flFragment, SuccessFragment(order))
                            commit()
                        }
                    }
                } else {
                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, FailureFragment(viewModel.myResponse))
                        commit()
                    }
                }
            })

        }

        return binding.root
    }
}